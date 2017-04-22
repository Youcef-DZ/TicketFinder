import java.util.ArrayList;

/**
 * @author Youcef Laidi
 *
 */
public class Trip {
	private ArrayList<Flight> flights = new ArrayList<Flight>();
	private int tripDuration;
	private String totalPrice;
	
	/**
	 * @return origin Airport Code from first flight, index 0
	 */
	public String getOriginAirportCode() {	
		return flights.get(0).getOriginAirportCode();
	}
	/**
	 * @return origin City Name from first flight, index 0
	 */
	public String getOriginCityName() {
		return flights.get(0).getOriginCityName();
	}

	/**
	 * @return destination City Name from last flight, index(size - 1)
	 */
	public String getDestinationCityName() {
		return flights.get(flights.size() - 1).getDestinationCityName();
	}

	/**
	 * @return destination Airport Code from last flight, index(size - 1)
	 */
	public String getDestinationAirportCode() {
		return flights.get(flights.size() - 1).getDestinationAirportCode();
	}

	/**
	 * @return the totalPrice
	 */
	public String getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * @return the tripDuration
	 */
	public int getTripDuration() {
		return tripDuration;
	}

	/**
	 * @param tripDuration the tripDuration to set
	 */
	public void setTripDuration(int tripDuration) {
		this.tripDuration = tripDuration;
	}

	/**
	 * @param index
	 * @return
	 * @see java.util.ArrayList#get(int)
	 */
	public Flight getFlight(int index) {
		return flights.get(index);
	}

	/**
	 * @param flight
	 * @return
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	public void addFlight(Flight flight) {
		flights.add(flight);
	}

	/**
	 * @return
	 * @see java.util.ArrayList#size()
	 */
	public int getNumberOfFlights() {
		return flights.size();
	}
	
	public Trip() {
		
	}
	public String getDepartureTime() {
		return flights.get(0).getDepartureTime();
	}
	public String getArrivalTime() {
		return flights.get(flights.size() - 1).getArrivalTime();
	}
	public Object getFormatedDuration() {
		int hours = tripDuration / 60; 
		int minutes = tripDuration % 60;
		return hours + "h" + " "+ minutes+"m";
	}

	public String getAirLineNames() {
		String airlines = flights.get(0).getAirLineName();

		if (flights.size() > 1) { // add asterisk if more than one flight, possible multiple airlines
				airlines += "*";
		}
		return airlines;
	}
	
	public String getNumberOfStops() {
		return flights.size() == 1 ? "non-Stop": String.valueOf(flights.size()-1);
	}

}
