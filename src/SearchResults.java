import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import com.google.api.services.qpxExpress.model.AirportData;
import com.google.api.services.qpxExpress.model.CarrierData;
import com.google.api.services.qpxExpress.model.CityData;
import com.google.api.services.qpxExpress.model.FlightInfo;
import com.google.api.services.qpxExpress.model.LegInfo;
import com.google.api.services.qpxExpress.model.SegmentInfo;
import com.google.api.services.qpxExpress.model.SliceInfo;
import com.google.api.services.qpxExpress.model.TripOption;
import com.google.api.services.qpxExpress.model.TripOptionsResponse;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static javax.swing.UIManager.getColor;

/**
 * @author Youcef Laidi
 */

public class SearchResults extends JFrame {

	private static final long serialVersionUID = 1L;
	private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm a");
	private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEE, MMM dd");


	private final DefaultTableModel dtm;
	private Flight tempFlight;

	public SearchResults(Search s) {

		new GridLayout(1, 0);

		String[] columnNames = { "Airline", "Departure", "From > To", "Arrival", "Duration", "Price", "Details",
				"Flight Instance" };

		dtm = new DefaultTableModel(0, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 6;
			}
		};

		dtm.setColumnIdentifiers(columnNames);

		JTable table = new JTable();
		table.setModel(dtm);

		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Dialog", Font.BOLD, 20));

		table.setFillsViewportHeight(true);
		table.setFont(new Font("Arial", Font.PLAIN, 24));
		table.setRowHeight(40);

		table.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(6).setCellEditor(new TableRenderer(new JCheckBox()));
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(7));
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		table.setShowHorizontalLines(true);
		table.setShowVerticalLines(false);

		populateResults(s);

		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);

		setVisible(true);
		setPreferredSize(new Dimension(1050, 700));
		setTitle("Results");
		pack();
	}

	/**
	 *
	 */
	private void populateResults(Search s) {
		dtm.setRowCount(0); // clear table if previous results are there
		boolean noResults = true;
		TripOptionsResponse response = s.getResults().getTrips();
		if (response != null) {
			noResults = false;

			List<TripOption> tripResults = response.getTripOption();

			Map<String, CityData> cities = response.getData().getCity().stream()
					.collect(Collectors.toMap(CityData::getCode, Function.identity()));

			Map<String, AirportData> airports = response.getData().getAirport().stream()
					.collect(Collectors.toMap(AirportData::getCode, Function.identity()));

			Map<String, CarrierData> carriers = response.getData().getCarrier().stream()
					.collect(Collectors.toMap(CarrierData::getCode, Function.identity()));

			if (tripResults != null) {
				/*
				 * (non-Javadoc)
				 * 
				 * @see java.util.function.Consumer#accept(java.lang.Object)
				 */
				tripResults.forEach(tripOption -> { // using lambda expression
					tempFlight = new Flight();
					List<SliceInfo> sliceInfo = tripOption.getSlice();
					/*
					 * (non-Javadoc)
					 * 
					 * @see java.util.function.Consumer#accept(java.lang.Object)
					 */
					sliceInfo.forEach(sliceIn -> { // using lambda expression
						int duration = sliceIn.getDuration();

						tempFlight.setDuration(duration);

						List<SegmentInfo> segInfo = sliceIn.getSegment();
						/*
						 * (non-Javadoc)
						 * 
						 * @see
						 * java.util.function.Consumer#accept(java.lang.Object)
						 */
						segInfo.forEach(segIn -> { // using lambda expression
							// String bookingCode = segIn.getBookingCode();
							FlightInfo flightInfo = segIn.getFlight();
							String flightNum = flightInfo.getNumber();
							String carrierCode = flightInfo.getCarrier();
							String carrierName = shortCarrierName(carriers.get(carrierCode).getName());
							tempFlight.setFlightNumber(Integer.parseInt(flightNum));
							tempFlight.setAirLine(carrierName);

							List<LegInfo> leg = segIn.getLeg();
							/*
							 * (non-Javadoc)
							 * 
							 * @see
							 * java.util.function.Consumer#accept(java.lang.
							 * Object)
							 */
							leg.forEach(l -> { // using lambda expression
								String departureDate = l.getDepartureTime();
								String arrivalDate = l.getArrivalTime();
								
								ZonedDateTime d = ZonedDateTime.parse(departureDate);
								String departureTime = d.format(timeFormat);
								departureDate = d.format(dateFormat);
								
								d = ZonedDateTime.parse(arrivalDate);
								String arrivalTime = d.format(timeFormat);
								arrivalDate = d.format(dateFormat);								
								
								int mil = l.getMileage();

								String aircraft = l.getAircraft();
								String originTer = l.getOriginTerminal();
								String originAirportCode = l.getOrigin();
								String originAirportName = airports.get(originAirportCode).getName();
								String originCityCode = airports.get(originAirportCode).getCity();
								String originCityName = cities.get(originCityCode).getName();

								String destTer = l.getDestinationTerminal();
								String destinationAirportCode = l.getDestination();
								String destinationAirportName = airports.get(destinationAirportCode).getName();
								String destinationCityCode = airports.get(destinationAirportCode).getCity();
								String destinationCityName = cities.get(destinationCityCode).getName();

								// int durationLeg = l.getDuration(); duration
								// for single flight

								tempFlight.setDestinationTerminal(destTer);
								tempFlight.setOriginTerminal(originTer);
								tempFlight.setAircraft(aircraft);
								tempFlight.setArrivalTime(arrivalTime);
								tempFlight.setDepartureTime(departureTime);
								tempFlight.setArrivalDate(arrivalDate);
								tempFlight.setDepartureDate(departureDate);
								tempFlight.setOriginCityName(originCityName);
								tempFlight.setDestinationCityName(destinationCityName);
								tempFlight.setDestinationAirportCode(destinationAirportCode);
								tempFlight.setOriginAirportCode(originAirportCode);
								tempFlight.setDestinationAirportName(destinationAirportName);
								tempFlight.setOriginAirportName(originAirportName);
								tempFlight.setMileage(mil);
							});
						});
						String price = tripOption.getPricing().get(0).getSaleTotal();
						tempFlight.setPrice(price);

						// add flight to table
						dtm.addRow(new Object[] { tempFlight.getAirLineName(), tempFlight.getDepartureTime(),
								tempFlight.getOriginAirportCode() + " > " + tempFlight.getDestinationAirportCode(),
								tempFlight.getArrivalTime(), tempFlight.getFormatedDuration(), tempFlight.getPrice(),
								"FLIGHT DETAILS", tempFlight });
					});

				});
			}
		}  
		
		if(!noResults){
			System.out.println("No results found.");
			System.out.println("from " + s.getDeparture() + " to " + s.getDestination());
		}
		
	}

	/**
	 * @param carrierName
	 * @return carrier name with only the first two words, deleting the comma
	 */
	private String shortCarrierName(String carrierName) {
		String[] word = carrierName.split(" ");
		String name = word[0];

		if (word.length > 1) {
			if (word[1].endsWith(",")) {
				StringBuilder builder = new StringBuilder(word[1]);
				builder.deleteCharAt(word[1].length() - 1);
				word[1] = builder.toString();
			}
			name += " ";
			name += word[1];
		}
		return name;
	}

}

/**
 * @author Youcef 
 *         <p>
 *         Code below was originated from user Bitmap at:
 *         http://stackoverflow.com/a/10348919/3850242 </br>
 *         I have optimized it to suit my JTable needs on 4/12/2017
 *         <p>
 */
class ButtonRenderer extends JButton implements TableCellRenderer {

	private static final long serialVersionUID = 1L;

	public ButtonRenderer() {
		setOpaque(true);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		setForeground(Color.black);
		setBackground(getColor("Button.background"));
		setText((value == null) ? "" : value.toString());
		return this;
	}
}

class TableRenderer extends DefaultCellEditor {

	private static final long serialVersionUID = 1L;
	private final JButton button;
	private String label;
	private boolean clicked;
	private int row;
	private JTable table;

	public TableRenderer(JCheckBox checkBox) {
		super(checkBox);
		button = new JButton();
		button.setOpaque(true);
		button.addActionListener(e -> fireEditingStopped());
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		this.table = table;
		this.row = row;
		button.setForeground(Color.black);
		button.setBackground(getColor("Button.background"));
		label = (value == null) ? "" : value.toString();
		button.setText(label);
		clicked = true;
		return button;
	}

	@Override
	public Object getCellEditorValue() {
		if (clicked) {
			Flight selectedFlight = (Flight) table.getModel().getValueAt(row, 7);
			new FlightDetailsDialog(selectedFlight);
		}
		clicked = false;
		return label;
	}

	@Override
	public boolean stopCellEditing() {
		clicked = false;
		return super.stopCellEditing();
	}

}
