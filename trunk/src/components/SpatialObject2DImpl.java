package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class SpatialObject2DImpl implements ISpatialObject2D {

	private List<Point> points;

	private Rectangle rectangleBound;
	
	private boolean isComplete;
	
	private String caption = null;

	public SpatialObject2DImpl() {
		this.points = new ArrayList<Point>();
		this.rectangleBound = new Rectangle();
		this.isComplete = false;
	}

	private void calculateBound() {
		if (points.size() > 1) {
			int left = points.get(0).x;
			int right = points.get(0).x;
			int up = points.get(0).y;
			int down = points.get(0).y;
			
			for (Point point : points) {
				left = Math.min(left, point.x);
				right = Math.max(right, point.x);
				up = Math.min(up, point.y);
				down = Math.max(down, point.y);
			}
			
			rectangleBound.x = left;
			rectangleBound.y = up;
			rectangleBound.height = down - up;
			rectangleBound.width = right - left;
		}
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
		g.setColor(Color.BLACK);
		if (points.size() > 1) {
			Point first = points.get(0);
			for (Point second : points.subList(1, points.size() - 1)) {
				g.drawLine(first.x, first.y, second.x, second.y);
				first = second;
			}
		}
		
		if ( isComplete ){
			paintBound(g);
		}
	}

	private void paintBound(Graphics g) {
		calculateBound();
		g.drawString(caption, rectangleBound.x, rectangleBound.y);
		g.setColor(Color.RED);
		g.drawRect(rectangleBound.x,rectangleBound.y, rectangleBound.width, rectangleBound.height);
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	public boolean isComplete() {
		return isComplete;
	}

	@Override
	public void setCaption(String caption) {
		this.caption = caption;
		
	}

	@Override
	public Rectangle getBound() {
		return this.rectangleBound;
	}

	@Override
	public void setBound(Rectangle bound) {
		this.rectangleBound = bound;
		
	}
	
}