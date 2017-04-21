import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.qpxExpress.QPXExpress;
import com.google.api.services.qpxExpress.QPXExpressRequestInitializer;
import com.google.api.services.qpxExpress.model.PassengerCounts;
import com.google.api.services.qpxExpress.model.SliceInput;
import com.google.api.services.qpxExpress.model.TripOptionsRequest;
import com.google.api.services.qpxExpress.model.TripsSearchRequest;
import com.google.api.services.qpxExpress.model.TripsSearchResponse;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Search {
    private static final String APPLICATION_NAME = "FlightSearch";
    //private static final String API_KEY = "AIzaSyAAhBNyRBLs9wddu7DybARnFi6vZTvFGqI";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String API_KEY = "AIzaSyCtmM90gvKkOFzG1D5DUPWfXPz5lKzxAik";
    private static HttpTransport httpTransport;
    private static Future<TripsSearchResponse> future;
    // legs, in minutes
    private final int solutions; // he number of solutions to return, maximum
    private String departure; // Airport or city IATA designator of the origin.
    private String destination; // Airport or city IATA designator of the
    // destination.
    private String departureDate; // Departure date in YYYY-MM-DD format.
    private String maxPrice;
    private int adultCount;
    private int childCount;
    private int infantInLapCount;
    private int infantInSeatCount;
    private int seniorCount;
    private int maxStops;
    private final int maxConnectionDuration; // The longest connection between two 500
    private final List<String> permittedCarrier; // A list of 2-letter IATA airline
    // designators.
    private final List<String> prohibitedCarrier; // A list of 2-letter IATA airline
    // BUSINESS, and FIRST.
    // designators. Exclude slices that
    // use these carriers.
    private String preferredCabin; // Allowed values are COACH, PREMIUM_COACH,

    /**
     * Default constructor will set the default values for the search
     */
    public Search() {
        adultCount = 1;
        childCount = 0;
        infantInLapCount = 0;
        infantInSeatCount = 0;
        maxPrice = "USD1000000"; // 1 million dollard by default
        solutions = 500;
        preferredCabin = null; // no preferrence
        maxConnectionDuration = 72 * 60; // 3 days
        permittedCarrier = null; // no preferrence
        prohibitedCarrier = null; // no preferrence
        maxStops = 10;
    }

    /**
     *
     */
    public void startSearch() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<TripsSearchResponse> callable;

        /*
         * (non-Javadoc)
         *
         * @see java.util.concurrent.Callable#call()
         */
        callable = () -> { // using lambda expression
            try {
                httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }

            PassengerCounts passengers = new PassengerCounts();
            passengers.setAdultCount(adultCount);
            passengers.setChildCount(childCount);
            passengers.setInfantInLapCount(infantInLapCount);
            passengers.setInfantInSeatCount(infantInSeatCount);
            passengers.setSeniorCount(seniorCount);

            List<SliceInput> slices = new ArrayList<>();

            SliceInput slice = new SliceInput();
            slice.setOrigin(departure);
            slice.setDestination(destination);
            slice.setDate(departureDate);
            slice.setMaxStops(maxStops);
            slice.setMaxConnectionDuration(maxConnectionDuration);
            slice.setPreferredCabin(preferredCabin);
            slice.setPermittedCarrier(permittedCarrier);
            slice.setProhibitedCarrier(prohibitedCarrier);

            slices.add(slice);

            TripOptionsRequest request = new TripOptionsRequest();
            request.setSolutions(solutions);
            request.setPassengers(passengers);
            request.setMaxPrice(maxPrice);
            request.setSlice(slices);

            TripsSearchRequest parameters = new TripsSearchRequest();
            parameters.setRequest(request);
            QPXExpress qpXExpress = new QPXExpress.Builder(httpTransport, JSON_FACTORY, null)
                    .setApplicationName(APPLICATION_NAME)
                    .setGoogleClientRequestInitializer(new QPXExpressRequestInitializer(API_KEY)).build();
            TripsSearchResponse list = qpXExpress.trips().search(parameters).execute();
            return list;
        };

        future = executor.submit(callable);
        executor.shutdown();
    }

    TripsSearchResponse getResults() {
        try {
            return future.get(20, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param maxPrice the maxPrice to set
     */
    public void setMaxPrice(double maxPrice) {
        this.maxPrice = "USD" + maxPrice;
    }

    /**
     * @param adultCount the adultCount to set
     */
    public void setAdultCount(int adultCount) {
        this.adultCount = adultCount;
    }

    /**
     * @param childCount the childCount to set
     */
    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    /**
     * @param seniorCount the seniorCount to set
     */
    public void setSeniorCount(int seniorCount) {
        this.seniorCount = seniorCount;
    }

    /**
     * @param maxStops the maxStops to set
     */
    public void setMaxStops(int maxStops) {
        this.maxStops = maxStops;
    }

    /**
     * @param preferredCabin the preferredCabin to set
     */
    public void setPreferredCabin(String preferredCabin) {
        this.preferredCabin = preferredCabin;
    }

    /**
     * @param infantInLapCount
     */
    public void setInfantInLapCount(int infantInLapCount) {
        this.infantInLapCount = infantInLapCount;
    }

    /**
     * @param infantInSeatCount
     */
    public void setInfantInSeatCount(int infantInSeatCount) {
        this.infantInSeatCount = infantInSeatCount;
    }

    /**
     * @return the departure
     */
    public String getDeparture() {
        return departure;
    }

    /**
     * @param departure the departure to set
     */
    public void setDeparture(String departure) {
        this.departure = departure;
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
     * @param departureDate the departureDate to set
     */
    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

}