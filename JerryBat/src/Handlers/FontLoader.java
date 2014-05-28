package Handlers;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;
import java.util.HashMap;

public class FontLoader {
	
	private static final Font SERIF_FONT = new Font("serif", Font.PLAIN, 34);
	private static HashMap<String, Font> fonts;
	public static void init(){
		fonts=new HashMap<>();
	}
	
	public static void loadFont(String path,String name) {
	    Font font = null;
	    try {
	       
	    	InputStream f = 
	                FontLoader.class.getResourceAsStream(path);
	        font = Font.createFont(Font.TRUETYPE_FONT,f);
	        GraphicsEnvironment ge = GraphicsEnvironment
	                .getLocalGraphicsEnvironment();

	        ge.registerFont(font);

	    } catch (Exception e) {
	    	e.printStackTrace();
	        font = SERIF_FONT;
	    }
	    fonts.put(name,font);
	}
	public static Font getFont(String name,float size){
		return fonts.get(name).deriveFont(size);
	}

}
