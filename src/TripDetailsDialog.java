import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

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
		getContentPane().setLayout(new BorderLayout());

		int numberOfFlights = selectedTrip.getNumberOfFlights();

		Box flightBox = Box.createVerticalBox();

		for (int i = 0; i < numberOfFlights; i++) {
			Flight selectedFlight = selectedTrip.getFlight(i);
			JPanel pane = singleFlightPanel(selectedFlight);

			flightBox.add(pane);
			flightBox.add(Box.createVerticalStrut(10));
		}
		
		   JScrollPane scrollPane = new JScrollPane(flightBox,
		            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);		
		   
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton cancelButton = new JButton("Close");
		cancelButton.addActionListener(e -> {
			setVisible(false);
			dispose();
		});
		
		buttonPane.add(cancelButton);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		setBounds(100, 100, (int) (dim.getWidth() - 200), (int) dim.getHeight() - 200);
		setVisible(true);
		//setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	/**
	 * @param selectedFlight
	 */
	private JPanel singleFlightPanel(Flight selectedFlight) {
		JPanel contentPanel = new JPanel();
		contentPanel.setBackground(Color.WHITE);
		setBorder(contentPanel, "Flight");

		String originAirport = selectedFlight.getOriginAirportName();
		String originCityName = selectedFlight.getOriginCityName();
		String originTerminal = selectedFlight.getOriginTerminal();
		String destinationAirport = selectedFlight.getDestinationAirportName();
		String destinationCityName = selectedFlight.getDestinationCityName();
		String arrivalTerminal = selectedFlight.getDestTerminal();

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblairline = new JLabel(selectedFlight.getAirLineName());
		JLabel lblDepartdate = new JLabel(selectedFlight.getDepartureDate());
		JLabel lblDeparturecity = new JLabel(originCityName + " (" + originAirport + ")");
		JLabel lblDeparturetime = new JLabel(selectedFlight.getDepartureTime() + "  Terminal: " + originTerminal);
		JLabel lblArrivaltime = new JLabel(selectedFlight.getArrivalTime() + "  Terminal: " + arrivalTerminal);
		JLabel lblArrivalcity = new JLabel(destinationCityName + " (" + destinationAirport + ")");
		JLabel lblDuration = new JLabel("Duration: " + selectedFlight.getFormatedDuration());
		JLabel lblMileage = new JLabel(selectedFlight.getMileage() + " Miles");
		JLabel lblAircraft = new JLabel(selectedFlight.getAircraft());
		JLabel lblFlight = new JLabel("Flight# " + selectedFlight.getFlightNumber());

		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblairline)
								.addComponent(lblFlight)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(lblDuration)
									.addGap(24)
									.addComponent(lblDepartdate)))
							.addGap(49)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDeparturetime)
								.addComponent(lblArrivaltime))
							.addGap(56)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblArrivalcity)
								.addComponent(lblDeparturecity)))
						.addComponent(lblMileage)
						.addComponent(lblAircraft))
					.addContainerGap(887, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblairline)
						.addComponent(lblDepartdate)
						.addComponent(lblDeparturetime)
						.addComponent(lblDeparturecity))
					.addGap(21)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblArrivaltime)
						.addComponent(lblFlight)
						.addComponent(lblArrivalcity))
					.addGap(12)
					.addComponent(lblDuration)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblMileage)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblAircraft)
					.addContainerGap(114, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		return contentPanel;
	}

	/**
	*
	*/
	private void setBorder(JPanel pnl, String title) {
		Border border = new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\t" + title + "\t",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0));
		Border margin = new EmptyBorder(10, 10, 10, 10);
		pnl.setBorder(new CompoundBorder(border, margin));
	}
}
