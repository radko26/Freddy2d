package TileMapEditor;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class App{
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame("Tile Map Editor moded by RP");
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setContentPane(new MyPanel());
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - window.getWidth()) / 2);
		 int y = (int) ((dimension.getHeight() - window.getHeight()) / 2);
		window.setLocation(x, 20);
	}


	
}