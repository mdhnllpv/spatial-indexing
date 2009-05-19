package components;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Component2D implements IComponent {

	private List<Point> points;

	private Rectangle rectangleBound;

	public Component2D() {
		this.points = new ArrayList<Point>();
		this.rectangleBound = new Rectangle();
	}

	/*
	 * @see components.IComponent#getBound()
	 */
	@Override
	public Rectangle getBound() {
		return rectangleBound;
	}

	/*
	 * @see components.IComponent#getPoints()
	 */
	@Override
	public List<Point> getPoints() {
		return points;
	}
	
	/*
	 * @see components.IComponent#addPoint(java.awt.Point)
	 */
	@Override
	public void addPoint(Point point) {
		points.add(point);
	}

	/*
	 * @see components.IComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		if (points.size() > 1) {
			Point first = points.get(0);
			for (Point second : points.subList(1, points.size() - 1)) {
				g.drawLine(first.x, first.y, second.x, second.y);
				first = second;
			}
		} else {
		}

	}

}
