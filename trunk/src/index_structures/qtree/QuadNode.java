package index_structures.qtree;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Set;

/**
 * Abstract class for quadtree nodes.
 */
public abstract class QuadNode {
	/**
	 * The rectangle for this node
	 */
	protected Rectangle rect;

	/**
	 * Constructor for a Quadtree node with coordinates and size for the
	 * rectangle
	 * 
	 * @param x
	 *            the x coordinate for the rectangle
	 * @param y
	 *            the y coordinate for the rectangle
	 * @param w
	 *            the width for the rectangle
	 * @param h
	 *            the height for the rectangle
	 */
	public QuadNode(int x, int y, int w, int h) {
		super();
		rect = new Rectangle(x, y, w, h);
		System.out.println("x = " + x);
		System.out.println("y = " + y);
		System.out.println("w = " + w);
		System.out.println("h = " + h);
	}

	/**
	 * Inserts the point into this node. Insertion may result in splitting a
	 * leaf into a branch. Thus the node is returned from this operation which
	 * may be different from this node. It allows the parent node to reset this
	 * node.
	 * 
	 * @param point
	 *            the point to be inserted
	 * @return the node, which may be this node or a new branch
	 * @throws QuadTreeInsertionException
	 *             thrown when point is not within the rect of this node
	 */
	public abstract QuadNode insert(Point point);

	/**
	 * Returns all the points stored in this nodes or its subnodes which are
	 * within the given rectable r
	 * 
	 * @param r
	 *            the rectangle the points should lie in.
	 * @return the array of points contained in r.
	 */
	public abstract Set<Point> findPoints(Rectangle r);

	/**
	 * Draws this node on <code>Window</code>
	 */
	public void draw(Graphics g) {
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
	}

	/**
	 * Tests if the point p is within the rectangle of this node
	 * 
	 * @param p
	 *            the point
	 * @return true, if the point p is contained in the rectangle of this node,
	 *         false otherwise
	 */
	protected boolean contains(Point p) {
		return rect.contains(p);
	}
}
