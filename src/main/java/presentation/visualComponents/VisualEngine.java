package presentation.visualComponents;

import java.awt.Color;
import java.awt.Graphics;

public class VisualEngine implements VisualComponent {

	@Override
	public void draw(String type, int x, int y, Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(30, 80 + y * 100, 80, 40);
		g.fillRect(80, 60 + y * 100, 30, 30);
		g.setColor(Color.DARK_GRAY);
		g.drawRoundRect(85, 40 + y * 100, 20, 20, 20, 20);
		g.drawRoundRect(85, y*100, 40, 40, 40, 40);
		g.setColor(Color.BLACK);
		g.fillRoundRect(35, 120 + y * 100, 20, 20, 20, 20);
		g.fillRoundRect(80, 120 + y * 100, 20, 20, 20, 20);
		g.setColor(Color.WHITE);
		g.drawString(type, 40, 105 + y * 100);

	}

}
