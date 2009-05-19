package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MainApplication extends JFrame{
	
	private static final long serialVersionUID = 1800617154369993903L;

	public MainApplication(){
		setSize(800, 600);
		setTitle("Spatial Indexing");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width/2 - getWidth()/2, 
					size.height/2 - getHeight()/2);
		
	}

}
