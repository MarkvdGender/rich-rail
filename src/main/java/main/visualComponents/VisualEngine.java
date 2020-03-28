package main.visualComponents;

import java.awt.Color;
import java.awt.Graphics;

public class VisualEngine implements VisualComponent{

	@Override
	public void draw(String type, int index, Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(30, 80 + 10, 80, 40);
		g.fillRect(80, 60 + 10, 30, 30);
		g.setColor(Color.DARK_GRAY);
		g.drawRoundRect(85, 40 + 10, 20, 20, 20, 20);
		g.drawRoundRect(85, 10, 40, 40, 40, 40);
		g.setColor(Color.BLACK);
		g.fillRoundRect(35, 120 + 10, 20, 20, 20, 20);
		g.fillRoundRect(80, 120 + 10, 20, 20, 20, 20);
		g.setColor(Color.WHITE);
		g.drawString(type, 40, 105 + 10);
		
	}
	

}
