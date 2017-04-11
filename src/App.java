import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * @author Youcef Laidi
 *
 */
public class App {

    private JFrame frame;

    /**
     * Launch the application.
     *
     * @param args
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> { // using lambda expression
            try {
                App window = new App();
                window.frame.setVisible(true);
            } catch (Exception e) {
            }
        });
    }

    /**
     * Create the application.
     */
    public App() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
		ConnectDatabase.setup();
    	setFonts();
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
