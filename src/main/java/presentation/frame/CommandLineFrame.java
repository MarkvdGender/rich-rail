package presentation.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

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
import domain.locomotive.Locomotive;
import presentation.antlr.parser.Command;
import service.train.trainobserver.TrainSubject;

public class CommandLineFrame implements Frame {

	private TrainSubject subject = TrainSubject.getInstance();
	private JFrame frame;
	private Command command = new Command();
	private JTextArea output = new JTextArea();
	private JTextArea log = new JTextArea();
	private String logText = "";
	private String trainsDisplay = "";
	private static CommandLineFrame instance;

	private CommandLineFrame() {

		subject.addObserver(this);
		frame = new JFrame("command line");
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		panel.setLayout(new GridLayout());

		JPanel panel2 = new JPanel();
		panel2.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		panel2.setLayout(new GridLayout());
		JTextField input = new JTextField();
		input.setPreferredSize(new Dimension(20, 20));

		JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(20, 20));

		output.setPreferredSize(new Dimension(100, 600));
		output.setEditable(false);
		output.setBackground(Color.BLACK);
		output.setForeground(Color.GREEN);
		log = new JTextArea();
		log.setPreferredSize(new Dimension(100, 600));
		log.setEditable(false);
		log.setBackground(Color.GRAY);
		log.setForeground(Color.WHITE);

		JButton execute = new JButton("Execute");
		execute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				command.command(input.getText());
				try {
					logText = "log: \n";
					Scanner myReader = new Scanner(new File("log/log.txt"));
					while (myReader.hasNextLine()) {
						String data = myReader.nextLine();
						if (data.contains("INFO: ")) {
							logText += " " + (data.split("INFO: ")[1]) + "\n";
						} else if (data.contains("WARNING: ")) {
							logText += " " + (data.split("WARNING: ")[1]) + "\n";
						}
					}
					log.setText(logText);
					myReader.close();
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				}

			}
		});

		panel.add(input);
		panel.add(label);
		panel.add(execute);
		panel2.add(output);
		panel2.add(log);
		frame.add(panel, BorderLayout.SOUTH);
		frame.add(panel2, BorderLayout.NORTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 800);

	}

	public static CommandLineFrame getInstance() {
		if (instance == null) {
			instance = new CommandLineFrame();
		}
		return instance;
	}

	@Override
	public void update(List<Train> trains, List<Wagon> wagons, List<Locomotive> locomotives) {
		trainsDisplay = "locomotives: \n";
		for (Locomotive l : locomotives) {
			trainsDisplay += "[" + l.getType() + "]";
		}
		trainsDisplay += "\n wagons: \n";
		for (Wagon w : wagons) {
			trainsDisplay += "(" + w.getType() + ":" + w.getSeats() + ")";
		}
		trainsDisplay += "\n trains: \n";
		for (Train t : trains) {
			trainsDisplay += "train " + t.getId() + ": \n";
			trainsDisplay += "[" + t.getEngine().getType() + "]";
			for (RollingStock r : t.getAllRollingStock()) {
				trainsDisplay += "-(" + r.getType() + ")";
			}
			trainsDisplay += "\n";
		}

		output.setText(trainsDisplay);

	}

	@Override
	public void showFrame() {
		frame.setVisible(true);

	}

	@Override
	public void hideFrame() {
		frame.setVisible(false);

	}

}
