import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public MainFrame() {
	JPanel searchPnl = new SearchPanel();
	add(searchPnl);
	
	setPreferredSize(new Dimension(950, 650));

		setTitle("Ticket Finder");
		pack();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
				
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				new MainFrame().setVisible(true);
			}
		});
	}
}
