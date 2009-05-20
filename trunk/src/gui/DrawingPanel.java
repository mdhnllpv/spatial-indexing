package gui;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import rtree.RTree;

import components.SpatialObject2DImpl;
import components.ISpatialObject2D;

public class DrawingPanel extends JPanel implements MouseMotionListener {

	private static final long serialVersionUID = -8669154925460990333L;

	private Set<ISpatialObject2D> components;
	
	private ISpatialObject2D currunt = null;
	
	private RTree tree;
	
	public DrawingPanel( RTree tree) {
		
		this.tree = tree;
		
		components = new HashSet<ISpatialObject2D>();
		
		addMouseListener(new MouseAdapterWrapper());
		addMouseMotionListener(this);
		setVisible(true);
	}

	public void mouseDragged(MouseEvent me) {
		currunt.addPoint(me.getPoint());
		repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);
		for (ISpatialObject2D component : components){
			component.paintComponent(g);
		}
		if ( !tree.isEmpty() ){
			tree.draw(g);
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

	}
	
	private class MouseAdapterWrapper extends MouseAdapter{
		
		public MouseAdapterWrapper(){
			super();
		}
		
		@Override
		public void mousePressed(MouseEvent evt) {
			currunt = new SpatialObject2DImpl();
			components.add(currunt);
		}

		@Override
		public void mouseReleased(MouseEvent evt) {
			currunt.setCaption("R" + components.size());
			repaint();
			currunt.getBound();
			tree.Insert(currunt);
		}
	}
}