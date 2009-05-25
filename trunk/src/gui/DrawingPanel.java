package gui;

import index_structures.IDrawableSpatialIndex;
import index_structures.ISpatialIndex;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import components.IDrawableSpatialObject2D;
import components.ISpatialObject2D;
import components.SpatialObject2DImpl;

public class DrawingPanel extends JPanel implements MouseMotionListener {

	private static final long serialVersionUID = -8669154925460990333L;

	private IDrawableSpatialObject2D currunt = null;

	private IDrawableSpatialIndex spatialIndex;

	private MouseAction mouseAction = MouseAction.INSERT;

	private ActionPanel actionPanel;

	public DrawingPanel(IDrawableSpatialIndex tree, ActionPanel actionPanel) {

		this.spatialIndex = tree;
		this.actionPanel = actionPanel;

		addMouseListener(new MouseAdapterWrapper());
		addMouseMotionListener(this);
		setVisible(true);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	public void mouseDragged(MouseEvent me) {
		spatialIndex.onMouseDragged(me, currunt, mouseAction);
		repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);
		if (!spatialIndex.isEmpty()) {
			spatialIndex.draw(g);
		}

		if (currunt != null)
			currunt.draw(g);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		actionPanel.getXPos().setText("X:" + String.valueOf(arg0.getX()));
		actionPanel.getYPos().setText("Y:" + String.valueOf(arg0.getY()));

	}

	private class MouseAdapterWrapper extends MouseAdapter {

		public MouseAdapterWrapper() {
			super();
		}

		@Override
		public void mousePressed(MouseEvent evt) {
			currunt = new SpatialObject2DImpl();
			currunt.setColor(Color.LIGHT_GRAY);
			currunt.addPoint(evt.getPoint());
		}

		@Override
		public void mouseClicked(MouseEvent evt) {
		}

		@Override
		public void mouseReleased(MouseEvent evt) {

			if (currunt != null) {
				currunt.getBound();

				if (mouseAction.equals(MouseAction.INSERT)) {
					currunt.setColor(Color.YELLOW);
					spatialIndex.Insert(currunt);

				} else if (mouseAction.equals(MouseAction.DELETE)) {
					spatialIndex.Delete(currunt);

				} else if (mouseAction.equals(MouseAction.SEARCH)) {
					Collection<ISpatialObject2D> found = spatialIndex
							.Search(currunt);
					actionPanel.getSelected().setText(
							"found: " + String.valueOf(found.size()));
				}
				repaint();
				currunt = null;
			}
		}
	}

	public ISpatialIndex getSpatialIndex() {
		return spatialIndex;
	}

	public void setSpatialIndex(IDrawableSpatialIndex spatialIndex) {
		this.spatialIndex = spatialIndex;
		repaint();
	}

	public void setMouseAction(MouseAction mouseAction) {
		this.mouseAction = mouseAction;
	}

	public MouseAction getMouseAction() {
		return mouseAction;
	}
}