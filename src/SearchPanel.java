import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * @author Youcef Laidi
 */
public class SearchPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final JLabel lblDeparture = new JLabel("Depart: ");
    private final JLabel lblFrom = new JLabel("From:");
    private final JLabel lblReturn = new JLabel("Return: ");
    private final JLabel lblTo = new JLabel("To:");

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private final JCheckBox chckbxNonstopOnly = new JCheckBox("Non-stop only");
    private final JButton btnSearch = new JButton("\tSearch\t");
    private final Integer[] numbers = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private final String[] maxStops = new String[]{"", "1", "2", "3", "4", "5"};
    private final String[] cabins = new String[]{"", "COACH", "PREMIUM_COACH", "BUSINESS", "FIRST"};
    private final JLabel lblAdult = new JLabel("Adults:");
    private final JLabel lblChild = new JLabel("Child:");
    private final JLabel lblInfantInSeat = new JLabel("Infant In Seat:");
    private final JLabel lblMaxPrice = new JLabel("Max Price:");
    private final JLabel lblPreferedCabin = new JLabel("Prefered Cabin:");
    private final JLabel lblMaxStops = new JLabel("Max Stops:");
    private final JLabel lblSenior = new JLabel("Senior:");
    private final JLabel lblInfantInLap = new JLabel("Infant In Lap:");
    private final JComboBox<?> adultNcomboBox = new JComboBox<Object>(numbers);
    private final JComboBox<?> seniorNcomboBox = new JComboBox<Object>(numbers);
    private final JComboBox<?> childNcomboBox = new JComboBox<Object>(numbers);
    private final JComboBox<?> infantInSeatCB = new JComboBox<Object>(numbers);
    private final JComboBox<?> infantInLapCB = new JComboBox<Object>(numbers);
    private final JComboBox<?> maxStopsComboBox = new JComboBox<Object>(maxStops);
    private final JComboBox<?> preferredCabinCB = new JComboBox<Object>(cabins);
    private JTextField fromTextField;
    private JTextField toTextField;
    private JTextField maxPriceTextField;
    private JDateChooser departureDatePicker;

    /**
     * Create the panel.
     */
    public SearchPanel() {
        JPanel content = new JPanel();
        content.setLayout(new GridLayout(2, 1));

        JPanel topPanel = createTopPanel();
        content.add(topPanel);

        JPanel optionalPanel = createOptionalPanel();
        content.add(optionalPanel);

        GridBagConstraints gbc_searchPanel = new GridBagConstraints();
        gbc_searchPanel.insets = new Insets(0, 0, 5, 0);
        gbc_searchPanel.gridx = 0;
        gbc_searchPanel.gridy = 1;

        add(content, gbc_searchPanel);
    }

    /**
     * @return optionalPanel
     */
    private JPanel createOptionalPanel() {
        JPanel optionalPanel = new JPanel();
        setBorder(optionalPanel, "Optional");

        maxPriceTextField = new JTextField();
        maxPriceTextField.setColumns(10);

        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        chckbxNonstopOnly.addActionListener(arg0 -> maxStopsComboBox.setEnabled(!chckbxNonstopOnly.isSelected()));

        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        btnSearch.addActionListener(arg0 -> {
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
        });

        GridBagLayout gbl_optionalPanel = new GridBagLayout();
        gbl_optionalPanel.columnWidths = new int[]{103, 36, 49, 116, 133, 35, 90, 66, 0};
        optionalPanel.setLayout(gbl_optionalPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 1;
        optionalPanel.add(lblAdult, gbc);

        adultNcomboBox.removeItemAt(0); // an adult has to be present

        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 2;
        gbc.gridy = 1;
        optionalPanel.add(adultNcomboBox, gbc);

        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 4;
        gbc.gridy = 1;
        optionalPanel.add(lblMaxPrice, gbc);

        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 6;
        gbc.gridy = 1;
        optionalPanel.add(maxPriceTextField, gbc);

        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 2;
        optionalPanel.add(lblSenior, gbc);

        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 2;
        gbc.gridy = 2;
        optionalPanel.add(seniorNcomboBox, gbc);

        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 4;
        gbc.gridy = 2;
        optionalPanel.add(lblPreferedCabin, gbc);

        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridwidth = 2;
        gbc.gridx = 6;
        gbc.gridy = 2;
        optionalPanel.add(preferredCabinCB, gbc);

        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 3;
        optionalPanel.add(lblChild, gbc);

        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 2;
        gbc.gridy = 3;
        optionalPanel.add(childNcomboBox, gbc);

        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 4;
        gbc.gridy = 3;
        optionalPanel.add(chckbxNonstopOnly, gbc);

        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 6;
        gbc.gridy = 3;
        optionalPanel.add(lblMaxStops, gbc);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 7;
        gbc.gridy = 3;
        optionalPanel.add(maxStopsComboBox, gbc);

        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 4;
        optionalPanel.add(lblInfantInSeat, gbc);

        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 2;
        gbc.gridy = 4;
        optionalPanel.add(infantInSeatCB, gbc);

        gbc = new GridBagConstraints();
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight = 2;
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.gridx = 6;
        gbc.gridy = 4;
        optionalPanel.add(btnSearch, gbc);

        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.gridx = 0;
        gbc.gridy = 5;
        optionalPanel.add(lblInfantInLap, gbc);

        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.gridx = 2;
        gbc.gridy = 5;
        optionalPanel.add(infantInLapCB, gbc);

        return optionalPanel;
    }

    /**
     * @return JPanel
     */
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        setBorder(topPanel, "Search");

        Calendar cal = Calendar.getInstance(); // today
        departureDatePicker = new JDateChooser();
        JDateChooser returnDatePicker = new JDateChooser();

        Dimension dd = new Dimension(270, 60);
        departureDatePicker.setMaximumSize(dd);
        returnDatePicker.setMaximumSize(dd);

        departureDatePicker.setCalendar(cal);

        List<Object> airportsList = ConnectDatabase.getNames();

        AutoComboBox airportsCB = new AutoComboBox(airportsList);

        Dimension d = new Dimension(700, 60);

        toTextField = new AutoTextField(airportsList, airportsCB);
        toTextField.setAlignmentX(Component.RIGHT_ALIGNMENT);
        toTextField.setMaximumSize(d);

        topPanel.setLayout(new GridLayout(0, 1, 0, 5));

        Box topBox = Box.createVerticalBox();
        fromTextField = new AutoTextField(airportsList, airportsCB);
        fromTextField.setAlignmentX(Component.RIGHT_ALIGNMENT);
        fromTextField.setMaximumSize(d);

        Box fromBox = Box.createHorizontalBox();
        fromBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        fromBox.setAlignmentY(Component.CENTER_ALIGNMENT);
        lblFrom.setAlignmentX(Component.CENTER_ALIGNMENT);
        fromBox.add(lblFrom);
        fromBox.add(Box.createHorizontalStrut(57));
        fromBox.add(fromTextField);

        Box toBox = Box.createHorizontalBox();
        toBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        toBox.setAlignmentY(Component.CENTER_ALIGNMENT);
        toBox.add(lblTo);
        toBox.add(Box.createHorizontalStrut(90));
        toBox.add(toTextField);

        topBox.add(fromBox);
        topBox.add(Box.createVerticalStrut(10));
        topBox.add(toBox);

        topPanel.add(topBox);

        Box box = Box.createHorizontalBox();
        box.add(lblDeparture);
        int strut = 25;
        box.add(Box.createHorizontalStrut(strut));

        box.add(departureDatePicker);
        box.add(Box.createHorizontalStrut(strut));
        box.add(lblReturn);
        box.add(Box.createHorizontalStrut(strut));
        box.add(returnDatePicker);

        topPanel.add(box);

        return topPanel;
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

    private String getDepartureDate() {
        return sdf.format(departureDatePicker.getDate());
    }

    private String getPreferredCabin() {
        return preferredCabinCB.getSelectedItem().toString();
    }

    private int getAdultCount() {
        return (int) adultNcomboBox.getSelectedItem();
    }

    private int getChildCount() {
        return (int) childNcomboBox.getSelectedItem();
    }

    private int getInfantInLapCount() {
        return (int) infantInLapCB.getSelectedItem();
    }

    private int getInfantInSeatCount() {
        return (int) infantInSeatCB.getSelectedItem();
    }

    private int getSeniorCount() {
        return (int) seniorNcomboBox.getSelectedItem();
    }

    private int getMaxStops() {
        return Integer.parseInt(maxStopsComboBox.getSelectedItem().toString());
    }

    private double getMaxPrice() {
        return Double.parseDouble(maxPriceTextField.getText());
    }

    private String getDepartureAirport() {
        return ConnectDatabase.getIATAKey(fromTextField.getText());
    }

    private String getDestinationAirport() {
        return ConnectDatabase.getIATAKey(toTextField.getText());
    }

}
