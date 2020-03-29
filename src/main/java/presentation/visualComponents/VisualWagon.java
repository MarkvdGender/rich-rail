package presentation.visualComponents;

import java.awt.Color;
import java.awt.Graphics;

public class VisualWagon implements VisualComponent{

	@Override
	public void draw(String type, int x, int y, Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(30 + x * 100, 80 + y*100, 80, 40);
		g.setColor(Color.BLACK);
		g.fillRoundRect(35 + x * 100, 120 + y*100, 20, 20, 20, 20);
		g.fillRoundRect(80 + x * 100, 120 + y*100, 20, 20, 20, 20);
		g.drawString(type, 40 + x * 100, 105 + y*100);
		

	}
	

}
