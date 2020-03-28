package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import domain.RollingStock;
import domain.Train;
import domain.Wagon;
import observer.train.TrainObserver;
import presentation.parser.Command;

public class CommandLineFrame implements TrainObserver{
	
	private Command command = new Command();
	private JTextArea output = new JTextArea();
	private String trainsDisplay = "";
	public CommandLineFrame() {

//		frame maken
		JFrame frame = new JFrame("eerste frame");

//        panel maken
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		panel.setLayout(new GridLayout());

		JPanel panel2 = new JPanel();
		panel2.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		panel2.setLayout(new GridLayout());

//Content maken voor in de panel
		JTextField input = new JTextField();
		input.setPreferredSize(new Dimension(20, 20));

		JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(20, 20));

		output.setPreferredSize(new Dimension(100, 600));
		output.setEditable(false);
		output.setBackground(Color.BLACK);
		output.setForeground(Color.GREEN);

		JTextArea log = new JTextArea();

		log.setPreferredSize(new Dimension(100, 600));
		log.setEditable(false);
		log.setBackground(Color.GRAY);
		log.setForeground(Color.WHITE);
		log.setText(" Log");
		log.append("\n log2");

		JButton execute = new JButton("Execute");
		execute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				command.command(input.getText());

			}
		});

//		content adden aan de panel
		panel.add(input);
		panel.add(label);
		panel.add(execute);

		panel2.add(output);
		panel2.add(log);

//		frame packen met settings
		frame.add(panel, BorderLayout.SOUTH);
		frame.add(panel2, BorderLayout.NORTH);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 800);
		frame.setVisible(true);

	}

	

	@Override
	public void update(List<Train> trains, List<Wagon> wagons) {
		trainsDisplay="wagons:";
		for(Wagon w : wagons) {
			trainsDisplay+="("+w.getType()+":"+w.getSeats()+")";
		}
		trainsDisplay+="\n trains \n";
		for(Train t : trains) {
			trainsDisplay+="train "+t.getId()+": \n";
			trainsDisplay+="["+t.getEngine().getType()+"]";
			for(RollingStock r : t.getAllRollingStock()) {
				trainsDisplay+="-("+r.getType()+")";
			}
			trainsDisplay+="\n";
		}
		
		output.setText(trainsDisplay);
		System.out.println(trainsDisplay);
	}

}
