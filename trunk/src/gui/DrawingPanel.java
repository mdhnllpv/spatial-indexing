package gui;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import components.Component2D;
import components.IComponent;

public class DrawingPanel extends JPanel implements MouseMotionListener {

	private static final long serialVersionUID = -8669154925460990333L;

	private Set<IComponent> components;
	
	private IComponent currunt = null;

	public DrawingPanel() {
		
		components = new HashSet<IComponent>();
		
		// Add mouse listener
		addMouseListener(new MouseAdapterWrapper());
		// Add mouse motion listener
		addMouseMotionListener(this);
		setVisible(true);
	}

	public void mouseDragged(MouseEvent me) {
		System.out.println("dragged");
		currunt.addPoint(me.getPoint());
		repaint();
	}

	public void paint(Graphics g) {
		for (IComponent component : components){
			component.paintComponent(g);
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

	}
	
	private class MouseAdapterWrapper extends MouseAdapter{
		
		@Override
		public void mousePressed(MouseEvent evt) {
			currunt = new Component2D();
			components.add(currunt);
		}

		@Override
		public void mouseClicked(MouseEvent evt) {
			System.out.println("Mouse clicked");
		}
	}
}