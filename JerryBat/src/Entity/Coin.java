package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Coin extends MapObject{
	
	BufferedImage bi[];
	private int score;
	private boolean remove;
	
	public Coin(TileMap tm) {
		super(tm);
		
		width = 32;
		height = 32;
		cwidth = 20;
		cheight = 20;
		score=1;
		try{
			BufferedImage sprite;
			sprite = ImageIO.read(getClass().getResourceAsStream("/Sprites/Objects/coin.gif"));
			bi=new BufferedImage[8];
			for(int i=0;i<8;i++){
				bi[i]=sprite.getSubimage(i*width, 0, width, height);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("load images error");
		}
		
		animation = new Animation();
		animation.setFrames(bi);
		animation.setDelay(100);
	
		
		
		
	}
	
	
	
	public void update(){
		checkTileMapCollision();
		if(dx==1)System.out.println("haha");

		
		animation.update();
	}
	
	@Override
	public void draw(Graphics2D g) {
		setMapPosition();
		if(!remove)
		g.drawImage(animation.getImage(), (int) (x + xmap - width / 2),
				(int) (y + ymap - height / 2),20,20, null);
	}
	public int  getScore(){return score;}



	public void remove() {
		remove=true;
		
	}
	public boolean shouldRemove(){
		return remove;
	}
	

}
