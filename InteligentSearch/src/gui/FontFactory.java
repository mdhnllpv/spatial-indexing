package gui;

import java.awt.Font;
import java.util.HashMap;
import java.util.Vector;

public class FontFactory {
	
	public static HashMap<String,String> fonts;
	public static HashMap<String,Integer> styles;
	public static Vector<Integer> size;
	
	public final static String defaultFont = Font.DIALOG;
	public final static Integer defaultStyle = Font.PLAIN;
	public final static Integer defaultSize = 12;
 	static {
		fonts = new HashMap<String,String>();
		fonts.put("dialog",Font.DIALOG);
		fonts.put("monospaced",Font.MONOSPACED);
		fonts.put("serif", Font.SERIF);
		fonts.put("sansserif",Font.SANS_SERIF);
		
		styles = new HashMap<String, Integer>();
		styles.put("italic", Font.ITALIC);
		styles.put("bold", Font.BOLD);
		styles.put("plain", Font.PLAIN);
		
		size = new Vector<Integer>();
		for ( int i = 12; i < 30; i++){
			size.add(i);
		}
	}
}
