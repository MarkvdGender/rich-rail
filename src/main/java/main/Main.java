package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import domain.RollingStock;
import domain.Train;
import domain.Wagon;
import domain.locomotive.Locomotive;
import domain.locomotive.SteamLocomotive;
import main.visualComponents.VisualComponent;
import main.visualComponents.VisualEngine;
import main.visualComponents.VisualLocomotive;
import main.visualComponents.VisualWagon;
import persistence.TrainDao;
import persistence.TrainPostgresDaoImpl;
import presentation.WagonDirector;
import service.wagonBuilder.FirstClassWagonBuilder;
import service.wagonBuilder.FreightWagonBuilder;
import service.wagonBuilder.PassengerWagonBuilder;
import service.wagonBuilder.WagonBuilder;

public class Main extends javax.swing.JFrame implements ActionListener {

//	DOMEIN OBJECTEN
	private JComboBox rollingBox;

	private Train train;
	private List<RollingStock> allRollingStock = new ArrayList<RollingStock>();

	private WagonBuilder wagonBuilder;
//	DYNAMISCHE INFORMATIE

//	input naam van de trein
	private JTextField tfNewTrain;

//	STATISCHE FRONT END
	private JPanel jPanel1;

	private JTextField tfCurrentTrain;

	private JButton btnNewTrain;
	private JButton btnDeleteRollingStock;
	private JButton btnAddRollingStock;

	private JPanel editPanel;
	private JPanel drawPanel;

	private HashMap numberOfWagons;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Main inst = new Main();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});

	

	}

	public Main() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			this.setTitle("Rich Rail");
			GridBagLayout thisLayout = new GridBagLayout();
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

			thisLayout.rowWeights = new double[] { 0.1, 0.1, 0.1, 0.1 };
			thisLayout.rowHeights = new int[] { 7, 7, 7, 7 };
			thisLayout.columnWeights = new double[] { 0.1, 0.1, 0.1, 0.1 };
			thisLayout.columnWidths = new int[] { 7, 7, 7, 7 };
			getContentPane().setLayout(thisLayout);
			{
				jPanel1 = new JPanel();
				jPanel1.setLayout(new BorderLayout());
				getContentPane().add(jPanel1, new GridBagConstraints(0, 0, 4, 2, 0.0, 0.0, GridBagConstraints.EAST,
						GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				{
					drawPanel = new JPanel();
					drawPanel.setBackground(Color.WHITE);
					jPanel1.add(drawPanel, BorderLayout.CENTER);
				}
			}
			{
				editPanel = new JPanel();
				GridBagLayout editPanelLayout = new GridBagLayout();
				editPanel.setLayout(editPanelLayout);
				getContentPane().add(editPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				{
					editPanel.add(new Label("Train Name:"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					editPanel.setBounds(10, 10, 100, 15);
					editPanelLayout.rowWeights = new double[] { 0.1, 0.1, 0.1, 0.1 };
					editPanelLayout.rowHeights = new int[] { 7, 7, 7, 7 };
					editPanelLayout.columnWeights = new double[] { 0.1, 0.1, 0.1, 0.1 };
					editPanelLayout.columnWidths = new int[] { 7, 7, 7, 7 };
				}
				{
					tfNewTrain = new JTextField(20);
					editPanel.add(tfNewTrain, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				}
				{
					btnNewTrain = new JButton("Create New Train");
					editPanel.add(btnNewTrain, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					btnNewTrain.addActionListener(this);
				}
				{
					String[] rollingOptions = { "First Class", "Freight", "Passenger", "Steam" };
					rollingBox = new JComboBox(rollingOptions);
					editPanel.add(rollingBox, new GridBagConstraints(1, 1, 1, 2, 0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

				}
				{
					btnAddRollingStock = new JButton("Add RollingStock");
					editPanel.add(btnAddRollingStock, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					btnAddRollingStock.addActionListener(this);
				}
				{
					btnDeleteRollingStock = new JButton("Delete RollingStock");
					editPanel.add(btnDeleteRollingStock, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					btnDeleteRollingStock.addActionListener(this);
				}
			}

			setSize(800, 600);
			numberOfWagons = new HashMap();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

//	EVENTHANDLER (kan netter door in de buttons te definen of in andere klas te zetten en die klasse aan te roepen)
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == btnNewTrain) {
			String trainName = tfNewTrain.getText();
			if (trainName != null && trainName.trim().length() > 0) {
				addTrain(trainName);
				drawTrain(train);
			}
		} else if (event.getSource() == btnAddRollingStock) {

			String selection = (String) rollingBox.getSelectedItem();
			addRollingStock(selection);
			drawTrain(train);

		} else if (event.getSource() == btnDeleteRollingStock) {

			String selection = (String) rollingBox.getSelectedItem();
			System.out.println("deleting " + selection);
			drawTrain(train);

		}
	}

// FUNCTIES (moeten uit deze klasse)

	public void addTrain(String name) {

		train = new Train();
		train.setName(name);

	}

	public boolean addRollingStock(String code) {
		RollingStock wagon;
		WagonDirector wagonDirector;
		if (code.equals("First Class")) {
			wagonBuilder = new FirstClassWagonBuilder();
		} else if (code.equals("Freight")) {
			wagonBuilder = new FreightWagonBuilder();
		} else if (code.equals("Passenger")) {
			wagonBuilder = new PassengerWagonBuilder();
		} else if (code.equals("Steam")) {
			allRollingStock.add(new SteamLocomotive());
			return true;
		} else {
			return false;
		}

		wagonDirector = new WagonDirector(wagonBuilder);
		wagonDirector.makeWagon();
		wagon = wagonDirector.getWagon();
		allRollingStock.add(wagon);

		return true;

	}

	public void drawTrain(Train train) {
		VisualComponent visualEngine = new VisualEngine();
		VisualComponent visualLocomotive = new VisualLocomotive();
		VisualComponent visualWagon = new VisualWagon();
		Graphics g = drawPanel.getGraphics();
		int index = 0;
		visualEngine.draw(train.getName(), 0, g);
		for (RollingStock r : allRollingStock) {
			index++;
			if (r instanceof Wagon) {
				visualWagon.draw(r.getType(), index, g);
			} else if (r instanceof Locomotive) {
				visualLocomotive.draw(r.getType(), index, g);
			}

		}

	}
}
