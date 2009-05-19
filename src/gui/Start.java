package gui;

import java.awt.Container;

public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainApplication application = new MainApplication();
		Container contentPane = application.getContentPane();
		contentPane.add(new DrawingPanel());
		application.setVisible(true);
	}
}
