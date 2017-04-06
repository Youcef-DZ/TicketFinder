import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.Box;

/**
 * @author Youcef Laidi
 *
 */
public class App {

	private JFrame frame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		setFonts();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setVgap(10);
		borderLayout.setHgap(10);
		
		Box horizontalBox = Box.createHorizontalBox();
		JPanel searchPnl = new SearchPanel();
		horizontalBox.add(searchPnl);
		
		frame = new JFrame();
		frame.setTitle("Ticket Finder");
		frame.getContentPane().setLayout(borderLayout);
		frame.getContentPane().add(horizontalBox, BorderLayout.CENTER);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * 
	 */
	private void setFonts() {
		Font font = new Font("Tahoma", Font.PLAIN, 30);
		UIManager.put("Label.font", font);
		UIManager.put("Button.font", font);
		UIManager.put("ComboBox.font", font);
		UIManager.put("CheckBox.font", font);
		UIManager.put("Label.font", font);
		UIManager.put("RadioButton.font", font);
		UIManager.put("TextField.font", font);
		UIManager.put("TitledBorder.font", font);
		UIManager.put("FormattedTextField.font", font);
	}

}
