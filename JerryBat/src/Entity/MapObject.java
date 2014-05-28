package Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import Main.GamePanel;
import TileMap.*;

public class MapObject {
	protected TileMap tilemap;
	protected int tileSize;
	protected double xmap;
	protected double ymap;

	// position and vector
	protected double x, y, dx, dy;

	// dimensions
	protected int width;
	protected int height;

	// collision box
	protected int cwidth;
	protected int cheight;

	// collison
	protected int currRow;
	protected int currCol;
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	protected boolean topleft;
	protected boolean topright;
	protected boolean bottomright;
	protected boolean bottomleft;

	// animation
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;
	protected boolean facingRight;

	// movement
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;

	// movement attr
	protected double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;
	
	protected int range;
	

	// health
	protected int health;
	
	//draw scale
	protected float SCALE;

	// constructor

	public MapObject(TileMap tm) {
		tilemap = tm;
		tileSize = tm.getTileSize()*2;

	}

	public boolean intersects(MapObject o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2);
	}

	public boolean intersects(Rectangle r) {
		return getRectangle().intersects(r);
	}

	public boolean contains(MapObject o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.contains(r2);
	}

	public boolean contains(Rectangle r) {
		return getRectangle().contains(r);
	}

	public Rectangle getRectangle() {
		return new Rectangle((int) x - (cwidth+range) / 2, (int) y - (range +cheight) / 2,
				cwidth+range, cheight+range);
	}

	public void calculateCorners(double x, double y) {
		int leftTile = (int) (x - cwidth / 2) / tileSize;
		int rightTile = (int) (x + cwidth / 2 - 1) / tileSize;
		int topTile = (int) (y - cheight / 2) / tileSize;
		int bottomTile = (int) (y + cheight / 2 - 1) / tileSize;
		if (topTile < 0 || bottomTile >= tilemap.getNumRows() || leftTile < 0
				|| rightTile >= tilemap.getNumCols()) {
			topleft = topright = bottomleft = bottomright = false;
			return;
		}
		int tl = tilemap.getType(topTile, leftTile);
		int tr = tilemap.getType(topTile, rightTile);
		int bl = tilemap.getType(bottomTile, leftTile);
		int br = tilemap.getType(bottomTile, rightTile);
		topleft = tl == Tile.BLOCKED;
		topright = tr == Tile.BLOCKED;
		bottomleft = bl == Tile.BLOCKED;
		bottomright = br == Tile.BLOCKED;
	}

	public void checkTileMapCollision() {

		currCol = (int) x / tileSize;
		currRow = (int) y / tileSize;

		xdest = x + dx;
		ydest = y + dy;

		xtemp = x;
		ytemp = y;

		calculateCorners(x, ydest);
		if (dy < 0) {
			if (topleft || topright) {
				dy = 0;
				ytemp = currRow * tileSize + cheight / 2;
			} else {
				ytemp += dy;
			}
		}
		if (dy > 0) {
			if (bottomleft || bottomright) {
				dy = 0;
				falling = false;
				ytemp = (currRow + 1) * tileSize - cheight / 2;
			} else {
				ytemp += dy;
			}
		}

		calculateCorners(xdest, y);
		if (dx < 0) {
			if (topleft || bottomleft) {
				dx = 0;
				xtemp = currCol * tileSize + cwidth / 2;
			} else {
				xtemp += dx;
			}
		}
		if (dx > 0) {
			if (topright || bottomright) {
				dx = 0;
				xtemp = (currCol + 1) * tileSize - cwidth / 2;
			} else {
				xtemp += dx;
			}
		}

		if (!falling) {
			calculateCorners(x, ydest + 1);
			if (!bottomleft && !bottomright) {
				falling = true;
			}
		}

	}

	public int getx() {
		return (int) x;
	}

	public int gety() {
		return (int) y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getCWidth() {
		return cwidth;
	}

	public int getCHeight() {
		return cheight;
	}

	public boolean isFacingRight() {
		return facingRight;
	}

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public void setMapPosition() {
		xmap = tilemap.getx();
		ymap = tilemap.gety();
	}

	public void setLeft(boolean b) {
		left = b;
	}

	public void setRight(boolean b) {
		right = b;
	}

	public void setUp(boolean b) {
		up = b;
	}

	public void setDown(boolean b) {
		down = b;
	}

	public void setJumping(boolean b) {
		jumping = b;
	}

	public boolean notOnScreen() {
		return x + xmap + width < 0 ||
			x + xmap - width > GamePanel.WIDTH ||
			y + ymap + height < 0 ||
			y + ymap - height > GamePanel.HEIGHT;
	}

	public void draw(Graphics2D g) {
		setMapPosition();
		if (facingRight) {
			g.drawImage(animation.getImage(),
					(int) (x + xmap  + (int) (width*SCALE) / 2 - width*SCALE),
					(int) (y + ymap -(int)( height*SCALE) / 2), (int) (width*SCALE), (int)(height*SCALE),null);
		} else {
			g.drawImage(animation.getImage(),
					(int) (x + xmap  - (int) (width*SCALE) / 2 + width*SCALE),
					(int) (y + ymap -(int)( height*SCALE) / 2), (int) (-width*SCALE), (int)(height*SCALE),null);
		}
		// draw collision box
		 Rectangle r = getRectangle();
		   r.x += xmap;
		   r.y += ymap;
		   g.draw(r);
		 
	}


}