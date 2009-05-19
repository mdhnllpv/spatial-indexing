package rtree;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import components.ISpatialObject2D;
import components.SpatialObject2DImpl;

public class Node {

	private Rectangle bound = new Rectangle();

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
	public double enlargment(Node node){
		
		return enlargment(node.getBound());
	}
	
	private double enlargment(Rectangle rectangle){
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
	}

	public void addSpatialObject(ISpatialObject2D component) {
		bound.add(component.getBound());
		this.spatialObjects.add(component);
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
	public Node SplitNode() {
		Node[] startGroups = PickSeeds();
		while (!this.getChildNodes().isEmpty()){
			
		}
		
		return null;
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
			if ( max < Math.abs(node.enlargment(group1) - node.enlargment(group2))){
				max = Math.abs(node.enlargment(group1) - node.enlargment(group2));
				result = node;
			}
		}
		return result;
	}

	/**
	 * Adjust the node bound so it enclosed all children bounds
	 */
	public void Enclose() {
		Rectangle rect = new Rectangle();
		for (Node child : childNodes) {
			rect.add(child.bound);
		}
		bound = rect;
	}
	

}
