import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

public class SearchPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField fromTextField;
	private JTextField toTextField;
	JDatePickerImpl departureDatePicker;
	JDatePickerImpl returnDatePicker;
	final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Search s = new Search();

	/**
	 * Create the panel.
	 */
	public SearchPanel() {
		
		UtilDateModel departureDateModel = new UtilDateModel();
		UtilDateModel arrivalDateModel = new UtilDateModel();
		
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl dpd = new JDatePanelImpl(departureDateModel, p);
		JDatePanelImpl dpa = new JDatePanelImpl(arrivalDateModel, p);
		
		departureDatePicker = new JDatePickerImpl(dpd, new DateLabelFormatter());
		returnDatePicker = new JDatePickerImpl(dpa, new DateLabelFormatter());
		
		List<String> airportsList = new ArrayList<String>();
		airportsList.add("JFK");
		airportsList.add("BOS");
		airportsList.add("LAX");
		airportsList.add("LGA");
		airportsList.add("PHL");

		AutoComboBox airportsCB = new AutoComboBox(airportsList);
		
		JLabel lblDeparture = new JLabel("Departure: ");
		lblDeparture.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblArrival = new JLabel("Arrival: ");
		lblArrival.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblFrom = new JLabel("From:");
		lblFrom.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		fromTextField = new AutoTextField(airportsList, airportsCB);
		fromTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		fromTextField.setColumns(10);
		
		JLabel lblTo = new JLabel("To:");
		lblTo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		toTextField =  new AutoTextField(airportsList, airportsCB);
		toTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		toTextField.setColumns(10);
		
		JPanel resultFrame = new SearchResults();
		
		JButton btnSearch = new JButton("	Search	");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				s.setDeparture(fromTextField.getText());
				s.setDestination(toTextField.getText());
				s.setDepartureDate(getDepartureDate());
				s.startSearch();
				((SearchResults) resultFrame).getResults(s);
				resultFrame.setVisible(true);
			}
		});
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JCheckBox chckbxNonstopOnly = new JCheckBox("Non-stop only");
		chckbxNonstopOnly.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(30)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(resultFrame, GroupLayout.PREFERRED_SIZE, 850, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblDeparture)
											.addGap(30)
											.addComponent(departureDatePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblFrom)
											.addGap(81)
											.addComponent(fromTextField)))
									.addGap(35)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblTo)
										.addComponent(lblArrival)))
								.addComponent(chckbxNonstopOnly))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnSearch)
								.addComponent(toTextField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(returnDatePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(54, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(30)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(lblDeparture))
						.addComponent(departureDatePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblArrival)
								.addComponent(returnDatePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGap(3)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblFrom)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(toTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblTo))
						.addComponent(fromTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnSearch)
						.addComponent(chckbxNonstopOnly))
					.addGap(39)
					.addComponent(resultFrame, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
					.addGap(35))
		);
		setLayout(groupLayout);

	}
	
	public String getDepartureDate(){
		return sdf.format(departureDatePicker.getModel().getValue());
	}
	
	public String getReturnDate(){
		return sdf.format(returnDatePicker.getModel().getValue());
	}
	
	public String getDepartureAirport(){
		return fromTextField.getText();
	}
	
	public String getArrivalAirport(){
		return toTextField.getText();
	}
}