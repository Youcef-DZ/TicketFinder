import com.google.api.services.qpxExpress.model.*;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author Youcef Laidi
 *
 */

public class SearchResults extends JFrame {

	private static final long serialVersionUID = 1L;

	private DefaultTableModel dtm;

	public SearchResults(Search s) {

		new GridLayout(1, 0);

		String[] columnNames = { "Airline", "Departure", "Arrival", "Duration", "Price", "Details" };

		dtm = new DefaultTableModel(0, 0) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return column == 5;
			}
		};

		dtm.setColumnIdentifiers(columnNames);

		JTable table = new JTable();
		table.setModel(dtm);

		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Dialog", Font.BOLD, 18));

		table.setFillsViewportHeight(true);
		table.setFont(new Font("Arial", Font.PLAIN, 20));
		table.setRowHeight(30);

		table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(5).setCellEditor(new TableRenderer(new JCheckBox()));
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		table.setShowHorizontalLines(true);
		table.setShowVerticalLines(false);

		populateResults(s);

		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);

		setVisible(true);
		setPreferredSize(new Dimension(900, 650));
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
						tempFlight.setAirLine(ConnectDatabase.getAirlineName(flightCarrier));

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
							tempFlight.getDestination(), tempFlight.getDuration(), tempFlight.getPrice(), "Details" });
				});

			});
		} else {
			System.out.println("No results found.");
			System.out.println("from " + s.getDeparture() + " to " + s.getDestination());
		}
	}

}

/**
 * 
 * @author Youcef
 * 
 * Code below was originated from user Bitmap at:
 * http://stackoverflow.com/a/10348919/3850242
 * 
 * I have optimized it to suit JTable needs on 4/12/2017
 *
 */
class ButtonRenderer extends JButton implements TableCellRenderer {

	private static final long serialVersionUID = 1L;

	public ButtonRenderer() {
		setOpaque(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		setForeground(Color.black);
		setBackground(UIManager.getColor("Button.background"));
		setText((value == null) ? "" : value.toString());
		return this;
	}
}

class TableRenderer extends DefaultCellEditor {

	private static final long serialVersionUID = 1L;
	private JButton button;
	private String label;
	private boolean clicked;
	private int row;
	private JTable table;

	public TableRenderer(JCheckBox checkBox) {
		super(checkBox);
		button = new JButton();
		button.setOpaque(true);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
			}
		});
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		this.table = table;
		this.row = row;

		button.setForeground(Color.black);
		button.setBackground(UIManager.getColor("Button.background"));
		label = (value == null) ? "" : value.toString();
		button.setText(label);
		clicked = true;
		return button;
	}

	public Object getCellEditorValue() {
		if (clicked) {
			new FlightDetailsDialog();
			//JOptionPane.showMessageDialog(button, "Column with Value: " + table.getValueAt(row, 1) + " -  Clicked!");
		}
		clicked = false;
		return new String(label);
	}

	public boolean stopCellEditing() {
		clicked = false;
		return super.stopCellEditing();
	}

	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}
}
