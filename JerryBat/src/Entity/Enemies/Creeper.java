package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;
import Entity.Animation;
import Entity.Enemy;
import Entity.Player;

public class Creeper extends Enemy {

	private BufferedImage bi[];
	private int blowRange;
	private int sightRange;
	
	private BufferedImage blowSprites[];
	
	Player player;
	boolean goRight;
	boolean goLeft;

	public Creeper(TileMap tm, Player p) {
		super(tm);
		player = p;

		width = 32;
		height = 32;
		SCALE  = 1.6f;
		cwidth = 20*2;
		cheight = 28*2;
		fallSpeed = 10;

		sightRange = 120;
		range=blowRange = 0;
		
		moveSpeed = maxSpeed = 0.2;
		damage = 14;
		health = maxHealth = 10;
		type = CREEPER;

		try {
			BufferedImage sprite = ImageIO.read(getClass().getResourceAsStream(
					"/Sprites/Enemies/creeper2.png"));
			bi = new BufferedImage[3];
			for (int i = 0; i < 3; i++) {
				bi[i] = sprite.getSubimage(i * width, 32, width, height);
			}


		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error loading creeper sprites");
		}

		animation = new Animation();
		animation.setFrames(bi);
		animation.setDelay(150);
	}

	private void getNextPosition() {

		// System.out.println(player.getx());
		// movement
		goRight = player.getx() > x && player.getx() < x + sightRange;
		goLeft = player.getx() < x && player.getx() > x - sightRange;
		
		if (goLeft) {
			dx -= moveSpeed;
			if (dx < -maxSpeed) {
				dx = -maxSpeed;
			}
			left=true;
			facingRight=false;
			right=false;
		} else if (goRight) {
			dx += moveSpeed;
			if (dx > maxSpeed) {
				dx = maxSpeed;
			}
			left=false;
			facingRight=true;
			right=true;
		}
		// falling
		if (falling) {
			dy += fallSpeed;
		}

	}

	public void update() {

		// update
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);

		if (flinching) {
			long elapsed = (System.nanoTime() - flinchingTimer) / 1000000;
			if (elapsed > 200) {
				flinching = false;
			}
		}

		// direction
		if (right ) {
			right = false;
			left = true;
			facingRight = false;
		} else if (left ) {
			left = false;
			right = true;
			facingRight = true;
		}
		// update animation
		animation.update();

	}

	
	public void draw(Graphics2D g) {

		// if(notOnScreen())return ;

		setMapPosition();
		super.draw(g);
	}

}
