/**
 * @author Youcef Laidi
 */

public class Flight {
	
	private String aircraft;
	private String originAirportCode;
	private String destinationAirportCode;
	private String originAirportName;
	private String destinationAirportName;

	private String originCityName;
	private String destinationCityName;
	
	private String departureDate;

	private String departureTime;
	private String arrivalDate;
	private String arrivalTime;
	private String originTerminal;
	private String destinationTerminal;

	private String price;
	private String airLineCode;
	private String airLineName;
	private String cabin;

	private int duration;
	private int mileage;
	private int flightNumber;

	private int adultCount;
	private int childCount;
	private int infantInLapCount;
	private int infantInSeatCount;
	private int seniorCount;

	/**
	 * Default constructor 
	 */
	public Flight() {

	}

	/**
	 * @return the formatedDuration
	 */
	public String getFormatedDuration() {
		int hours = duration / 60; 
		int minutes = duration % 60;
		return hours + "h" + " "+ minutes+"m";
	}
	
	/**
	 * @return the arrivalDate
	 */
	public String getArrivalDate() {
		return arrivalDate;
	}

	/**
	 * @param arrivalDate
	 *            the arrivalDate to set
	 */
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	/**
	 * @param aircraft
	 *            the aircraft to set
	 */
	public void setAircraft(String aircraft) {
		this.aircraft = aircraft;
	}

	/**
	 * @param originTerminal
	 *            the originTerminal to set
	 */
	public void setOriginTerminal(String originTerminal) {
		this.originTerminal = originTerminal;
	}

	/**
	 * @param destinationTerminal
	 *            the destinationTerminal to set
	 */
	public void setDestinationTerminal(String destinationTerminal) {
		this.destinationTerminal = destinationTerminal;
	}

	/**
	 * @param airLineName
	 *            the airLineName to set
	 */
	public void setAirLineName(String airLineName) {
		this.airLineName = airLineName;
	}

	/**
	 * @return airLineName
	 */
	public String setAirLineName() {
		return airLineName;
	}
	
	/**
	 * @return the originCityName
	 */
	public String getOriginCityName() {
		return originCityName;
	}

	/**
	 * @param originCityName the originCityName to set
	 */
	public void setOriginCityName(String originCityName) {
		this.originCityName = originCityName;
	}

	/**
	 * @return the destinationCityName
	 */
	public String getDestinationCityName() {
		return destinationCityName;
	}

	/**
	 * @param destinationCityName the destinationCityName to set
	 */
	public void setDestinationCityName(String destinationCityName) {
		this.destinationCityName = destinationCityName;
	}


	/**
	 * @return the originCode
	 */
	public String getOriginAirportCode() {
		return originAirportCode;
	}

	/**
	 * @param originCode
	 *            the originCode to set
	 */
	public void setOriginAirportCode(String originCode) {
		this.originAirportCode = originCode;
	}

	/**
	 * @return the destinationCode
	 */
	public String getDestinationAirportCode() {
		return destinationAirportCode;
	}

	/**
	 * @param destinationCode
	 *            the destinationCode to set
	 */
	public void setDestinationAirportCode(String destinationCode) {
		this.destinationAirportCode = destinationCode;
	}

	/**
	 * @return the originName
	 */
	public String getOriginAirportName() {
		return originAirportName;
	}

	/**
	 * @param originName
	 *            the originName to set
	 */
	public void setOriginAirportName(String originName) {
		this.originAirportName = originName;
	}

	/**
	 * @return the destinationName
	 */
	public String getDestinationAirportName() {
		return destinationAirportName;
	}

	/**
	 * @param destinationName
	 *            the destinationName to set
	 */
	public void setDestinationAirportName(String destinationName) {
		this.destinationAirportName = destinationName;
	}

	/**
	 * @return the departureDate
	 */
	public String getDepartureDate() {
		return departureDate;
	}

	/**
	 * @param departureDate
	 *            the departureDate to set
	 */
	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	/**
	 * @return the airLineCode
	 */
	public String getAirLineCode() {
		return airLineCode;
	}

	/**
	 * @param airLineCode
	 *            the airLineCode to set
	 */
	public void setAirLineCode(String airLineCode) {
		this.airLineCode = airLineCode;
	}

	/**
	 * @return the cabin
	 */
	public String getCabin() {
		return cabin;
	}

	/**
	 * @param cabin
	 *            the cabin to set
	 */
	public void setCabin(String cabin) {
		this.cabin = cabin;
	}

	/**
	 * @return the adultCount
	 */
	public int getAdultCount() {
		return adultCount;
	}

	/**
	 * @param adultCount
	 *            the adultCount to set
	 */
	public void setAdultCount(int adultCount) {
		this.adultCount = adultCount;
	}

	/**
	 * @return the childCount
	 */
	public int getChildCount() {
		return childCount;
	}

	/**
	 * @param childCount
	 *            the childCount to set
	 */
	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}

	/**
	 * @return the infantInLapCount
	 */
	public int getInfantInLapCount() {
		return infantInLapCount;
	}

	/**
	 * @param infantInLapCount
	 *            the infantInLapCount to set
	 */
	public void setInfantInLapCount(int infantInLapCount) {
		this.infantInLapCount = infantInLapCount;
	}

	/**
	 * @return the infantInSeatCount
	 */
	public int getInfantInSeatCount() {
		return infantInSeatCount;
	}

	/**
	 * @param infantInSeatCount
	 *            the infantInSeatCount to set
	 */
	public void setInfantInSeatCount(int infantInSeatCount) {
		this.infantInSeatCount = infantInSeatCount;
	}

	/**
	 * @return the seniorCount
	 */
	public int getSeniorCount() {
		return seniorCount;
	}

	/**
	 * @param seniorCount
	 *            the seniorCount to set
	 */
	public void setSeniorCount(int seniorCount) {
		this.seniorCount = seniorCount;
	}

	/**
	 * @return the arrivalTime
	 */
	public String getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * @return the destinationTerminal
	 */
	public String getDestinationTerminal() {
		return destinationTerminal;
	}

	/**
	 * @return the mileage
	 */
	public int getMileage() {
		return mileage;
	}

	/**
	 * @return the flightNumber
	 */
	public int getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber
	 *            the flightNumber to set
	 */
	public void setFlightNumber(int flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return "$" + price.substring(3);
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @param airLine
	 *            the airLineCode to set
	 */
	public void setAirLine(String airLine) {
		this.airLineCode = airLine;
	}

	/**
	 * @return the airLine
	 */
	public String getAirLineName() {
		return airLineCode;
	}

	/**
	 * @return the departureTime
	 */
	public String getDepartureTime() {
		return departureTime;
	}

	/**
	 * @param departureTime
	 *            the departureTime to set
	 */
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	/**
	 * @param arrivalTime
	 *            the arrivalTime to set
	 */
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @param mileage
	 *            the mileage to set
	 */
	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	/**
	 * @return the aircraft
	 */
	public String getAircraft() {
		return aircraft;
	}

	/**
	 * @return the origin Terminal
	 */
	public String getOriginTerminal() {
		return originTerminal;
	}

	/**
	 * @return the destination Terminal
	 */
	public String getDestTerminal() {
		return destinationTerminal;
	}
}
