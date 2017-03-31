import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MainFrame() {

		Font font = new Font("Tahoma", Font.PLAIN, 24);
		UIManager.put("Label.font", font);
		UIManager.put("Button.font", font);
		UIManager.put("ComboBox.font", font);
		UIManager.put("CheckBox.font", font);
		UIManager.put("Label.font", font);
		UIManager.put("RadioButton.font", font);
		UIManager.put("TextField.font", font);
		UIManager.put("TitledBorder.font", font);
		UIManager.put("FormattedTextField.font", font);

		JPanel searchPnl = new SearchPanel();
		add(searchPnl);

		setPreferredSize(new Dimension(950, 850));
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
