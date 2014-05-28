package GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import Entity.PlayerSave;
import Handlers.Keys;
import Main.GamePanel;
import Audio.AudioListener;
import Audio.AudioPlayer;
public class GameOver extends GameState{

	private AudioPlayer audio;
	
	
	
	public GameOver(GameStateManager gsm) {
		super(gsm);
		
		init();
		
	}

	@Override
	public void init() {
		AudioListener.load("/Music/LM/gameOver.mp3","gameOver");
		AudioListener.play("gameOver");
	}

	@Override
	public void update() {
		handleInput();
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH,GamePanel.HEIGHT);
		
		g.setColor(Color.CYAN);
		g.drawRect(GamePanel.WIDTH/4, GamePanel.HEIGHT/4,GamePanel.WIDTH/2, GamePanel.HEIGHT/2 );
		
		g.drawString("Game Over", GamePanel.WIDTH/4,GamePanel.HEIGHT/4);
		g.drawString("Your score: "+ PlayerSave.getScore(), 155,150);
		g.drawString("Played Time: " + convertTime(PlayerSave.getTime()), 155,165);
		g.drawString("Press M to continue", 155,180);
		
		
		
	}
	public String convertTime(long time){
		long seconds = TimeUnit.MILLISECONDS.toSeconds(time);	
	    return seconds/60+ ":" + seconds%60;
	}
	

	@Override
	public void handleInput() {
		if(Keys.isPressed(Keys.M) ){
			gsm.setState(GameStateManager.MENUSTATE);
		}
		
	}
	

}
