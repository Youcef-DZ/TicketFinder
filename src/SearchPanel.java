import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import java.awt.Color;

public class SearchPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final Integer[] numbers = new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	private final String[] maxStops = new String[] { "", "1", "2", "3", "4", "5" };
	private final String[] cabins = new String[] { "", "COACH", "PREMIUM_COACH", "BUSINESS", "FIRST" };
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private final JButton btnSearch = new JButton("	Search	");
	private final JLabel lblDeparture = new JLabel("Depart: ");
	private final JLabel lblFrom = new JLabel("From:");
	private final JLabel lblReturn = new JLabel("Return: ");
	private final JLabel lblTo = new JLabel("To:");
	private final JLabel lblAdult = new JLabel("Adults:");
	private final JLabel lblChild = new JLabel("Child:");
	private final JLabel lblInfantInSeat = new JLabel("Infant In Seat:");
	private final JLabel lblMaxPrice = new JLabel("Max Price:");
	private final JLabel lblPreferedCabin = new JLabel("Prefered Cabin:");
	private final JLabel lblMaxStops = new JLabel("Max Stops:");
	private final JLabel lblSenior = new JLabel("Senior:");
	private final JLabel lblInfantInLap = new JLabel("Infant In Lap:");

	private JTextField fromTextField;
	private JTextField toTextField;
	private JTextField maxPriceTextField;

	private JDatePickerImpl departureDatePicker;
	private JDatePickerImpl returnDatePicker;

	private JRadioButton rdbtnOneway = new JRadioButton("One Way");
	private JRadioButton rdbtnRoundtrip = new JRadioButton("Rount Trip");
	private JCheckBox chckbxNonstopOnly = new JCheckBox("Non-stop only");

	private final JComboBox<?> adultNcomboBox = new JComboBox<Object>(numbers);
	private final JComboBox<?> seniorNcomboBox = new JComboBox<Object>(numbers);
	private final JComboBox<?> childNcomboBox = new JComboBox<Object>(numbers);
	private final JComboBox<?> infantInSeatCB = new JComboBox<Object>(numbers);
	private final JComboBox<?> infantInLapCB = new JComboBox<Object>(numbers);
	private final JComboBox<?> maxStopsComboBox = new JComboBox<Object>(maxStops);
	private final JComboBox<?> preferredCabinCB = new JComboBox<Object>(cabins);

	/**
	 * Create the panel.
	 */
	public SearchPanel() {
		Date date = new Date();
		UtilDateModel departureDateModel = new UtilDateModel(date);
		UtilDateModel arrivalDateModel = new UtilDateModel();

		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl dpd = new JDatePanelImpl(departureDateModel, p);
		JDatePanelImpl dpa = new JDatePanelImpl(arrivalDateModel, p);

		List<String> airportsList = new ArrayList<String>();
		airportsList.add("New York");
		airportsList.add("Boston");
		airportsList.add("Los Angeles");
		airportsList.add("Chicago");

		airportsList.add("JFK");
		airportsList.add("BOS");
		airportsList.add("LAX");
		airportsList.add("LGA");
		airportsList.add("PHL");

		AutoComboBox airportsCB = new AutoComboBox(airportsList);

		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Search s = new Search();
				s.setDeparture(getDepartureAirport());
				s.setDestination(getDestinationAirport());
				s.setDepartureDate(getDepartureDate());
				s.setAdultCount(getAdultCount());

				if (!infantInSeatCB.getSelectedItem().equals(0)) {
					s.setInfantInSeatCount(getInfantInSeatCount());
				}
				if (!infantInLapCB.getSelectedItem().equals(0)) {
					s.setInfantInLapCount(getInfantInLapCount());
				}
				if (!seniorNcomboBox.getSelectedItem().equals(0)) {
					s.setSeniorCount(getSeniorCount());
				}
				if (!childNcomboBox.getSelectedItem().equals(0)) {
					s.setChildCount(getChildCount());
				}
				if (!maxPriceTextField.getText().equals("")) {
					s.setMaxPrice(getMaxPrice());
				}
				if (maxStopsComboBox.isEnabled() && !maxStopsComboBox.getSelectedItem().equals("")) {
					s.setMaxStops(getMaxStops());
				}
				if (!preferredCabinCB.getSelectedItem().equals("")) {
					s.setPreferredCabin(getPreferredCabin());
				}
				if (chckbxNonstopOnly.isSelected()) {
					s.setMaxStops(0);
				}

				s.startSearch();
				new SearchResults(s);
			}
		});

		ButtonGroup grp = new ButtonGroup();
		grp.add(rdbtnOneway);
		grp.add(rdbtnRoundtrip);

		departureDatePicker = new JDatePickerImpl(dpd, new DateLabelFormatter());
		returnDatePicker = new JDatePickerImpl(dpa, new DateLabelFormatter());

		departureDatePicker.getJFormattedTextField().setBackground(Color.WHITE);
		returnDatePicker.getJFormattedTextField().setBackground(Color.WHITE);

		fromTextField = new AutoTextField(airportsList, airportsCB);
		fromTextField.setColumns(10);

		toTextField = new AutoTextField(airportsList, airportsCB);
		toTextField.setColumns(10);

		adultNcomboBox.removeItemAt(0); // an adult has to be present
		maxPriceTextField = new JTextField();
		maxPriceTextField.setColumns(10);

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("	Search	"));

		JPanel optionalPanel = new JPanel();
		optionalPanel.setBorder(BorderFactory.createTitledBorder("	Optional	"));

		rdbtnOneway.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				returnDatePicker.setVisible(rdbtnOneway.isSelected() ? false : true);
				lblReturn.setVisible(rdbtnOneway.isSelected() ? false : true);
			}
		});

		rdbtnRoundtrip.setSelected(true); // round trip is default
		rdbtnRoundtrip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				returnDatePicker.setVisible(true);
				lblReturn.setVisible(true);
			}
		});

		chckbxNonstopOnly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				maxStopsComboBox.setEnabled(chckbxNonstopOnly.isSelected() ? false : true);
			}
		});

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(
								groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup().addGap(83)
												.addComponent(rdbtnOneway).addGap(35).addComponent(rdbtnRoundtrip))
										.addGroup(groupLayout.createSequentialGroup().addContainerGap()
												.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
														.addComponent(panel, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
														.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 122,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(optionalPanel, Alignment.LEADING,
																GroupLayout.PREFERRED_SIZE, 832,
																GroupLayout.PREFERRED_SIZE))))
						.addContainerGap(38, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(rdbtnOneway)
								.addComponent(rdbtnRoundtrip))
						.addGap(18).addComponent(panel, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(optionalPanel, GroupLayout.PREFERRED_SIZE, 331, GroupLayout.PREFERRED_SIZE)
						.addGap(18).addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addGap(37)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(lblFrom)
						.addComponent(lblDeparture))
				.addGap(18)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(departureDatePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(fromTextField, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE))
				.addGap(53)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(lblTo).addComponent(lblReturn))
				.addPreferredGap(ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(toTextField, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)
						.addComponent(returnDatePicker, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE))
				.addGap(27)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lblFrom)
								.addComponent(fromTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTo).addComponent(toTextField, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addComponent(lblReturn)
								.addComponent(departureDatePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblDeparture).addComponent(returnDatePicker, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(24, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);

		GroupLayout gl_optionalPanel = new GroupLayout(optionalPanel);
		gl_optionalPanel.setHorizontalGroup(gl_optionalPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_optionalPanel.createSequentialGroup().addGap(35)
						.addGroup(gl_optionalPanel.createParallelGroup(Alignment.LEADING).addComponent(lblAdult)
								.addComponent(lblChild).addComponent(lblSenior).addComponent(lblInfantInSeat)
								.addComponent(lblInfantInLap))
						.addGap(36)
						.addGroup(gl_optionalPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_optionalPanel
								.createSequentialGroup()
								.addGroup(gl_optionalPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(seniorNcomboBox, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(childNcomboBox, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(adultNcomboBox, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(116)
								.addGroup(gl_optionalPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblMaxPrice).addComponent(lblPreferedCabin)
										.addComponent(chckbxNonstopOnly))
								.addGap(35)
								.addGroup(gl_optionalPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(maxPriceTextField, GroupLayout.PREFERRED_SIZE, 90,
												GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_optionalPanel.createParallelGroup(Alignment.LEADING, false)
												.addGroup(gl_optionalPanel
														.createSequentialGroup().addComponent(lblMaxStops)
														.addPreferredGap(ComponentPlacement.RELATED,
																GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(maxStopsComboBox, GroupLayout.PREFERRED_SIZE, 56,
																GroupLayout.PREFERRED_SIZE))
												.addComponent(preferredCabinCB, GroupLayout.PREFERRED_SIZE, 212,
														GroupLayout.PREFERRED_SIZE))))
								.addComponent(infantInSeatCB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(infantInLapCB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(101, Short.MAX_VALUE)));
		gl_optionalPanel.setVerticalGroup(gl_optionalPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_optionalPanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_optionalPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblAdult)
								.addComponent(adultNcomboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblMaxPrice).addComponent(maxPriceTextField, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_optionalPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblPreferedCabin)
								.addComponent(preferredCabinCB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblSenior).addComponent(seniorNcomboBox, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_optionalPanel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_optionalPanel.createParallelGroup(Alignment.BASELINE)
										.addComponent(childNcomboBox, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblChild))
								.addGroup(gl_optionalPanel.createParallelGroup(Alignment.BASELINE)
										.addComponent(chckbxNonstopOnly).addComponent(lblMaxStops).addComponent(
												maxStopsComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_optionalPanel.createParallelGroup(Alignment.LEADING).addComponent(lblInfantInSeat)
								.addComponent(infantInSeatCB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_optionalPanel.createParallelGroup(Alignment.LEADING).addComponent(lblInfantInLap)
								.addComponent(infantInLapCB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(90)));
		optionalPanel.setLayout(gl_optionalPanel);
		setLayout(groupLayout);

	}

	public String getDepartureDate() {
		return sdf.format(departureDatePicker.getModel().getValue());
	}

	public String getReturnDate() {
		return sdf.format(returnDatePicker.getModel().getValue());
	}

	public String getPreferredCabin() {
		return preferredCabinCB.getSelectedItem().toString();
	}

	public int getAdultCount() {
		return (int) adultNcomboBox.getSelectedItem();
	}

	public int getChildCount() {
		return (int) childNcomboBox.getSelectedItem();
	}

	public int getInfantInLapCount() {
		return (int) infantInLapCB.getSelectedItem();
	}

	public int getInfantInSeatCount() {
		return (int) infantInSeatCB.getSelectedItem();
	}

	public int getSeniorCount() {
		return (int) seniorNcomboBox.getSelectedItem();
	}

	public int getMaxStops() {
		return Integer.parseInt(maxStopsComboBox.getSelectedItem().toString());
	}

	public double getMaxPrice() {
		return Double.parseDouble(maxPriceTextField.getText());
	}

	public String getDepartureAirport() {
		if (fromTextField.getText().equals("New York")) {
			return "JFK";
		}
		if (fromTextField.getText().equals("Boston")) {
			return "BOS";
		}
		if (fromTextField.getText().equals("Los Angeles")) {
			return "LAX";
		}
		if (fromTextField.getText().equals("Chicago")) {
			return "ORD";
		}
		return fromTextField.getText();
	}

	public String getDestinationAirport() {
		if (toTextField.getText().equals("New York")) {
			return "JFK";
		}
		if (toTextField.getText().equals("Boston")) {
			return "BOS";
		}
		if (toTextField.getText().equals("Los Angeles")) {
			return "LAX";
		}
		if (toTextField.getText().equals("Chicago")) {
			return "ORD";
		}
		return toTextField.getText();
	}
}