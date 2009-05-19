package rtree;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import components.ISpatialObject2D;

public class Node {

	private Rectangle bound = null;

	private List<Node> childNodes = new ArrayList<Node>();

	private List<ISpatialObject2D> spatialObjects = new ArrayList<ISpatialObject2D>();

	private Node parent;

	// min number of nodes in a node
	private int m;
	// max number of nodes in a node
	private int M;

	/**
	 * Constructor
	 * 
	 * @param m
	 * @param M
	 */
	public Node(int m, int M) {
		this.m = m;
		this.m = M;

	}

	/**
	 * 
	 * @return True if node is a leaf
	 */
	public boolean isLeaf() {
		return childNodes.size() == 0;
	}

	/**
	 * 
	 * @param component
	 *            component
	 * @return the enlargement needed to bound the spatial object
	 */
	public double enlargment(ISpatialObject2D component) {

		return enlargment(component.getBound());
	}

	/**
	 * 
	 * @param node
	 * @return the enlargement needed to bound the node
	 */
	public double enlargment(Node node) {

		return enlargment(node.getBound());
	}

	private double enlargment(Rectangle rectangle) {
		Rectangle newRectangle = (Rectangle) bound.createUnion(rectangle);
		return newRectangle.getHeight() * newRectangle.getWidth()
				- bound.getHeight() * bound.getWidth();
	}

	public List<Node> getChildNodes() {
		return childNodes;
	}

	/**
	 * Add child node to the node
	 * 
	 * @param child
	 *            Child node
	 */

	public void addChild(Node child) {
		childNodes.add(child);
		this.enclose();
	}

	/**
	 * Add new spatial object to the node structure
	 * 
	 * @param obj
	 *            Object
	 */
	public void addSpatialObject(ISpatialObject2D obj) {
		if (this.spatialObjects.isEmpty()) {
			bound = (Rectangle) obj.getBound().clone();
		} else {
			bound.add(obj.getBound());
		}
		this.spatialObjects.add(obj);
	}

	public List<ISpatialObject2D> getSpatialObjects() {
		return spatialObjects;
	}

	public void setBound(Rectangle bound) {
		this.bound = bound;
	}

	public Rectangle getBound() {
		return bound;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Node getParent() {
		return this.parent;
	}

	/**
	 * Split the node into two nodes which satisfy the node conditions for
	 * r-tree
	 * 
	 * @param node
	 *            - The spatial object to add
	 * @return - the splited node
	 */
	public List<Set<Node>> SplitNode() {
		Node[] startGroups = PickSeeds();
		
		Set<Node> set0 = new HashSet<Node>();
		Set<Node> set1 = new HashSet<Node>();
		
		set0.add(startGroups[0]);
		set1.add(startGroups[1]);

		this.getChildNodes().remove(startGroups[0]);
		this.getChildNodes().remove(startGroups[1]);

		while (!this.getChildNodes().isEmpty()) {
			Node next = pickNext(startGroups[0], startGroups[1]);

			if (startGroups[0].enlargment(next) < startGroups[1]
					.enlargment(next)) {
				set0.add(next);
			} else {
				set1.add(next);
			}
			this.getChildNodes().remove(next);
		}

		List<Set<Node>> res = new ArrayList<Set<Node>>();
		res.add(set0);
		res.add(set1);
		
		return  res;
	}

	/**
	 * Select two entries to be the first objects of the group
	 * 
	 * @return array containing the selected 2 elements
	 */
	private Node[] PickSeeds() {
		double left = 0;
		double right = 0;
		double down = 0;
		double up = 0;
		for (Node node : this.getChildNodes()) {
			left = Math.min(left, node.getBound().getX());
			right = Math.max(right, node.getBound().getX());
			up = Math.min(up, node.getBound().getY());
			down = Math.max(down, node.getBound().getY());
		}

		Node[] result = new Node[2];
		result[0] = new Node(this.m, this.M);
		result[1] = new Node(this.m, this.M);
		double maxDimensionX = 0;
		double maxDimensionY = 0;
		for (Node node : this.getChildNodes()) {
			if (maxDimensionX < node.getBound().getWidth() / (right - left)) {
				maxDimensionX = node.getBound().getWidth() / (right - left);
				result[0] = node;
			} else if (maxDimensionY < node.getBound().getHeight()
					/ (down - up)) {
				maxDimensionY = node.getBound().getHeight() / (down - up);
				result[1] = node;
			}
		}
		return result;
	}

	/**
	 * Select one remaining element for classification
	 * 
	 * @return the classified element
	 */
	private Node pickNext(Node group1, Node group2) {
		double max = 0;
		Node result = null;
		for (Node node : this.getChildNodes()) {
			if (max < Math.abs(node.enlargment(group1)
					- node.enlargment(group2))) {
				max = Math.abs(node.enlargment(group1)
						- node.enlargment(group2));
				result = node;
			}
		}
		return result;
	}

	/**
	 * Adjust the node bound so it enclosed all children bounds
	 */
	public void enclose() {
		if (childNodes.isEmpty()) {
			return;
		} else if (childNodes.size() == 1) {
			this.bound = childNodes.get(0).getBound();
		} else {
			Rectangle rect = (Rectangle) childNodes.get(0).getBound().clone();
			for (Node child : childNodes) {
				rect.add(child.getBound());
			}
			this.bound = rect;
		}
	}
}
