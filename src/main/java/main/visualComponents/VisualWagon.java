package main.visualComponents;

import java.awt.Color;
import java.awt.Graphics;

public class VisualWagon implements VisualComponent{

	@Override
	public void draw(String type, int index, Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(30 + index * 100, 80 + 10, 80, 40);
		g.setColor(Color.BLACK);
		g.fillRoundRect(35 + index * 100, 120 + 10, 20, 20, 20, 20);
		g.fillRoundRect(80 + index * 100, 120 + 10, 20, 20, 20, 20);
		g.drawString(type, 40 + index * 100, 105 + 10);
		
	}
	

}
