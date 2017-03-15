import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

public class ResultsTable extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static DefaultTableModel dtm = new DefaultTableModel(0, 0);


	public ResultsTable() {
		super(new GridLayout(1, 0));

		String[] columnNames = { "Airline", "Departure", "Arrival", "Duration", "Price" };
		
		 dtm.setColumnIdentifiers(columnNames);

		JTable table = new JTable();
		table.setModel(dtm);
		
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Dialog", Font.BOLD, 18));

		table.setPreferredScrollableViewportSize(new Dimension(600, 70));
		table.setFillsViewportHeight(true);
		table.setEnabled(false);
		table.setFont(new Font("Arial", Font.PLAIN, 20));
		table.setRowHeight(30);

		
		JScrollPane scrollPane = new JScrollPane(table);

		add(scrollPane);
	}
	
	public void addFlight(Flight flight){
	    dtm.addRow(new Object[]{ flight.getAirLine(), flight.getOrigin(), 
	    		flight.getDestination(), flight.getDuration(), flight.getPrice() });
	}

}
