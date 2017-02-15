
import javax.swing.*;

import java.awt.Dimension;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainFrame() {
	JPanel searchPnl = new SearchPanel();
	add(searchPnl);
	
	setPreferredSize(new Dimension(800,600));// changed it to preferredSize, Thanks!



		setTitle("Ticket Finder");
		pack();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
					// "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
					// UIManager.getCrossPlatformLookAndFeelClassName());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				new MainFrame().setVisible(true);
			}
		});
	}
}
