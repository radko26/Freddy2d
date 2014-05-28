package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import Main.GamePanel;



public class HUD {

	private Player player;

	private BufferedImage heart;
	private BufferedImage halfheart;
	private BufferedImage emptyheart;
	private BufferedImage energyball;
	private BufferedImage timerIcon;
	private BufferedImage coinIcon;
	private Font font;

	public HUD(Player p) {
		player = p;
		try {
			heart = ImageIO.read(getClass().getResourceAsStream(
					"/HUD/hud_heartFull.png"));

			halfheart = ImageIO.read(getClass().getResourceAsStream(
					"/HUD/hud_heartHalf.png"));
			emptyheart = ImageIO.read(getClass().getResourceAsStream(
					"/HUD/hud_heartEmpty.png"));
			energyball = ImageIO.read(getClass().getResourceAsStream(
					"/HUD/EnergyBall.png"));
			timerIcon= ImageIO.read(getClass().getResourceAsStream("/HUD/timer.png"));
			coinIcon = ImageIO.read(getClass().getResourceAsStream("/HUD/retro_coin.png"));
			font = new Font("Arial", Font.PLAIN, 14);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g) {
		if (player.getHealth() <= player.getMaxHealth() / 2+ player.getMaxHealth() % 2
				&& player.getHealth() >1) {
			g.drawImage(halfheart, 10, 13, 15, 15, null);
		} else if (player.getHealth() <= 1) {
			g.drawImage(emptyheart, 10, 13, 15, 15, null);
		} else
			g.drawImage(heart, 10, 13, 15, 15, null);
		g.drawImage(energyball, 10, 30, 15, 15, null);
		g.drawImage(timerIcon,12,2,10,10,null);
		g.drawImage(coinIcon,10,45,15,15,null);
		
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(player.getLives()+ "", 80,25);
		g.drawString(player.getHealth() + "/" + player.getMaxHealth(), 30, 25);
		g.drawString(player.getFire() / 100 + "/" + player.getMaxFire() / 100,
				30, 40);
		g.drawString("x:" + player.getx() + " y:" + player.gety(),
				GamePanel.WIDTH-100, 10);
		g.drawString(player.getTimetoString(),30,12);
		g.drawString(player.getScore()+ "", 30,57);

	}

}
