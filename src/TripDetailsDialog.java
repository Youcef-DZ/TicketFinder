import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.text.SimpleDateFormat;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;


/**
 * @author Youcef Laidi
 */
public class TripDetailsDialog extends JDialog {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH::mm");

    /**
     * Create the dialog.
     */
    public TripDetailsDialog(Trip selectedTrip) {
        setBounds(100, 100, 850, 400);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        getContentPane().setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        Flight selectedFlight = selectedTrip.getFlight(0);
        String airline = selectedFlight.getAirLineName();
        String originAirport = selectedFlight.getOriginAirportName();
        String originCityName = selectedFlight.getOriginCityName();
        String departureTime = selectedFlight.getDepartureTime();

        String destinationAirport = selectedFlight.getDestinationAirportName();
        String destinationCityName = selectedFlight.getDestinationCityName();
        String arrivalTime = selectedFlight.getArrivalTime();

        //int duration = selectedFlight.getDuration();
        //String price = selectedFlight.getPrice();

        getContentPane().add(contentPanel, BorderLayout.CENTER);
        JLabel lblairline;
        {
            lblairline = new JLabel(airline);
        }

        JLabel lblDepartdate = new JLabel(selectedFlight.getDepartureDate());
        JLabel lblDeparturecity = new JLabel(originCityName + " (" + originAirport + ")");        
        JLabel lblDeparturetime = new JLabel(departureTime);        
        JLabel lblArrivaltime = new JLabel(arrivalTime);        
        JLabel lblArrivalcity = new JLabel(destinationCityName+ " (" + destinationAirport + ")");        
        JLabel lblDuration = new JLabel(selectedFlight.getFormatedDuration());      
        JLabel lblMileage = new JLabel(selectedFlight.getMileage() + " Miles");        
        JLabel lblAircraft = new JLabel("aircraft");
        
        GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
        gl_contentPanel.setHorizontalGroup(
        	gl_contentPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPanel.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblairline)
        				.addComponent(lblDuration))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblDepartdate)
        				.addComponent(lblMileage))
        			.addGap(18)
        			.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_contentPanel.createSequentialGroup()
        					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
        						.addComponent(lblDeparturetime)
        						.addComponent(lblArrivaltime))
        					.addPreferredGap(ComponentPlacement.RELATED, 242, Short.MAX_VALUE)
        					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
        						.addComponent(lblArrivalcity)
        						.addComponent(lblDeparturecity))
        					.addGap(102))
        				.addGroup(gl_contentPanel.createSequentialGroup()
        					.addComponent(lblAircraft)
        					.addContainerGap(541, Short.MAX_VALUE))))
        );
        gl_contentPanel.setVerticalGroup(
        	gl_contentPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPanel.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblairline)
        				.addComponent(lblDepartdate)
        				.addComponent(lblDeparturecity)
        				.addComponent(lblDeparturetime))
        			.addGap(31)
        			.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblArrivaltime)
        				.addComponent(lblArrivalcity))
        			.addGap(71)
        			.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblDuration)
        				.addComponent(lblMileage)
        				.addComponent(lblAircraft))
        			.addContainerGap(117, Short.MAX_VALUE))
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
