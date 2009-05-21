package index_structures.rtree;

import index_structures.ISpatialIndex;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import components.ISpatialObject2D;
import components.SpatialObject2DImpl;

public class RTree implements ISpatialIndex{

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
		root = new Node("root", null);
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
				Node newRoot = new Node("root", null);
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

		Node newNode = new Node(null, leaf, obj);
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
			String p = null;
			if (root.getParent() != null) {
				p = root.getParent().getCaption();
			}
			System.out.println("Parent = " + p);
			System.out.println("Caption = " + root.getCaption());
			System.out.println("bound = " + root.getBound());
			System.out.println("objects: = " + root.getObject());
			System.out.println("---->");
			for (Node child : root.getChilds()) {
				child.setCaption(root.getCaption()
						+ root.getChilds().indexOf(child));
				print(child);
			}

		}
	}
	
	private void draw(Node node,Graphics g){
		g.setColor(Color.BLUE);
		//g.drawString(node.getCaption(), node.getBound().x, node.getBound().y);
		g.drawRect(node.getBound().x, node.getBound().y, node.getBound().width, node.getBound().height);
		for ( Node child : node.getChilds()){
			child.setCaption(root.getCaption()
					+ root.getChilds().indexOf(child));
			draw(child,g);
		}
	}
	
	public void draw(Graphics g){
		draw(root,g);
	}

	public void print() {
		print(root);
	}
	
	public boolean isEmpty(){
		return root.getChilds().size() == 0;
	}

}
