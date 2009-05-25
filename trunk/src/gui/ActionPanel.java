package gui;

import index_structures.IDrawableSpatialIndex;
import index_structures.SpatialIndex;
import index_structures.SpatialIndexFactory;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ActionPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2862070783185585353L;

	private JLabel xPos;
	private JLabel yPos;
	private JLabel selected;
	private JComboBox actionBox;
	
	private DrawingPanel drawingPanel;

	public ActionPanel() {
		super();
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setBounds(700, 0, 100, 600);
		setLayout(null);

		xPos = new JLabel();
		yPos = new JLabel();
		selected = new JLabel();

		List<String> actionItems = new ArrayList<String>();

		for (MouseAction i : MouseAction.values()) {
			actionItems.add(i.toString());
		}
		actionBox = new JComboBox(actionItems.toArray());
		actionBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				String action = (String) cb.getSelectedItem();
				drawingPanel.setMouseAction(MouseAction.valueOf(action));

			}

		});

		List<SpatialIndex> comboItems = new ArrayList<SpatialIndex>();

		for (SpatialIndex i : SpatialIndex.values()) {
			comboItems.add(i);
		}
		JComboBox comboBox = new JComboBox(comboItems.toArray());
		comboBox.setBounds(0, 0, 90, 30);
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				SpatialIndex action = (SpatialIndex) cb.getSelectedItem();
				IDrawableSpatialIndex spatial = SpatialIndexFactory
				.createSpatialIndex(action);
				drawingPanel.setSpatialIndex(spatial);
			}

		});

		xPos.setBounds(0, 100, 40, 20);
		yPos.setBounds(50, 100, 40, 20);
		selected.setBounds(0, 200, 100, 20);
		actionBox.setBounds(0, 40, 90, 30);

		add(xPos);
		add(yPos);
		add(selected);
		add(comboBox);
		add(actionBox);
	}

	public JLabel getXPos() {
		return xPos;
	}

	public void setXPos(JLabel pos) {
		xPos = pos;
	}

	public JLabel getYPos() {
		return yPos;
	}

	public void setYPos(JLabel pos) {
		yPos = pos;
	}

	public JLabel getSelected() {
		return selected;
	}

	public void setSelected(JLabel selected) {
		this.selected = selected;
	}

	public JComboBox getActionBox() {
		return actionBox;
	}

	public void setActionBox(JComboBox actionBox) {
		this.actionBox = actionBox;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public DrawingPanel getDrawingPanel() {
		return drawingPanel;
	}

	public void setDrawingPanel(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
}
