package GameState;

import java.util.Map;
import java.util.Map.Entry;

import javax.sound.sampled.Clip;

import Audio.AudioListener;
import Handlers.FontLoader;
import Main.GamePanel;
import TileMapEditor.App;

public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
	private PauseState pauseState;
	private boolean paused;
	
	public static final int NUMGAMESTATES = 7;
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
	public static final int CREDITS = 2;
	public static final int LEVEL2STATE = 3;
	public static final int GAMEOVER=4;
	public static final int TILEMAPEDITOR=5;
	public static final int LOADSTATE = 6;
	
	public GameStateManager() {
		
		gameStates = new GameState[NUMGAMESTATES];
		AudioListener.init();
		FontLoader.init();
		FontLoader.loadFont("/Fonts/WalkwayExpand.ttf", "loadStateFont");
		pauseState = new PauseState(this);
		paused = false;
		
		currentState = LOADSTATE;
		loadState(currentState);
		
	}
	
	private void loadState(int state) {
		if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		if(state == LEVEL1STATE)
			gameStates[state] = new Level1State(this);
		if(state == CREDITS)
			gameStates[state] = new CreditsState(this);
		if(state == LEVEL2STATE)
			gameStates[state]= new Level2State(this);
		if(state == GAMEOVER)
			gameStates[state]= new GameOver(this);
		if(state == LOADSTATE)
			gameStates[state]=new LoadState(this);
		//if(state==TILEMAPEDITOR)
			//gameStates[state] = new App();
	}
	
	private void unloadState(int state) {
		gameStates[state] = null;
		//audio stop to be done
		System.out.println("stopping music");
		for(Entry<String, Clip> entry: AudioListener.getSize()){
			entry.getValue().stop();
		}
		
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
		//gameStates[currentState].init();
	}
	
	
	
	public void update() {
		if(paused) {
			pauseState.update();
			return;
		}
		if(gameStates[currentState] != null) gameStates[currentState].update();
	}
	
	public void draw(java.awt.Graphics2D g) {
		if(paused) {
			pauseState.draw(g);
			return;
		}
		if(gameStates[currentState] != null) gameStates[currentState].draw(g);
		else {
			g.setColor(java.awt.Color.BLACK);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		}
	}
	

	public void setPaused(boolean b) {
		paused = b;
	}

	
}