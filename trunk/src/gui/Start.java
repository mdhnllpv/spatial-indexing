package gui;

import java.awt.Container;

import rtree.RTree;

public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainApplication application = new MainApplication();
		Container contentPane = application.getContentPane();
		RTree tree = new RTree(3,5);
		contentPane.add(new DrawingPanel(tree));
		application.setVisible(true);
	}
}
