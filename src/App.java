import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

/**
 * @author Youcef Laidi
 */
public class App {

    private JFrame frame;

    /**
     * Create the application.
     */
    private App() {
        initialize();
    }

    /**
     * Launch the application.
     *
     * @param args
     */
    public static void main(String[] args) {
        /* (non-Javadoc)
         * @see java.lang.Runnable#run()
         */
        EventQueue.invokeLater(() -> { // using lambda expression
            try {
                App window = new App();
                window.frame.setVisible(true);
            } catch (Exception ignored) {
            }
        }); 
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
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
        UIManager.put("ScrollBar.width", 40);
        UIManager.put("FormattedTextField.font", font);
    }

}
