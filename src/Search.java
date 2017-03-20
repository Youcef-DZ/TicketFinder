import java.io.IOException;

import java.net.MalformedURLException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.qpxExpress.QPXExpress;
import com.google.api.services.qpxExpress.QPXExpressRequestInitializer;
import com.google.api.services.qpxExpress.model.PassengerCounts;
import com.google.api.services.qpxExpress.model.SliceInput;
import com.google.api.services.qpxExpress.model.TripOption;
import com.google.api.services.qpxExpress.model.TripOptionsRequest;
import com.google.api.services.qpxExpress.model.TripsSearchRequest;
import com.google.api.services.qpxExpress.model.TripsSearchResponse;

public class Search {
	private static final String APPLICATION_NAME = "FlightSearch";
	private static final String API_KEY = "AIzaSyAAhBNyRBLs9wddu7DybARnFi6vZTvFGqI";
	private static HttpTransport httpTransport;
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	private String departure; // Airport or city IATA designator of the origin.	
	private String destination; // Airport or city IATA designator of the destination.	
	private String departureDate; // Departure date in YYYY-MM-DD format.
	private int maxPrice;
	private int adultCount;
	private int childCount;
	private int seniorCount;
	private int maxStops;
	private int maxConnectionDuration; // The longest connection between two legs, in minutes
	private int solutions; // he number of solutions to return, maximum 500
	private String[] permittedCarrier; // A list of 2-letter IATA airline designators.
	private String[] prohibitedCarrier; // A list of 2-letter IATA airline designators. Exclude slices that use these carriers.
	private String preferredCabin; // Allowed values are COACH, PREMIUM_COACH, BUSINESS, and FIRST.
	
	private List<TripOption> tripResults;
	private Future<List<TripOption>> future;

	public Search() {

		ExecutorService executor = Executors.newSingleThreadExecutor();
		Callable<List<TripOption>> callable;
		
		callable = new Callable<List<TripOption>>() {
			@Override
			public List<TripOption> call() throws IOException, MalformedURLException {
				try {
					httpTransport = GoogleNetHttpTransport.newTrustedTransport();
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
				}

				PassengerCounts passengers = new PassengerCounts();
				passengers.setAdultCount(1);

				List<SliceInput> slices = new ArrayList<SliceInput>();

				SliceInput slice = new SliceInput();
				slice.setOrigin(departure); 
				slice.setDestination(destination);
				slice.setDate(departureDate);
				
				slices.add(slice);

				TripOptionsRequest request = new TripOptionsRequest();
				request.setSolutions(10);
				request.setPassengers(passengers);
				request.setSlice(slices);

				TripsSearchRequest parameters = new TripsSearchRequest();
				parameters.setRequest(request);
				QPXExpress qpXExpress = new QPXExpress.Builder(httpTransport, JSON_FACTORY, null)
						.setApplicationName(APPLICATION_NAME)
						.setGoogleClientRequestInitializer(new QPXExpressRequestInitializer(API_KEY)).build();
				TripsSearchResponse list = qpXExpress.trips().search(parameters).execute();
				tripResults = list.getTrips().getTripOption();
				return tripResults;
			}
		};
		
		 future = executor.submit(callable);
		executor.shutdown();
	}
	
	List<TripOption> getResults(){
		try {
			return future.get(10, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}
		return null;
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
	 * @return the departureDate
	 */
	public String getDepartureDate() {
		return departureDate;
	}

	/**
	 * @param departureDate the departureDate to set
	 */
	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

}