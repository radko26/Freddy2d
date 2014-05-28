package Entity;

import Audio.AudioListener;
import Handlers.Keys;
import TileMap.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends MapObject {

	// player stuff
	private int health;
	private int maxHealth;
	private int fire;
	private int maxFire;
	private boolean flinching;
	private long flinchTimer;

	private long startTime, endTime;
	private boolean startedTime = false;

	private int lives;
	private int score;

	// fireball
	private boolean firing;
	private int fireCost;
	private int fireBallDamage;
	private ArrayList<Fireball> fireBalls;

	// scratch
	private boolean scratching;
	private int scratchDamage;
	private int scratchRange;

	// gliding
	private boolean gliding;

	// animations
	private ArrayList<BufferedImage[]> sprites;
	// private final int[] numFrames = { 2, 8, 1, 2, 4, 2, 5 };

	// animation actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int GLIDING = 4;
	private static final int FIREBALL = 5;
	private static final int SCRATCHING = 6;

	public Player(TileMap tm) {

		super(tm);
		
		width = 34;
		height = 59;
		SCALE = 1.5f;
		
		cwidth = (int) (32*SCALE);
		cheight = (int) (45*SCALE);
		

		moveSpeed = 0.5;
		maxSpeed = 2.6;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 2.0;
		jumpStart = -6.8;
		stopJumpSpeed = 1.3;

		facingRight = true;
		lives = 3;
		health = maxHealth = 16;

		fire = maxFire = 4500;
		fireCost = 500;
		fireBallDamage = 5;
		fireBalls = new ArrayList<Fireball>();

		scratchDamage = 8;
		scratchRange = 40;

		// load sprites
		try {

			int widthIdle=70;
			int heightIdle=120;
			
			int widthWalk=47;
			int heightWalk=47;
			
			int widthBlast=50;
			int heightBlast=48;
			
			int widthFly=47;
			int heightFly=48;
			BufferedImage IdleSprite = ImageIO.read(getClass()
					.getResourceAsStream("/Sprites/Player/SpriteTest.png"));
			BufferedImage WalkSprite = ImageIO.read(getClass()
					.getResourceAsStream("/Sprites/Player/heroWalking.png"));
			BufferedImage BlastSprite = ImageIO.read(getClass()
					.getResourceAsStream("/Sprites/Player/heroBlastBall.png"));
			BufferedImage FlySprite = ImageIO.read(getClass()
					.getResourceAsStream("/Sprites/Player/heroFly.png"));
			sprites = new ArrayList<BufferedImage[]>();
			BufferedImage[] bi1 = new BufferedImage[3];
			BufferedImage[] bi2 = new BufferedImage[6];
			BufferedImage[] bi3 = new BufferedImage[6];
			BufferedImage[] bi4 = new BufferedImage[3];
			for (int j = 0; j < 3; j++) {
				bi1[j] = IdleSprite.getSubimage(j * widthIdle, 0, widthIdle, heightIdle);
				bi2[j] = WalkSprite.getSubimage(j * widthWalk, 0, widthWalk, heightWalk);
				bi3[j] = BlastSprite.getSubimage(j * widthBlast, 0, widthBlast, heightBlast);
			}
			for(int j=0;j<3;j++){
				bi4[j]=FlySprite.getSubimage(j*widthFly, 0, widthFly, heightFly);
			}
			sprites.add(bi1);// 0
			sprites.add(bi1);// 1
			sprites.add(bi1);// 2
			sprites.add(bi1);// 3
			sprites.add(bi1);// 4
			sprites.add(bi1);// 5
			sprites.add(bi1);// 6
		} catch (Exception e) {
			e.printStackTrace();
		}

		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(240);
		
		//AudioListener.load("/Music/SE/ballBlow.mp3", "ballBlow");
		
		//setting volumes
		AudioListener.setVolume("fireball", -20f);

	}

	public int getHealth() {
		return health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getFire() {
		return fire;
	}

	public int getMaxFire() {
		return maxFire;
	}

	public void setFiring() {
		firing = true;
	}

	public void setScratching() {
		scratching = true;
	}

	public void setGliding(boolean b) {
		gliding = b;
	}

	public void setDead() {
		lives--;
		if (lives == 0) {
			// System.out.println(time);
			PlayerSave.setScore(score);
			PlayerSave.setStartTime(startTime);
			PlayerSave.setEndTime(endTime);
		}
	}

	public void reset() {
		health = maxHealth;
		facingRight = true;
		currentAction = -1;
		setPosition(100, 100);
	}

	public long getTime() {
		return endTime - startTime;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setStartTime(long i) {
		startTime = i;
	}

	public void setEndTime(long i) {
		endTime = i;
	}

	public String getTimetoString() {
		long seconds = TimeUnit.MILLISECONDS.toSeconds(getTime());
		if (seconds % 60 < 10) {
			return seconds / 60 + ":" + "0" + seconds % 60;
		} else
			return seconds / 60 + ":" + seconds % 60;
	}

	public void setHealth(int i) {
		health = i;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int i) {
		lives = i;
	}

	public void increaseScore(int score) {
		this.score += score;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int i) {
		score = i;
	}

	private void getNextPosition() {

		// movement
		if (left) {
			dx -= moveSpeed;
			if (dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		} else if (right) {
			dx += moveSpeed;

			if (dx > maxSpeed) {
				dx = maxSpeed;
			}
		} else {
			if (dx > 0) {
				dx -= stopSpeed;
				if (dx < 0) {
					dx = 0;
				}
			} else if (dx < 0) {
				dx += stopSpeed;
				if (dx > 0) {
					dx = 0;
				}
			}
		}

		// cannot move while attacking, except in air
		if ((currentAction == SCRATCHING || currentAction == FIREBALL)) {
			dx = 0;
		}

		// jumping
		if (jumping && !falling) {
			dy = jumpStart;
			falling = true;
		}

		// falling
		if (falling) {

			if (dy > 0 && gliding)
				dy += fallSpeed * 0.1;
			else
				dy += fallSpeed;

			if (dy > 0)
				jumping = false;
			if (dy < 0 && !jumping)
				dy += stopJumpSpeed;

			if (dy > maxFallSpeed)
				dy = maxFallSpeed;

		}

	}

	public void update() {
		Date date = new Date();
		if (PlayerSave.getLives() == 0 && !startedTime) {
			startTime = date.getTime();
			startedTime = true;
		} else {
			endTime = date.getTime();
		}

		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);

		// check attack
		if (currentAction == SCRATCHING) {
			if (animation.hasPlayedOnce())
				scratching = false;
		}
		

		// fireball attack
		fire += 1;
		if (fire > maxFire) {
			fire = maxFire;
		}
		if (firing && !falling&& !gliding && animation.hasPlayedOnce() && currentAction==FIREBALL) {
			firing=false;
			// InfiniteFire
			if (fire > fireCost) {
				fire -= fireCost;
				Fireball fb = new Fireball(tilemap, facingRight);
				AudioListener.play("fireball");
				
				fb.setPosition(x, y);
				fireBalls.add(fb);
			}
		}
		
		

		// fireball update
		for (int i = 0; i < fireBalls.size(); i++) {
			fireBalls.get(i).update();
			if (fireBalls.get(i).shouldRemove()) {
				fireBalls.remove(i);
				i--;
			}
		}
		// check our flinching
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 1000) {
				flinching = false;
			}
		}
		// set animation
		if (scratching) {
			if (currentAction != SCRATCHING) {
				currentAction = SCRATCHING;
				animation.setFrames(sprites.get(SCRATCHING));
				animation.setDelay(60);
				width = 30;
			}
		} else if (firing) {
			if(falling || gliding){
				firing=false;
			}
			if (currentAction != FIREBALL && !falling && !gliding) {
				currentAction = FIREBALL;
				animation.setFrames(sprites.get(FIREBALL));
				animation.setDelay(50);
				width = 30;
			}
		} else if (dy > 0) {
			if (gliding) {
				if (currentAction != GLIDING) {
					currentAction = GLIDING;
					animation.setFrames(sprites.get(GLIDING));
					animation.setDelay(100);
					width = 30;
				}
			} else if (currentAction != FALLING) {
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(80);
				width = 30;
			}
		} else if (dy < 0) {
			if (currentAction != JUMPING) {
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
				width = 30;
			}
		} else if (left || right) {
			if (currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(70);
				width = 30;
			}
		} else {
			if (currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(150);
				width = 30;
			}
		}

		animation.update();

		// set direction
		if (currentAction != SCRATCHING && currentAction != FIREBALL) {
			if (right)
				facingRight = true;
			if (left)
				facingRight = false;
		}

	}

	public void checkAttacks(ArrayList<Enemy> enemies) {
		// loop through enemies
		for (int i = 0; i < enemies.size(); i++) {

			Enemy e = enemies.get(i);

			// scratch attack
			if (scratching) {
				if (facingRight) {
					if (e.getx() > x && e.getx() < x + scratchRange
							&& e.gety() > y - height / 2
							&& e.gety() < y + height / 2) {
						e.hit(scratchDamage);
					}
				} else {
					if (e.getx() < x && e.getx() > x - scratchRange
							&& e.gety() > y - height / 2
							&& e.gety() < y + height / 2) {
						e.hit(scratchDamage);
					}
				}
			}

			// fireballs
			for (int j = 0; j < fireBalls.size(); j++) {
				if (fireBalls.get(j).intersects(e)) {
					if (!fireBalls.get(j).isHit())
						e.hit(fireBallDamage);
					fireBalls.get(j).setHit();
					//AudioListener.stop("fireball");
					//AudioListener.play("ballBlow");
					break;
				}
			}

			// check enemy collision
			if (intersects(e)) {
				hit(e.getDamage());
			}
		}
	}

	private void getTheCoin(Coin c) {
		score += c.getScore();
		c.remove();
	}

	public void getCoin(ArrayList<Coin> coins) {
		for (int i = 0; i < coins.size(); i++) {
			Coin c = coins.get(i);
			// check collision
			if (intersects(c)) {
				getTheCoin(c);
				AudioListener.play("collectCoin");
			}
		}

	}

	public void hit(int damage) {
		if (flinching)
			return;
		health -= damage;
		if (health < 0)
			health = 0;
		if (health == 0) {
		}
		flinching = true;
		flinchTimer = System.nanoTime();
	}

	public void draw(Graphics2D g) {

		
		// draw fireball
		for (int i = 0; i < fireBalls.size(); i++) {
			fireBalls.get(i).draw(g);
		}

		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed / 100 % 2 == 0) {
				return;
			}
		}
		
		super.draw(g);

	}

}
