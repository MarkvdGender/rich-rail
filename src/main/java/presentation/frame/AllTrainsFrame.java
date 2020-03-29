package presentation.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
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

public class AllTrainsFrame implements TrainObserver{
	
	private TrainSubject subject = TrainSubject.getInstance();
	private JFrame frame;
	private JPanel drawPanel;
	

	public AllTrainsFrame() {
		

		frame = new JFrame("basic edit");
		
		JPanel jPanel1 = new JPanel();
		jPanel1.setLayout(new BorderLayout());
		frame.getContentPane().add(jPanel1, new GridBagConstraints(0, 0, 4, 2, 0.0, 0.0, GridBagConstraints.EAST,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		{
			drawPanel = new JPanel();
			drawPanel.setBackground(Color.WHITE);
			jPanel1.add(drawPanel, BorderLayout.CENTER);
		}
		

//		drawPanel = new JPanel();
//		drawPanel.setBackground(Color.WHITE);
		
		JButton button = new JButton("testtrain");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				subject.newTrain("test", "steam");

			}
		});
		
		

		frame.add(jPanel1, BorderLayout.CENTER);
		frame.add(button, BorderLayout.NORTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 800);
		frame.setVisible(true);
		
		
		

	}

//	public void drawTrain(Train train) {
//		System.out.println("drawTrain call");
//		VisualComponent visualEngine = new VisualEngine();
//		VisualComponent visualLocomotive = new VisualLocomotive();
//		VisualComponent visualWagon = new VisualWagon();
//		Graphics g = drawPanel.getGraphics();
//		System.out.println(g);
//		int index = 0;
//		visualEngine.draw(train.getId(), 0, g);
//		for (RollingStock r : train.getAllRollingStock()) {
//			index++;
//			if (r instanceof Wagon) {
//				visualWagon.draw(r.getType(), index, g);
//			} else if (r instanceof Locomotive) {
//				visualLocomotive.draw(r.getType(), index, g);
//			}
//
//		}
//
//	}
	@Override
	public void update(List<Train> trains, List<Wagon> wagons, List<Locomotive> locomotives) {
		for(Train t : trains) {
//			drawTrain(t);
		}

	}


}
