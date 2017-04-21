import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.border.EmptyBorder;


/**
 * @author Youcef Laidi
 */
public class FlightDetailsDialog extends JDialog {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Create the dialog.
     */
    public FlightDetailsDialog(Flight selectedFlight) {
        setBounds(100, 100, 450, 300);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        getContentPane().setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        String airline = selectedFlight.getAirLineName();
        String origin = selectedFlight.getOriginAirportName();
        //String destination = selectedFlight.getDestination();
        //int duration = selectedFlight.getDuration();
        //String price = selectedFlight.getPrice();

        getContentPane().add(contentPanel, BorderLayout.CENTER);
        JLabel lblairline;
        {
            lblairline = new JLabel(airline);
        }

        JLabel lblDepartdate = new JLabel(selectedFlight.getDepartureTime());
        JLabel lblDeparturecity = new JLabel(origin);
        GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
        gl_contentPanel.setHorizontalGroup(gl_contentPanel.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_contentPanel.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblairline)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblDepartdate)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblDeparturecity)
                                .addContainerGap(150, Short.MAX_VALUE))
        );
        gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_contentPanel.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPanel.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblairline)
                                        .addComponent(lblDepartdate)
                                        .addComponent(lblDeparturecity))
                                .addContainerGap(159, Short.MAX_VALUE))
        );
        contentPanel.setLayout(gl_contentPanel);
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton cancelButton = new JButton("Close");
                cancelButton.addActionListener(e -> {
                    setVisible(false);
                    dispose();
                });
                cancelButton.setActionCommand("Close");
                buttonPane.add(cancelButton);
            }
        }
    }
}
