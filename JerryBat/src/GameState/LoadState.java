package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.SwingWorker;

import Audio.AudioListener;
import Handlers.FontLoader;
import Main.GamePanel;

public class LoadState extends GameState {
    private int ticks=0;
    private int alpha=0;
	private int FADE_IN=90;
	private int LENGTH=90;
	private double FADE_OUT=90;

	private int count=0;
	private int loadingFilesNum=7;

	public LoadState(GameStateManager gsm) {
		super(gsm);
		start();
	}

    public void start() {
        new StartWorker().execute();
    }

    private class StartWorker extends SwingWorker<Void, Void> {
    	
    	public StartWorker() {
    		
		}
    	
        @Override
        protected Void doInBackground() throws Exception {
        	try{AudioListener.load("/Music/SE/menuclick.wav","menuClick");}finally{count++;}
    		try{AudioListener.load("/Music/LM/level1.mp3", "bgSound");}finally{count++;}
    		try{AudioListener.load("/Music/LM/menuBackgroundMusic.mp3", "menuBgMusic");}finally {count++;}
    		try{AudioListener.load("/Music/SE/coinCollect.mp3", "collectCoin");}finally{count++;}
    		try{AudioListener.load("/Music/SE/fireballSFX.mp3", "fireball");}finally{count++;}
    		
    		
    		try{FontLoader.loadFont("/Fonts/LittleBird.ttf", "menuTitleFont");}finally{count++;}
    		try{FontLoader.loadFont("/Fonts/pentaGram.ttf", "menuOptionsFont");}finally{count++;}
            return null;
        }

        @Override
        protected void done() {
        	try {
				//Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
            gsm.setState(GameStateManager.MENUSTATE);
        }
    }


	public void init() {
		
	}

	public void update() {
		ticks++;
		if(ticks < FADE_IN) {
			alpha = (int) (255 - 255 * (1.0 * ticks / FADE_IN));
			if(alpha < 0) {
				alpha = 0;
				ticks=0;
			}
		}
		if(ticks > FADE_IN + LENGTH) {
			alpha = (int) (255 * (1.0 * ticks - FADE_IN - LENGTH) / FADE_OUT);
			if(alpha > 255){
				alpha = 255;
				ticks=0;
			}
			
		}
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.red);
		g.setFont(FontLoader.getFont("loadStateFont", 24));
		String msg="Loading ... ";
		g.drawString(msg, GamePanel.WIDTH/2 ,GamePanel.HEIGHT/2 );
		g.drawRect(GamePanel.WIDTH/2, GamePanel.HEIGHT/2 + 50, loadingFilesNum*50 + 1, 31);
		g.setColor(Color.green);
		g.fillRect(GamePanel.WIDTH/2 + 2, GamePanel.HEIGHT/2 +52, count*50 -2,28 );
		g.setColor(new Color(0,0,0,alpha));
		g.fillRect(GamePanel.WIDTH/2, GamePanel.HEIGHT/2 - 20, loadingFilesNum*50 + 1, 31);
		
		
	}

	@Override
	public void handleInput() {
		
		
	}
}