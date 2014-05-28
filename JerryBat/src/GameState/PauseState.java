package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Entity.PlayerSave;
import Handlers.Keys;
import Main.GamePanel;



public class PauseState extends GameState {
	
	private Font font;
	
	public PauseState(GameStateManager gsm) {
		
		super(gsm);
		
		// fonts
		font = new Font("Century Gothic", Font.PLAIN, 20);
		
	}
	
	public void init() {}
	
	public void update() {
		handleInput();
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString("Game Paused..", 90, 90);
		g.drawString("Press 'Esc' to continue..", GamePanel.WIDTH/4, GamePanel.HEIGHT/2);
		g.drawString("or", GamePanel.WIDTH/4, GamePanel.HEIGHT/2 +20);
		g.drawString("'M' to return back in the main", GamePanel.WIDTH/4, GamePanel.HEIGHT/2 +40);
	}
	
	public void handleInput() {
		//System.out.println("stigam");
		if(Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(false);
		if(Keys.isPressed(Keys.M)) {
			gsm.setPaused(false);
			PlayerSave.nullPlayer();
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}
}
