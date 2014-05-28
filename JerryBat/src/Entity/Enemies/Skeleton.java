package Entity.Enemies;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;
import Entity.Enemy;

public class Skeleton extends Enemy {

	private int attackRange;
	private BufferedImage sprites[];
	
	
	public Skeleton(TileMap tm) {
		super(tm);
		
		width=32;
		height=32;
		cwidth=20;
		cheight=20;
		
		health=maxHealth=7;
		damage=3;
		
		attackRange=100; 
		type = SKELETON;
		
		try{
			BufferedImage sprite=ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/"));
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("cannot load skeleton images");
		}
		
		
		
		
	}

}
