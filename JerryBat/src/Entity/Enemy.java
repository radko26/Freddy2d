package Entity;

import java.awt.image.BufferedImage;

import TileMap.TileMap;

public class Enemy extends MapObject {

	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;
	
	protected int type;
	protected static int SNAIL=1;
	protected static int CREEPER = 2;
	protected static int SKELETON = 3;

	protected boolean flinching;
	protected long flinchingTimer;

	
	public Enemy(TileMap tm) {
		super(tm);
	}

	public boolean isDead() {
		return dead;
	}

	public int getDamage() {
		return damage;
	}
	public int getType(){return type;}
	public BufferedImage[] getBlowSprite() {return null;}
	public int getHealth(){return health;}
	
	public void hit(int damage) {
		
		if (dead || flinching)
			return;
		health -= damage;
		if (health < 0)
			health = 0;
		if (health == 0)
			dead = true;
		flinching = true;
		flinchingTimer = System.nanoTime();
	}
	public void update() {}

	

}
