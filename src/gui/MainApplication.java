package gui;

import index_structures.SpatialIndex;
import index_structures.SpatialIndexFactory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainApplication extends JFrame {

	private static final long serialVersionUID = 1800617154369993903L;

	private Toolkit toolkit;

	private DrawingPanel drawingPanel;
	
	private JLabel xPos = new JLabel();
	
	private JLabel yPos = new JLabel();

	public MainApplication() {
		
		
		setSize(800, 600);
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
		
		// Create action panel
		JPanel actionPanel = new JPanel();
		panel.add(actionPanel);
		actionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		actionPanel.setBounds(700, 0, 100, 600);
		actionPanel.setLayout(null);
		

		drawingPanel = new DrawingPanel(SpatialIndexFactory
				.createSpatialIndex(SpatialIndex.RTree),xPos,yPos);
		drawingPanel.setSize(700, 600);

		List<String> comboItems = new ArrayList<String>();

		for (SpatialIndex i : SpatialIndex.values()) {
			comboItems.add(i.toString());
		}
		
		// Create combo box for the spatial structures
		JComboBox comboBox = new JComboBox(comboItems.toArray());
		comboBox.setBounds(0, 0, 90, 30);
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}

		});
		List<String> actionItems = new ArrayList<String>();

		for (MouseAction i : MouseAction.values()) {
			actionItems.add(i.toString());
		}
		JComboBox actionBox = new JComboBox(actionItems.toArray());
		actionBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				String action = (String) cb.getSelectedItem();
				drawingPanel.setMouseAction(MouseAction.valueOf(action));
				
			}
			
		});
				
		xPos.setBounds(0,100,40,20);
		yPos.setBounds(50,100,40,20);
		
		actionBox.setBounds(0,40,90,30);
		
		
		actionPanel.add(xPos);
		actionPanel.add(yPos);
		actionPanel.add(comboBox);
		actionPanel.add(actionBox);
		panel.add(drawingPanel);

	}

}
