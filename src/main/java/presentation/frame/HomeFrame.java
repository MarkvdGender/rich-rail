package presentation.frame;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import domain.Train;
import domain.Wagon;
import domain.locomotive.Locomotive;
import service.train.trainobserver.TrainSubject;

public class HomeFrame extends JFrame implements Frame{
	
	private TrainSubject subject = TrainSubject.getInstance();

	private JComboBox rollingBox;
	private List<String> locoTypes = new ArrayList<String>();
	private JTextField tfNewTrain;
	private JPanel jPanel1;
	private JButton btnNewTrain;
	private JButton btnDeleteRollingStock;
	private JButton btnAddRollingStock;

	private JPanel editPanel;
	private JPanel drawPanel;

	public HomeFrame() {

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
//					drawPanel.setBackground(Color.WHITE);
					jPanel1.add(drawPanel, BorderLayout.CENTER);
				}
			}
			subject.addObserver(this);
			{
				editPanel = new JPanel();
				GridBagLayout editPanelLayout = new GridBagLayout();
				editPanel.setLayout(editPanelLayout);
				getContentPane().add(editPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				{
					editPanel.add(new Label("Train id:"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
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
					btnNewTrain.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							subject.newTrain(tfNewTrain.getText(), rollingBox.getSelectedItem().toString());

						}
					});
				}
				{
					rollingBox = new JComboBox(locoTypes.toArray());
					editPanel.add(rollingBox, new GridBagConstraints(1, 1, 1, 2, 0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

				}
				{
					btnAddRollingStock = new JButton("Add RollingStock");
					editPanel.add(btnAddRollingStock, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					btnAddRollingStock.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Frame cmlf = CommandLineFrame.getInstance();
							cmlf.showFrame();

						}
					});
				}
				{
					btnDeleteRollingStock = new JButton("Delete RollingStock");
					editPanel.add(btnDeleteRollingStock, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					btnDeleteRollingStock.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Frame cmlf = CommandLineFrame.getInstance();
							cmlf.hideFrame();

						}
					});
				}
			}

			setSize(800, 600);
//			setVisible(true);
			setLocationRelativeTo(null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(List<Train> trains, List<Wagon> wagons, List<Locomotive> locomotives) {
		for(Locomotive l : locomotives) {
			locoTypes.add(l.getType());
		}
	}

	@Override
	public void showFrame() {
		setVisible(true);
		
	}

	@Override
	public void hideFrame() {
		setVisible(false);
		
	}

}
