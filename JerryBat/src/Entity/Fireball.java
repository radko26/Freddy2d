package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Fireball extends MapObject {

	private boolean hit;
	private boolean remove;
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;

	public Fireball(TileMap tm, boolean right) {// add up direction too
		super(tm);
		moveSpeed = 3.8;
		if (right)
			dx = moveSpeed;
		else
			dx = -moveSpeed;

		width = 30;
		height = 30;

		cwidth = 14;
		cheight = 14;
		facingRight = right;
		// load sprites

		try {
			BufferedImage spritesheet = ImageIO.read(getClass()
					.getResourceAsStream("/Sprites/Player/ball2.png"));

			sprites = new BufferedImage[4];// add sprite number
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width,
						height);
			}
			hitSprites = sprites;
			hitSprites = new BufferedImage[4];// same as above (add sprite num)
			for (int i = 0; i < hitSprites.length; i++) {
				hitSprites[i] = spritesheet.getSubimage(i * width, height,
						width, height);
			}
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(70);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setHit() {
		if (hit)
			return;
		hit = true;
		animation.setFrames(hitSprites);
		animation.setDelay(50);
		
		dx = 0;
	}
	public boolean isHit(){return hit;}

	public boolean shouldRemove() {
		return remove;
	}

	public void update() {

		checkTileMapCollision();
		setPosition(xtemp, ytemp);

		if (dx == 0 && !hit) {
			setHit();
		}

		animation.update();
		if (hit && animation.hasPlayedOnce()) {
			remove = true;
		}

	}

	public void draw(Graphics2D g) {
		setMapPosition();
		// System.out.println(facingRight);
		if (facingRight) {
			g.drawImage(animation.getImage(), (int) (x + xmap - width / 2),
					(int) (y + ymap - height / 2), null);
		} else {
			g.drawImage(animation.getImage(),
					(int) (x + xmap - width / 2 + width),
					(int) (y + ymap - height / 2), -width, height, null);

		}

	}

}
