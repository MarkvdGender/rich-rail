package presentation;

import javax.swing.SwingUtilities;

import presentation.frame.CommandLineFrame;
import presentation.frame.DrawPanel;

public class GUI {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initGUI();
			}
		});

	}

	private static void initGUI() {
		CommandLineFrame.getInstance().showFrame();
		new DrawPanel().showFrame();
	}

}
