package index_structures.rtree;

import index_structures.ISpatialIndex;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import components.ISpatialObject2D;
import components.SpatialObject2DImpl;

public class RTree implements ISpatialIndex {

	private Node root = null;

	// Max entries for a node
	private int M;

	// Min entry for a node
	private int m;

	/**
	 * Constructor
	 * 
	 * @param m
	 * @param M
	 */
	public RTree(int m, int M) {
		this.M = M;
		this.m = m;
		root = new Node(null);
		root.setLevel(0);
		root.setChild(new ArrayList<Node>());
	}

	public void Search(final SpatialObject2DImpl object) {
		// TODO implement
	}

	/**
	 * Select a leaf node in which to place the new spatial object
	 * 
	 * @param node
	 *            Starting node
	 * @param object
	 *            Spatial object
	 * @return The found node
	 */
	private Node ChooseLeaf(Node node, ISpatialObject2D object) {

		if (node != null) {

			// If node is a leaf return it
			if (node.isLeaf()) {
				return node;
			} else {
				// Select the child with the least needed enlargement to
				// encapsulate object bounds
				Node selected = null;
				for (Node child : node.getChilds()) {
					if (selected == null) {
						selected = child;
						continue;
					} else {
						if (Node.enlargment(selected.getBound(), object
								.getBound()) > Node.enlargment(
								child.getBound(), object.getBound())) {
							selected = child;
						}
					}
				}
				return ChooseLeaf(selected, object);
			}
		}
		return null;
	}

	/**
	 * Ascend from a leaf node the root, adjusting covering rectangles and
	 * propagating node split as necessary
	 * 
	 * @param node
	 *            Starting node
	 * @param splited
	 *            Splited node of the started node
	 */
	private void AdjustTree(Node node, Node splited) {

		node.setBound(Node.encloseNode(node.getChilds()));
		if (node == root) {
			if (splited == null) {
			} else {
				// grow tree
				Node newRoot = new Node(null);
				node.setParent(newRoot);
				splited.setParent(newRoot);
				newRoot.addChild(node);
				newRoot.addChild(splited);
				this.root = newRoot;

			}

			return;
		}
		// If there was a split
		if (splited != null) {
			// If there is room add it
			if (node.getParent().getChilds().size() < M) {
				node.getParent().addChild(splited);
				AdjustTree(node.getParent(), null);
			} else {
				// Split the parent
				AdjustTree(node.getParent(), splited);
			}
		}
		AdjustTree(node.getParent(), null);
	}

	/**
	 * Insert a Spatial object into the tree
	 * 
	 * @param component
	 *            spatial object
	 */
	public void Insert(ISpatialObject2D component) {
		InternalInsert(root, component);
	}

	/**
	 * Recursive insert
	 * 
	 * @param root
	 *            curr node
	 * @param obj
	 *            object
	 */
	private void InternalInsert(Node root, ISpatialObject2D obj) {
		// Select a leaf in which to place object
		Node leaf = ChooseLeaf(root, obj);
		Node splitedNode = null;

		Node newNode = new Node(leaf, obj);
		// Put object into leaf if there is space
		if (leaf.getChilds().size() < M) {
			leaf.addChild(newNode);
		} else {
			Collection<Node> nodesForSplit = leaf.getChilds();
			nodesForSplit.add(newNode);
			List<Set<Node>> splited = Node.Split(nodesForSplit, m, M);
			// Add splited children to leaf to leaf
			leaf.setChild(new ArrayList<Node>(splited.get(0)));

			splitedNode = new Node();
			splitedNode.setChild(new ArrayList<Node>(splited.get(1)));
		}
		AdjustTree(leaf, splitedNode);

	}

	private void print(Node root) {
		if (root != null) {
			Integer p = null;
			if (root.getParent() != null) {
				p = root.getParent().getLevel();
			}
			System.out.println("Parent = " + p);
			System.out.println("Caption = " + root.getLevel());
			System.out.println("bound = " + root.getBound());
			System.out.println("objects: = " + root.getObject());
			System.out.println("---->");
			for (Node child : root.getChilds()) {
				child.setLevel(root.getLevel()
						+ root.getChilds().indexOf(child));
				print(child);
			}

		}
	}

	private void draw(Node node, Graphics g) {
		root.setLevel(0);
		g.setColor(getColorBound(node.getLevel()));
		((Graphics2D) g).setStroke(new BasicStroke(2));
		g.drawRect(node.getBound().x, node.getBound().y, node.getBound().width,
				node.getBound().height);
		for (Node child : node.getChilds()) {
			child.setLevel(node.getLevel() + 1);
			draw(child, g);
		}
	}

	public void draw(Graphics g) {
		draw(root, g);
	}

	public void print() {
		print(root);
	}

	public boolean isEmpty() {
		return root.getChilds().size() == 0;
	}

	/**
	 * Delete given object for the tree
	 * 
	 * @param root
	 *            root
	 * @param object
	 *            object
	 * @return true if the object was deleted
	 */
	private boolean Delete(Node root, ISpatialObject2D object) {
		// Find the leaf that contains the object
		Node leaf = findLeaf(root, object);
		// If no leaf is found return false
		if (leaf == null) {
			return false;
		}

		Node forDelete = null;
		// Find the child which contains the element
		for (Node child : leaf.getChilds()) {
			if (child.getBound().contains(object.getBound())) {
				forDelete = child;
			}
		}
		if (forDelete != null)
			// Remove elements
			leaf.removeChild(forDelete);
		
		// Condense tree to propagate changes
		CondenseTree(leaf);

		return false;
	}

	/**
	 * Find a leaf that contains the given object
	 * 
	 * @param root
	 *            root
	 * @param object
	 *            searched object
	 * @return the leaf that contain the object
	 */
	private Node findLeaf(Node root, ISpatialObject2D object) {
		// Check all entries to find the object
		if (root.isLeaf()) {
			// Check if matching child is found
			for (Node child : root.getChilds()) {
				if (child.getBound().contains(object.getBound())) {
					return root;
				}
			}
			// If no child is found return null
			return null;
		} else {
			for (Node child : root.getChilds()) {
				// Descent if object is in node bounds
				if (child.getBound().contains(object.getBound())) {
					Node candidate = findLeaf(child, object);
					if (candidate != null) {
						return candidate;
					}

				}
			}

			// If no child has found leaf return null
			return null;
		}
	}

	/**
	 * Propagate node elimination upwards the tree
	 * 
	 * @param node
	 *            node from which a record has been deleted
	 */
	private void CondenseTree(Node node) {
		Set<Node> orphans = new HashSet<Node>();

		while (node != root) {
			Node parent = node.getParent();
			if (node.getChilds().size() < m) {
				parent.removeChild(node);
				orphans.add(node);
				node = parent;
			} else {
				AdjustTree(node, null);
				break;
			}
		}

		// Insert all entries from orphans
		Set<ISpatialObject2D> objectToAdd = new HashSet<ISpatialObject2D>();
		for ( Node child : orphans ){
			objectToAdd.addAll(extraxtSpatialObject(child));
		}
		
		Insert(objectToAdd);
	}
	
	private Set<ISpatialObject2D> extraxtSpatialObject(Node root){
		Set<ISpatialObject2D> result = new HashSet<ISpatialObject2D>();
		
		for (Node child : root.getChilds()){
			if ( child.getObject() != null ){
				result.add(child.getObject());
			}
			else{
				result.addAll(extraxtSpatialObject(child));
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see index_structures.ISpatialIndex#Delete(components.ISpatialObject2D)
	 */
	@Override
	public boolean Delete(ISpatialObject2D object) {
		return Delete(root, object);

	}

	private Color getColorBound(int i) {
		if (i == 0)
			return Color.YELLOW;
		if (i == 1)
			return Color.GREEN;
		if (i == 2)
			return Color.BLUE;
		if (i == 3)
			return Color.ORANGE;
		return Color.MAGENTA;
	}

	/**
	 * 
	 */
	@Override
	public void Insert(Collection<ISpatialObject2D> collection) {
		for ( ISpatialObject2D object : collection ){
			Insert(object);
		}
		
	}

}
