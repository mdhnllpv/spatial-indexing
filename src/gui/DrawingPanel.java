package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel implements MouseMotionListener {

	private static final long serialVersionUID = -8669154925460990333L;

	private int mX, mY;

	public DrawingPanel() {
		addMouseListener(new MouseAdapterWrapper());
		addMouseMotionListener(this);
		setVisible(true);
	}

	public void mouseDragged(MouseEvent me) {
		mX = (int) me.getPoint().getX();
		mY = (int) me.getPoint().getY();
		repaint();
	}

	public void paint(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(mX, mY, 5, 5);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	private class MouseAdapterWrapper extends MouseAdapter{
		
		@Override
		public void mousePressed(MouseEvent evt) {
			System.out.println("Mouse pressed");
		}

		@Override
		public void mouseClicked(MouseEvent evt) {
			System.out.println("Mouse clicked");
		}
	}
}