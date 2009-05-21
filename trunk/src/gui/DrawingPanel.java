package gui;

import index_structures.ISpatialIndex;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import components.ISpatialObject2D;
import components.SpatialObject2DImpl;

public class DrawingPanel extends JPanel implements MouseMotionListener {

	private static final long serialVersionUID = -8669154925460990333L;

	private Set<ISpatialObject2D> components;

	private ISpatialObject2D currunt = null;

	private ISpatialIndex spatialIndex;

	private MouseAction mouseAction = MouseAction.INSERT;

	public DrawingPanel(ISpatialIndex tree) {

		this.spatialIndex = tree;

		components = new HashSet<ISpatialObject2D>();

		addMouseListener(new MouseAdapterWrapper());
		addMouseMotionListener(this);
		setVisible(true);

		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	public void mouseDragged(MouseEvent me) {
		if (mouseAction.equals(MouseAction.INSERT)) {
			currunt.addPoint(me.getPoint());
			repaint();
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
		for (ISpatialObject2D component : components) {
			component.paintComponent(g);
		}
		if (!spatialIndex.isEmpty()) {
			spatialIndex.draw(g);
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

	}

	private class MouseAdapterWrapper extends MouseAdapter {

		public MouseAdapterWrapper() {
			super();
		}

		@Override
		public void mousePressed(MouseEvent evt) {
			if (mouseAction.equals(MouseAction.INSERT)) {
				currunt = new SpatialObject2DImpl();
				components.add(currunt);
			} else if (mouseAction.equals(MouseAction.DELETE)) {
				Rectangle delete = new Rectangle(evt.getX(), evt.getY(), 1, 1);
				spatialIndex.Delete(new SpatialObject2DImpl(delete));
				ISpatialObject2D forDelete = null;
				for (ISpatialObject2D component : components) {
					if (component.getBound().contains(delete)) {
						forDelete = component;
					}
				}
				if (forDelete != null)
					components.remove(forDelete);
				repaint();
			}
		}

		@Override
		public void mouseClicked(MouseEvent evt) {

		}

		@Override
		public void mouseReleased(MouseEvent evt) {
			if (mouseAction.equals(MouseAction.INSERT)) {
				if (currunt.getPoints().size() > 0) {
					repaint();
					currunt.getBound();
					spatialIndex.Insert(currunt);
					currunt = null;
				}
			}
		}
	}

	public ISpatialIndex getSpatialIndex() {
		return spatialIndex;
	}

	public void setSpatialIndex(ISpatialIndex spatialIndex) {
		this.spatialIndex = spatialIndex;
	}

	public void setMouseAction(MouseAction mouseAction) {
		this.mouseAction = mouseAction;
	}

	public MouseAction getMouseAction() {
		return mouseAction;
	}
}