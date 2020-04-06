package presentation.frame;

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
import service.train.trainobserver.TrainObserver;
import service.train.trainobserver.TrainSubject;

public class DrawPanel extends JPanel implements Frame {

	private TrainSubject subject = TrainSubject.getInstance();
	private List<Train> trains;
	private JFrame jf;

	public DrawPanel() {

		jf = new JFrame();
		jf.setSize(600, 800);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(this);
	}

	public void paintComponent(Graphics g) {

		subject.addObserver(this);
		super.paintComponent(g);

		VisualComponent visualEngine = new VisualEngine();
		VisualComponent visualLocomotive = new VisualLocomotive();
		VisualComponent visualWagon = new VisualWagon();

		int y = -1;
		for (Train t : trains) {
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

	}

//	public JPanel getPanel() {
//		return this;
//	}

//	public JFrame getFrame() {
//
////		DrawPanel d = DrawPanel.getInstance();
////		JFrame jf = new JFrame();
////		jf.setSize(600, 800);
////		jf.setVisible(true);
////		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////		jf.add(d);
//
//		return jf;
//	}

	@Override
	public void update(List<Train> trains, List<Wagon> wagons, List<Locomotive> locomotives) {
		this.trains = trains;
		jf.repaint();

	}

	@Override
	public void showFrame() {
		jf.setVisible(true);

	}

	@Override
	public void hideFrame() {

		jf.setVisible(false);
	}

}
