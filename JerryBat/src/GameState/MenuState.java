package GameState;

import Handlers.FontLoader;
import Handlers.Keys;
import Main.GamePanel;
import TileMap.Background;
import TileMapEditor.App;
import java.awt.*;

import Audio.AudioListener;

public class MenuState extends GameState {

	private Background bg;

	private int currentChoice = 0;
	private String[] options = { "Start","Custom Level","Credits", "Quit"

	};

	private Color titleColor;
	private Font titleFont;

	private Font font;
	
	public MenuState(GameStateManager gsm) {

		super(gsm);

		try {

			bg = new Background("/Backgrounds/carbon.jpg", 0);
			//bg.setVector(1, 0);

			titleColor = new Color(255, 180, 13);
			
			titleFont = FontLoader.getFont("menuTitleFont",64);
			font =FontLoader.getFont("menuOptionsFont",36);

		} catch (Exception e) {
			e.printStackTrace();
		}
		AudioListener.setVolume("menuBgMusic", -10f);
		AudioListener.play("menuBgMusic");
		
	}

	public void init() {}

	public void update() {
		handleInput();
	}

	public void draw(final Graphics2D g) {
		g.clearRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		// draw bg
		bg.draw(g);

		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		
		String title = "Freddy the Bat";
		g.drawString(title, GamePanel.WIDTH / 2 - title.length() * 20,
				GamePanel.HEIGHT / 2 - 110);
		g.drawString("Adventures", GamePanel.WIDTH / 3 - 10,
				GamePanel.HEIGHT / 2 - 60);

		// draw menu options
		for (int i = 0; i < options.length; i++) {
			g.setColor(Color.RED);
			g.setFont(font);

			if (i == currentChoice) {
				g.setColor(Color.YELLOW);
			} else {
				g.setColor(Color.RED);
				g.setFont(font);
			}
			g.drawString(options[i], GamePanel.WIDTH / 2 -50, GamePanel.HEIGHT
					/ 2 + i * 35);
		}
	}



	private void select() {
		if(currentChoice == 0) {
			//PlayerSave.init();
			gsm.setState(GameStateManager.LEVEL1STATE);
		}else if(currentChoice == options.length-3){
			try{
				App app = new App();
				String args[]={};
				app.main(args);
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		else if(currentChoice == options.length-2) {
			gsm.setState(GameStateManager.CREDITS);
		}
		else if(currentChoice == options.length-1) {
			System.exit(0);
		}
	}

	public void handleInput() {
		
		if (Keys.isPressed(Keys.ENTER))
			select();
		if (Keys.isPressed(Keys.UP)) {
			if (currentChoice >= 0) {
				currentChoice--;
				AudioListener.play("menuClick");
				
				
				
			}
			if(currentChoice==-1){
				currentChoice=options.length-1;
			}
		}
		if (Keys.isPressed(Keys.DOWN)) {
			if (currentChoice < options.length) {
				currentChoice++;
				AudioListener.play("menuClick");
				
			}
			if(currentChoice == options.length){
				currentChoice=0;
			}
		}
	}

}
