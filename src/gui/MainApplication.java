package gui;

import index_structures.SpatialIndex;
import index_structures.SpatialIndexFactory;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainApplication extends JFrame {

	private static final long serialVersionUID = 1800617154369993903L;

	private Toolkit toolkit;

	private DrawingPanel drawingPanel;
	
	private ActionPanel actionPanel = new ActionPanel();;

	public MainApplication() {
		
		
		setSize(840, 640);
		setTitle("Spatial Indexing");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 2 - getWidth() / 2, size.height / 2
				- getHeight() / 2);

		// Panel for the drawing panel
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);
		
		drawingPanel = new DrawingPanel(SpatialIndexFactory
				.createSpatialIndex(SpatialIndex.RTree),actionPanel);
		
		// Create action panel
		
		actionPanel.setDrawingPanel(drawingPanel);
		panel.add(actionPanel);
		

		
		drawingPanel.setSize(700, 600);

		panel.add(drawingPanel);

	}

}
