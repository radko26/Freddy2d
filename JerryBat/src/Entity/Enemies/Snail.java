package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import TileMap.TileMap;
import Entity.Animation;
import Entity.Enemy;

public class Snail extends Enemy {

	// animation
	BufferedImage[] bi;

	public Snail(TileMap tm) {
		super(tm);

		moveSpeed = 0.3;
		maxSpeed = 0.3;
		fallSpeed = 0.12;
		maxFallSpeed = 10;

		width = 31;
		height = 20;
		cwidth = 20*2;
		cheight = 14*2;

		health = maxHealth = 2;
		damage = 1;
		type= SNAIL;
		SCALE=1.6f;
		// load sprites

		try {
			BufferedImage spritesheet = ImageIO.read(getClass()
					.getResourceAsStream("/Sprites/Enemies/snail.jpg"));
			bi = new BufferedImage[6];
			for (int i = 0; i < 6; i++) {
				bi[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}

		animation = new Animation();
		animation.setFrames(bi);
		animation.setDelay(250);

		right = true;
		facingRight = true;
	}

	private void getNextPosition() {

		// movement
		if(left) dx = -moveSpeed;
		else if(right) dx = moveSpeed;
		else dx = 0;

		// falling
		if (falling) {
			dy += fallSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
		if(jumping && !falling) {
			dy = jumpStart;
		}

	}

	public void update() {
		

		if (flinching) {
			long elapsed = (System.nanoTime() - flinchingTimer) / 1000000;
			if (elapsed > 400) {
				flinching = false;
			}
		}
		
		// update
		checkTileMapCollision();
		getNextPosition();
		calculateCorners(x, ydest +1);
		// direction
		if(!bottomleft) {
			left = false;
			right = facingRight = true;
		}
		if(!bottomright) {
			left = true;
			right = facingRight = false;
		}
		setPosition(xtemp, ytemp);
		//if(!bottomleft || !bottomright)
		if(dx == 0) {
			left = !left;
			right = !right;
			facingRight = !facingRight;
		}
		super.update();
		// update animation
		animation.update();

	}

	public void draw(Graphics2D g) {

		// if(notOnScreen())return ;

		setMapPosition();
		super.draw(g);
	}

}
