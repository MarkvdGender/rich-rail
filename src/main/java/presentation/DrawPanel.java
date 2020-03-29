package presentation;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import domain.RollingStock;
import domain.Train;
import domain.Wagon;
import domain.locomotive.Locomotive;
import presentation.visualComponents.VisualComponent;
import presentation.visualComponents.VisualEngine;
import presentation.visualComponents.VisualLocomotive;
import presentation.visualComponents.VisualWagon;
import service.observer.TrainObserver;
import service.observer.TrainSubject;

public class DrawPanel extends JPanel implements TrainObserver{
	

	private TrainSubject subject = TrainSubject.getInstance();
	private List<Train> trains;
	
	public void paintComponent(Graphics g) {
		
		subject.addObserver(this);
		System.out.println("painting");
		super.paintComponent(g);
		
		
		VisualComponent visualEngine = new VisualEngine();
		VisualComponent visualLocomotive = new VisualLocomotive();
		VisualComponent visualWagon = new VisualWagon();

		int y = -1;
		for(Train t : trains) {
			int x = 0;
			y++;
			visualEngine.draw(t.getId(), x, y, g);
			for (RollingStock r : t.getAllRollingStock()) {
				x++;
				if (r instanceof Wagon) {
					visualWagon.draw(r.getType(), x, y, g);
				} else if (r instanceof Locomotive) {
					visualLocomotive.draw(r.getType(), x, y, g);
				}

			}
		}
		
//		g.setColor(Color.BLUE);
//		g.fillRect(30, 80 + 10, 80, 40);
//		g.fillRect(80, 60 + 10, 30, 30);
//		g.setColor(Color.DARK_GRAY);
//		g.drawRoundRect(85, 40 + 10, 20, 20, 20, 20);
//		g.drawRoundRect(85, 10, 40, 40, 40, 40);
//		g.setColor(Color.BLACK);
//		g.fillRoundRect(35, 120 + 10, 20, 20, 20, 20);
//		g.fillRoundRect(80, 120 + 10, 20, 20, 20, 20);
//		g.setColor(Color.WHITE);
//		g.drawString("lol", 40, 105 + 10);
//		
	}
	
	public JPanel getPanel() {
		System.out.println("returning");
		return this;
	}
	
//	public static void main(String[] args) {
//		
//		 DrawPanel d = new DrawPanel();
//		 JFrame jf = new JFrame();
//		 jf.setSize(600, 800);
//		 jf.setVisible(true);
//		 jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		 jf.add(d);
//	}
	
	public JFrame getFrame() {
		
		 DrawPanel d = new DrawPanel();
		 JFrame jf = new JFrame();
		 jf.setSize(600, 800);
		 jf.setVisible(true);
		 jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 jf.add(d);
		 
		 return jf;
	}
		

	@Override
	public void update(List<Train> trains, List<Wagon> wagons, List<Locomotive> locomotives) {
		System.out.println("update");
		this.trains = trains;
		
	}
	
	

}
