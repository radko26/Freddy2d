package TileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class TileMap {

	// position
	double x;
	double y;

	// bounds
	int xmin;
	int ymin;
	int xmax;
	int ymax;

	private double tween;

	// map
	private int[][] map;
	private int tileSize;
	private int numCols;
	private int numRows;
	private int width;
	private int height;

	// tileSet
	private BufferedImage tileset;
	private int numTilesAcross;
	private Tile[][] tiles;

	// drawing
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	private int SCALE = 2;

	public TileMap(int tileSize) {
		this.tileSize = tileSize;
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		numColsToDraw = GamePanel.WIDTH / tileSize + 2;
	}

	public void loadTiles(String s) {

		try {
			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			numTilesAcross = tileset.getWidth() / tileSize;
			tiles = new Tile[2][numTilesAcross];

			BufferedImage subImage;
			for (int col = 0; col < numTilesAcross; col++) {
				subImage = tileset.getSubimage(col * tileSize, 0, tileSize,
						tileSize);
				tiles[0][col] = new Tile(subImage, Tile.NORMAL);

				subImage = tileset.getSubimage(col * tileSize, tileSize,
						tileSize, tileSize);
				tiles[1][col] = new Tile(subImage, Tile.BLOCKED);
				// System.out.println("col:" +col);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void loadMap(String s) {

		// 1 . cols
		// 2 . rows
		try {
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());

			// System.out.println("numCols:"+numCols);
			// System.out.println("numRows:"+numRows);
			// System.exit(0);

			map = new int[numRows][numCols];
			width = numCols * tileSize*SCALE;
			height = numRows * tileSize*SCALE;

			xmin = GamePanel.WIDTH - width;
			xmax = 0;
			ymin = GamePanel.HEIGHT - height;
			ymax = 0;

			String delims = "\\s+";

			for (int row = 0; row < numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for (int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getTileSize() {
		return tileSize;
	}

	public double getx() {
		return x;
	}

	public double gety() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getType(int row, int col) {
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c].getType();
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumCols() {
		return numCols;
	}

	public void setTween(double d) {
		tween = d;
	}

	public void setPosition(double x, double y) {
		this.x += (x - this.x) * tween;
		this.y += (y - this.y) * tween;

		fixBounds();

		colOffset = (int) -this.x / tileSize/SCALE;
		rowOffset = (int) -this.y / tileSize/SCALE;

	}

	private void fixBounds() {
		if (x < xmin)
			x = xmin;
		if (x > xmax)
			x = xmax;
		if (y < ymin)
			y = ymin;
		if (y > ymax)
			y = ymax;
	}

	public void draw(Graphics2D g) {
		for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
			if (row >= numRows)
				break;

			for (int col = colOffset; col < colOffset + numColsToDraw; col++) {
				if (col >= numCols)
					break;
				if (map[row][col] == 0)
					continue;
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;

				g.drawImage(tiles[r][c].getImage(), (int) x + col * tileSize*SCALE ,
						(int) y + row * tileSize*SCALE,tileSize*SCALE,tileSize*SCALE ,null);
				// System.out.println("drawMap: " +"x:"+x+" y:"+y );
			}
		}
	}

}
