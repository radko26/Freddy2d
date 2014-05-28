package GameState;

import Main.GamePanel;
import TileMap.*;
import Entity.*;
import Entity.Enemies.Creeper;
import Entity.Enemies.Skeleton;
import Entity.Enemies.Snail;
import Handlers.Keys;

import java.awt.*;
import java.util.ArrayList;

import Audio.AudioListener;
import Audio.AudioPlayer;

public class Level1State extends GameState {

	private TileMap tileMap;
	private Background bg;
	private HUD hud;
	private Player player;
	private ArrayList<Enemy> enemies;
	private ArrayList<Coin> coins;
	private ArrayList<Explosion> explosions;

	private AudioPlayer audio;
	
	
	public Level1State(GameStateManager gsm) {
		super(gsm);
		init();
	}

	public void init() {

		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/stoneTiles.png");
		tileMap.loadMap("/Maps/Block1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(0.07);

		bg = new Background("/Backgrounds/night.jpg", 0);

		player = new Player(tileMap);
		player.setPosition(100, 387);

		setEntity();
		
		hud = new HUD(player);
		explosions = new ArrayList<>();
		
		
		AudioListener.play("bgSound");
		AudioListener.setVolume("bgSound", -10.5f);

	}
	
	private void setEntity(){
		enemies = new ArrayList<Enemy>();
		coins = new ArrayList<>();
		
		Coin c=new Coin(tileMap);
		Snail s;
		Creeper creeper;
		Point[] points = new Point[] {
			new Point(200, 100),
			new Point(860, 100),
			new Point(1507, 150),
			new Point(1680, 150),
			new Point(1800, 150),
			new Point(2710, 150),
			new Point(2762, 150)
		};
		for(int i = 0; i < points.length; i++) {
			s = new Snail(tileMap);
			s.setPosition(points[i].x, points[i].y);
			creeper = new Creeper(tileMap, player);
			creeper.setPosition(points[i].x, points[i].y);
			enemies.add(s);
			enemies.add(creeper);
		}
		for(int i = 0; i < points.length; i++) {
			c=new Coin(tileMap);
			c.setPosition(points[i].x, points[i].y);
			coins.add(c);
		}

	}
	
	

	public void update() {

		//handle input
		handleInput();
		
		// update player
		player.update();
		if (player.gety() > tileMap.getHeight()) {
			//player.setDead();
			player.setHealth(player.getHealth()-1);
			player.setPosition(100, 100);
			
		}
		if(player.getHealth()<=0){
			player.setDead();
			player.reset();
		}
		if(player.getLives()==0){
			gsm.setState(GameStateManager.GAMEOVER);
		}
		
		if(player.getx() > tileMap.getWidth()){
			PlayerSave.setHealth(player.getHealth());
			PlayerSave.setStartTime(player.getStartTime());
			PlayerSave.setEndTime(player.getEndTime());
			PlayerSave.setLives(player.getLives());
			PlayerSave.setScore(player.getScore());
			gsm.setState(GameStateManager.LEVEL2STATE);
		}
		
		tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(),
				GamePanel.HEIGHT / 2 - player.gety());
		// background update
		// bg.setPosition(tileMap.getx(), tileMap.gety());
		// bg.setVector(0, 0);
		// bg.update();

		// update enemies
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update();
		}
		
		// attack enemies
		player.checkAttacks(enemies);

		// update all enemies
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if (e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(e.getx(), e.gety()));
			}
		}
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
		
		for(int i=0;i<coins.size();i++){
			coins.get(i).update();
			if(coins.get(i).shouldRemove()){
				coins.remove(i);
				i--;
			}
		}
		player.getCoin(coins);
	}

	public void draw(Graphics2D g) {
		// draw bg
		bg.draw(g);

		// draw tilemap
		tileMap.draw(g);
		// draw player
		player.draw(g);
		// draw info
		hud.draw(g);
		
		//draw coins
		for(int i = 0; i< coins.size(); i++){
			coins.get(i).draw(g);
		}
		
		//draw enemies
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		// draw explosions
		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition((int) tileMap.getx(),
					(int) tileMap.gety());
			explosions.get(i).draw(g);
		}

	}

	public void handleInput() {
		if(Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(true);
		//if(blockInput || player.getHealth() == 0) return;
		player.setUp(Keys.keyState[Keys.UP]);
		player.setLeft(Keys.keyState[Keys.LEFT]);
		player.setDown(Keys.keyState[Keys.DOWN]);
		player.setRight(Keys.keyState[Keys.RIGHT]);
		player.setJumping(Keys.keyState[Keys.JUMPING]);
		player.setGliding(Keys.keyState[Keys.GLIDING]);
		if(Keys.isPressed(Keys.FIRING)) player.setFiring();
		if(Keys.isPressed(Keys.BUTTON4)) player.setScratching();
	}

}
