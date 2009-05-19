package rtree;

import components.ISpatialObject2D;
import components.SpatialObject2DImpl;

public class RTree {

	private Node root = null;

	// Max entries for a node
	private int M;

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
		root = new Node(m, M);
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

			if (node.isLeaf()) {
				return node;
			} else {
				Node selected = node.getChildNodes().get(0);
				for (Node child : node.getChildNodes()) {
					if (selected.enlargment(object) > child.enlargment(object)) {
						selected = child;
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
		if (node == root) {
			return;
		}
		Node parent = node.getParent();

		parent.Enclose();

		if (parent.getChildNodes().size() > M) {
			//parent.SplitNode(splited);
		}

		parent.addChild(splited);

	}

	public void Insert(ISpatialObject2D component) {
		InternalInsert(root, component);
	}

	private void InternalInsert(Node root, ISpatialObject2D component) {
//		Node leaf = ChooseLeaf(root, component);
//		Node splited = null;
//		if (leaf.getSpatialObjects().size() > M) {
//			Node newNode = new Node(m, M);
//			newNode.addSpatialObject(component);
//			splited = leaf.SplitNode(newNode);
//		} else {
//			leaf.addSpatialObject(component);
//		}
//
//		AdjustTree(leaf, splited);
	}

}
