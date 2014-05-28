package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Handlers.Keys;
import Main.GamePanel;
import TileMap.Background;

public class CreditsState extends GameState {

	private Background bg;

	private Color titleColor;
	private Font titleFont;

	public CreditsState(GameStateManager gsm) {
		super(gsm);

		try {

			bg = new Background("/Backgrounds/batBackground1.png", 15);
			bg.setVector(2, 0);

			titleColor = new Color(255, 180, 13);
			titleFont = new Font("Century Gothic", Font.PLAIN, 28);

			new Font("Arial", Font.PLAIN, 13);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void init() {

	}

	@Override
	public void update() {
		handleInput();
	}

	@Override
	public void draw(Graphics2D g) {
		g.clearRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		// draw bg
		bg.draw(g);
		String source = "Sources:|Sprites/Tiles-Google|TileMapEditor-ForeignGuyMike (RP mode) |~press ESC or Q to exit";
		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		String title = "Freddy the Bat";
		g.drawString(title, GamePanel.WIDTH / 2 - title.length() * 20,
				GamePanel.HEIGHT / 2 - 80);
		g.drawString("Adventures", GamePanel.WIDTH / 3 - 40,
				GamePanel.HEIGHT / 2 - 60);
		g.setColor(Color.RED);
		g.setFont(new Font("Times New Roman", Font.ITALIC, 28));
		g.drawString("Credits", GamePanel.WIDTH / 3 - 40,
				GamePanel.HEIGHT / 2 - 40);
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		g.setColor(Color.WHITE);
		// g.drawString(text, GamePanel.WIDTH/3 - 40, GamePanel.HEIGHT/2);
		for (int i = 1, x = 0, j = 40; i <= source.length(); i++, x++) {
			if (source.charAt(i - 1) == '|') {
				j += 20;
				x = 0;
				if (source.charAt(i) == '~') {
					j += 40;
					g.setFont(new Font("Arial", Font.BOLD, 10));
					g.setColor(Color.YELLOW);
				}
			} else
				g.drawString((String) source.subSequence(i - 1, i),
						GamePanel.WIDTH / 3 - 40 + x * 10, GamePanel.HEIGHT / 2
								+ j);

		}
	}

	@Override
	public void handleInput() {
		if (Keys.isPressed(Keys.ENTER) || Keys.isPressed(Keys.ESCAPE) || Keys.isPressed(Keys.Q))
			gsm.setState(GameStateManager.MENUSTATE);
	}
	
	
}
