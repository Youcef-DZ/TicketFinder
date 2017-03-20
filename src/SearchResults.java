import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.google.api.services.qpxExpress.model.FlightInfo;
import com.google.api.services.qpxExpress.model.LegInfo;
import com.google.api.services.qpxExpress.model.SegmentInfo;
import com.google.api.services.qpxExpress.model.SliceInfo;
import com.google.api.services.qpxExpress.model.TripOption;

/**
 * @author Youcef Laidi
 *
 */


public class SearchResults extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static DefaultTableModel dtm = new DefaultTableModel(0, 0);
	Search s;

	public SearchResults() {
		
		super(new GridLayout(1, 0));

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
	
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);

		setVisible(true);
	}
	
	
	public void setSearchParameters(Search s){
		this.s = s;
	}

	/**
	 * 
	 */
	public void getResults(Search s) {
		List<TripOption> tripResults = s.getResults();

		for (int i = 0; i < tripResults.size(); i++) {
			Flight tempFlight = new Flight();

			List<SliceInfo> sliceInfo = tripResults.get(i).getSlice();

			for (int j = 0; j < sliceInfo.size(); j++) {
				int duration = sliceInfo.get(j).getDuration();
				tempFlight.setDuration(duration);

				List<SegmentInfo> segInfo = sliceInfo.get(j).getSegment();

				for (int k = 0; k < segInfo.size(); k++) {
					//String bookingCode = segInfo.get(k).getBookingCode();
					FlightInfo flightInfo = segInfo.get(k).getFlight();
					String flightNum = flightInfo.getNumber();
					String flightCarrier = flightInfo.getCarrier();

					tempFlight.setFlightNumber(Integer.parseInt(flightNum));
					tempFlight.setAirLine(flightCarrier);

					List<LegInfo> leg = segInfo.get(k).getLeg();

					for (int l = 0; l < leg.size(); l++) {

						//String aircraft = leg.get(l).getAircraft();
						String arrivalTime = leg.get(l).getArrivalTime();
						String departureTime = leg.get(l).getDepartureTime();
						String dest = leg.get(l).getDestination();
						//String destTer = leg.get(l).getDestinationTerminal();
						String origin = leg.get(l).getOrigin();
						//String originTer = leg.get(l).getOriginTerminal();
						int durationLeg = leg.get(l).getDuration();
						int mil = leg.get(l).getMileage();

						tempFlight.setArrivalTime(arrivalTime);
						tempFlight.setDepartureTime(departureTime);
						tempFlight.setDestination(dest);
						tempFlight.setOrigin(origin);
						tempFlight.setMileage(mil);
						tempFlight.setDuration(durationLeg);

					}
				}
				String price = tripResults.get(i).getPricing().get(0).getSaleTotal();
				tempFlight.setPrice(price);

				addFlight(tempFlight);
			}
			
		}
	}
	
	public void addFlight(Flight flight){
	    dtm.addRow(new Object[]{ flight.getAirLine(), flight.getOrigin(), 
	    		flight.getDestination(), flight.getDuration(), flight.getPrice() });
	}

}