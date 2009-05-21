package index_structures.rtree;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import components.ISpatialObject2D;

/**
 * R-Tree node
 * 
 * @author Martin Obreshkov
 * 
 */
public class Node {

	/* List with children */
	private List<Node> childs = new ArrayList<Node>();

	/* If node is a leaf the spatial object to store */
	private ISpatialObject2D object;

	/* Node caption */
	private String caption;

	/* The rectangle bound of the node */
	private Rectangle bound;

	/* Parent node */
	private Node parent;

	/**
	 * Constructor
	 */
	public Node() {

	}

	/**
	 * Constructor
	 * 
	 * @param caption
	 *            Caption
	 */
	public Node(String caption) {
		this.caption = caption;
	}

	/**
	 * Constructor
	 * 
	 * @param caption
	 *            caption
	 * @param parent
	 *            parent
	 */
	public Node(String caption, Node parent) {
		this.caption = caption;
		this.parent = parent;
	}

	/**
	 * Constructor
	 * 
	 * @param caption
	 *            caption
	 * @param parent
	 *            parent
	 * @param object
	 *            spatial object
	 */
	public Node(String caption, Node parent, ISpatialObject2D object) {
		this.caption = caption;
		this.parent = parent;
		this.object = object;
		bound = object.getBound();
	}

	/**
	 * Add a child to child list and rebuild rectangle bound
	 * 
	 * @param node
	 *            child
	 */
	public void addChild(Node node) {
		this.childs.add(node);
		node.setParent(this);
		this.bound = Node.encloseNode(getChilds());
	}

	/**
	 * Remove child from child list bound is recalculated
	 * @param node
	 */
	public void removeChild(Node node) {
		this.childs.remove(node);
		this.bound = Node.encloseNode(getChilds());
	}

	/**
	 * 
	 * @param col
	 *            collection with ISpatialObject2D
	 * @param m
	 *            minimum entries in a node
	 * @param M
	 *            maximum entries in a node
	 * @return Select two entries to be the first objects of the group
	 */
	public static List<Node> PickSeed(Collection<Node> col) {

		if (col.size() < 2) {
			throw new IllegalArgumentException(
					"can't make pick seed with collectino that has less than 2 elements");
		}
		// Find the extreme points
		double left = 0;
		double right = 0;
		double down = 0;
		double up = 0;

		for (Node object : col) {
			left = Math.min(left, object.getBound().getX());
			right = Math.max(right, object.getBound().getX());
			up = Math.min(up, object.getBound().getY());
			down = Math.max(down, object.getBound().getY());
		}

		
		Node[] result = new Node[2];
		double maxDimensionX = 0;
		double maxDimensionY = 0;

		// Select the two nodes with extreme values by the two dimensions
		for (Node obj : col) {
			if (maxDimensionX < obj.getBound().getWidth() / (right - left)) {
				maxDimensionX = obj.getBound().getWidth() / (right - left);
				result[0] = obj;
			} else if (maxDimensionY < obj.getBound().getHeight() / (down - up)) {
				maxDimensionY = obj.getBound().getHeight() / (down - up);
				result[1] = obj;
			}
		}

		return Arrays.asList(result);
	}

	/**
	 * Pick next spatial object which will be classified from a collection
	 * 
	 * @param collection
	 *            - collection
	 * @param group1Bound
	 *            bound of group1
	 * @param group2Bound
	 *            bound of group2
	 * @return object for classification
	 */
	public static Node PickNext(Collection<Node> collection,
			Rectangle group1Bound, Rectangle group2Bound) {

		if (collection.size() < 1) {
			throw new IllegalArgumentException(
					"can't pick next from a collection with less than 1 element");
		}
		double max = 0;
		Node result = null;
		
		for (Node object : collection) {
			double d1 = enlargment(group1Bound, object.getBound());
			double d2 = enlargment(group2Bound, object.getBound());
			if (max < Math.abs(d1 - d2)) {
				result = object;
			}
		}
		return result;

	}

	/**
	 * Split collection with nodes into two sets
	 * @param collection collection with nodes
	 * @param m  minimum entries in a node
	 * @param M maximum entries in a node
	 * @return List with the 2 sets
	 */
	public static List<Set<Node>> Split(Collection<Node> collection, int m,
			int M) {

		// get start elements
		List<Node> startGroups = PickSeed(collection);

		Set<Node> group1 = new HashSet<Node>();
		Set<Node> group2 = new HashSet<Node>();
		group1.add(startGroups.get(0));
		group2.add(startGroups.get(1));

		collection.remove(startGroups.get(0));
		collection.remove(startGroups.get(1));

		while (!collection.isEmpty()) {

			// If minimum entries is reached copy all elements
			if (group1.size() + collection.size() < m) {
				group1.addAll(collection);
				collection.clear();
				break;
			}
			
			// If minimum entries is reached copy all elements
			if (group2.size() + collection.size() < m) {
				group2.addAll(collection);
				collection.clear();
				break;
			}

			// Get next object
			Node next = PickNext(collection, encloseNode(group1),
					encloseNode(group2));

			// Choose in which group to add
			if (enlargment(encloseNode(group1), next.getBound()) < enlargment(
					encloseNode(group2), next.getBound())) {
				group1.add(next);
			} else {
				group2.add(next);
			}
			collection.remove(next);

		}

		List<Set<Node>> result = new ArrayList<Set<Node>>();
		result.add(group1);
		result.add(group2);
		return result;

	}

	/**
	 * Find the minimum rectangle bound that contains all object's bounds
	 * 
	 * @param collection
	 *            objects
	 * @return rectangle bound
	 */
	public static Rectangle encloseSpatialObject(
			Collection<ISpatialObject2D> collection) {
		if (collection.size() == 0) {
			return null;
		}
		Rectangle bound = null;
		for (ISpatialObject2D object : collection) {

			if (bound == null) {
				bound = object.getBound();
				continue;
			} else {
				bound.add(object.getBound());
			}
		}

		return bound;
	}

	/**
	 * Find the minimum rectangle bound that contains all object's bounds
	 * 
	 * @param collection
	 *            node collection
	 * @return rectangle bound
	 */
	public static Rectangle encloseNode(Collection<Node> collection) {
		if (collection.size() == 0) {
			return null;
		}
		Rectangle bound = null;
		for (Node node : collection) {

			if (bound == null) {
				bound = (Rectangle) node.getBound().clone();
				continue;
			} else {
				bound.add(node.getBound());
			}
		}

		return bound;
	}

	/**
	 * Calculate the enlargement needed to union two rectangles
	 * 
	 * @param rect1
	 *            basic rectangle
	 * @param rect2
	 *            added rectangle
	 * @return union(rect1,rect2) - rect1
	 */
	public static double enlargment(Rectangle rect1, Rectangle rect2) {
		Rectangle newRectangle = (Rectangle) rect1.createUnion(rect2);
		return newRectangle.getHeight() * newRectangle.getWidth()
				- rect1.getHeight() * rect1.getWidth();
	}

	/**
	 * 
	 * @return true if a node is a leaf
	 */
	public boolean isLeaf() {
		for (Node node : childs) {
			if (node.childs.size() > 0) {
				return false;
			}
		}
		return true;
	}

	public List<Node> getChilds() {
		return childs;
	}

	public void setChild(List<Node> childs) {
		this.childs = childs;
		this.bound = Node.encloseNode(getChilds());
	}

	public ISpatialObject2D getObject() {
		return object;
	}

	public void setObject(ISpatialObject2D object) {
		this.object = object;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public Rectangle getBound() {
		return bound;
	}

	public void setBound(Rectangle bound) {
		this.bound = bound;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Node getParent() {
		return parent;
	}
}
