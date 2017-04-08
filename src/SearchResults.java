import com.google.api.services.qpxExpress.model.*;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

/**
 * @author Youcef Laidi
 *
 */

public class SearchResults extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final DefaultTableModel dtm = new DefaultTableModel(0, 0);

	public SearchResults(Search s) {

		new GridLayout(1, 0);

		String[] columnNames = { "Airline", "Departure", "Arrival", "Duration", "Price" };

		dtm.setColumnIdentifiers(columnNames);

		JTable table = new JTable();
		table.setModel(dtm);

		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Dialog", Font.BOLD, 18));

		table.setFillsViewportHeight(true);
		table.setEnabled(false);
		table.setFont(new Font("Arial", Font.PLAIN, 20));
		table.setRowHeight(30);
		
		populateResults(s);

		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);

		setVisible(true);
		setPreferredSize(new Dimension(850, 650));
		setTitle("Results");
		pack();
	}

	/**
	 * 
	 */
	private void populateResults(Search s) {

		dtm.setRowCount(0); // clear table if previous results are there
		List<TripOption> tripResults = s.getResults();
		if (tripResults != null) {
			ConnectDatabase cb = new ConnectDatabase();

			tripResults.forEach(tripOption -> { // using lambda expression
				Flight tempFlight = new Flight();
				List<SliceInfo> sliceInfo = tripOption.getSlice();

				sliceInfo.forEach(sliceIn -> { // using lambda expression
					int duration = sliceIn.getDuration();
					tempFlight.setDuration(duration);

					List<SegmentInfo> segInfo = sliceIn.getSegment();
					segInfo.forEach(segIn -> { // using lambda expression
						// String bookingCode = segInfo.get(k).getBookingCode();
						FlightInfo flightInfo = segIn.getFlight();
						String flightNum = flightInfo.getNumber();
						String flightCarrier = flightInfo.getCarrier();

						tempFlight.setFlightNumber(Integer.parseInt(flightNum));
						tempFlight.setAirLine(cb.getAirlineName(flightCarrier));

						List<LegInfo> leg = segIn.getLeg();

						leg.forEach(l -> { // using lambda expression
							// String aircraft = leg.get(l).getAircraft();
							String arrivalTime = l.getArrivalTime();
							String departureTime = l.getDepartureTime();
							String dest = l.getDestination();
							// String destTer =
							// leg.get(l).getDestinationTerminal();
							String origin = l.getOrigin();
							// String originTer =
							// leg.get(l).getOriginTerminal();
							int durationLeg = l.getDuration();
							int mil = l.getMileage();

							tempFlight.setArrivalTime(arrivalTime);
							tempFlight.setDepartureTime(departureTime);
							tempFlight.setDestination(dest);
							tempFlight.setOrigin(origin);
							tempFlight.setMileage(mil);
							tempFlight.setDuration(durationLeg);

						});
					});
					String price = tripOption.getPricing().get(0).getSaleTotal();
					tempFlight.setPrice(price);

					// add flight to table
					dtm.addRow(new Object[] { tempFlight.getAirLineName(), tempFlight.getOrigin(),
							tempFlight.getDestination(), tempFlight.getDuration(), tempFlight.getPrice() });
				});
				
				

			});
		} else {
			System.out.println("No results found.");
			System.out.println("from " + s.getDeparture() + " to " + s.getDestination());

		}
	}

}