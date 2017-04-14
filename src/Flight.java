/**
 * @author Youcef Laidi
 */

public class Flight {
    private int flightNumber;
    private String price;
    private String airLineCode;
    private String origin;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private int duration;
    private int mileage;

    public Flight() {

    }

    /**
     * @param flightNumber
     * @param price
     * @param airLine
     * @param origin
     * @param destination
     * @param originTerminal
     * @param destinationTerminal
     * @param departureTime
     * @param arrivalTime
     * @param duration
     */
    public Flight(int flightNumber, String price, String airLine, String origin, String destination, String originTerminal,
                  String destinationTerminal, String departureTime, String arrivalTime, int duration, int mileage) {

        this.flightNumber = flightNumber;
        this.price = price;
        this.airLineCode = airLine;
        this.origin = origin;
        this.destination = destination;
        String originTerminal1 = originTerminal;
        String destinationTerminal1 = destinationTerminal;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        this.mileage = mileage;

    }

    /**
     * @param flightNumber the flightNumber to set
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
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * @param airLine the airLineCode to set
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
     * @return the origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin to set
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @param destination the destination to set
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * @return the departureTime
     */
    public String getDepartureTime() {
        return departureTime;
    }

    /**
     * @param departureTime the departureTime to set
     */
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * @param arrivalTime the arrivalTime to set
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
     * @param duration the duration to set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * @param mileage the mileage to set
     */
    public void setMileage(int mileage) {
        this.mileage = mileage;
    }
}
