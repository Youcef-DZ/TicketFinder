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

public class SearchPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField fromTextField;
	private JTextField toTextField;

	/**
	 * Create the panel.
	 */
	public SearchPanel() {
		
		UtilDateModel model = new UtilDateModel();
		
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl departureDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		JDatePickerImpl arrivalDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		
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
		
		JButton btnSearch = new JButton("	Search	");
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JCheckBox chckbxNonstopOnly = new JCheckBox("Non-stop only");
		chckbxNonstopOnly.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(30)
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
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnSearch)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
							.addComponent(arrivalDatePicker, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(toTextField)))
					.addGap(28))
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
							.addComponent(lblArrival))
						.addComponent(arrivalDatePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
					.addGap(17))
		);
		setLayout(groupLayout);

	}
}


