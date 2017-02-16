import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.qpxExpress.QPXExpressRequestInitializer;
import com.google.api.services.qpxExpress.QPXExpress;
import com.google.api.services.qpxExpress.model.FlightInfo;
import com.google.api.services.qpxExpress.model.LegInfo;
import com.google.api.services.qpxExpress.model.PassengerCounts;
import com.google.api.services.qpxExpress.model.PricingInfo;
import com.google.api.services.qpxExpress.model.SegmentInfo;
import com.google.api.services.qpxExpress.model.SliceInfo;
import com.google.api.services.qpxExpress.model.TripOption;
import com.google.api.services.qpxExpress.model.TripOptionsRequest;
import com.google.api.services.qpxExpress.model.TripsSearchRequest;
import com.google.api.services.qpxExpress.model.SliceInput;
import com.google.api.services.qpxExpress.model.TripsSearchResponse;

// Testing new comment

public class AirlineReservation {

	private static final String APPLICATION_NAME = "MyFlightApplication";

	private static final String API_KEY = "AIzaSyA2-qsfEPiZbWoSIEbxj9yeTY0j8tmn0BU";

	
	private static HttpTransport httpTransport;

	
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	public static void main(String[] args)  {
	
		try {
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();

			PassengerCounts passengers = new PassengerCounts();
			passengers.setAdultCount(1);

			List<SliceInput> slices = new ArrayList<SliceInput>();

			SliceInput slice = new SliceInput();
			slice.setOrigin("NYC"); // from new york
			slice.setDestination("BOS"); // to boston
			slice.setDate("2017-04-29");
			
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
			List<TripOption> tripResults = list.getTrips().getTripOption();

			String id;

			for (int i = 0; i < tripResults.size(); i++) {
				// Trip Option ID
				id = tripResults.get(i).getId();
				System.out.println("id " + id);

				// Slice
				List<SliceInfo> sliceInfo = tripResults.get(i).getSlice();
				for (int j = 0; j < sliceInfo.size(); j++) {
					int duration = sliceInfo.get(j).getDuration();
					System.out.println("duration " + duration);
					
					List<SegmentInfo> segInfo = sliceInfo.get(j).getSegment();
					for (int k = 0; k < segInfo.size(); k++) {
						String bookingCode = segInfo.get(k).getBookingCode();
						FlightInfo flightInfo = segInfo.get(k).getFlight();
						String flightNum = flightInfo.getNumber();
						String flightCarrier = flightInfo.getCarrier();
						
						System.out.println("bookingCode " + bookingCode);
						System.out.println("flightNum " + flightNum);
						System.out.println("flightCarrier " + flightCarrier);
						
						List<LegInfo> leg = segInfo.get(k).getLeg();
						for (int l = 0; l < leg.size(); l++) {

							String aircraft = leg.get(l).getAircraft();							
							String arrivalTime = leg.get(l).getArrivalTime();					
							String departTime = leg.get(l).getDepartureTime();						
							String dest = leg.get(l).getDestination();						
							String destTer = leg.get(l).getDestinationTerminal();					
							String origin = leg.get(l).getOrigin();						
							String originTer = leg.get(l).getOriginTerminal();					
							int durationLeg = leg.get(l).getDuration();			
							int mil = leg.get(l).getMileage();
							
							System.out.println("aircraft " + aircraft);
							System.out.println("arrivalTime " + arrivalTime);
							System.out.println("departTime " + departTime);
							System.out.println("Destination " + dest);
							System.out.println("DestTer " + destTer);
							System.out.println("origun " + origin);
							System.out.println("OriginTer " + originTer);
							System.out.println("durationleg " + durationLeg);
							System.out.println("Milleage " + mil);
						}
					}
				}

				// Pricing
				List<PricingInfo> priceInfo = tripResults.get(i).getPricing();
				for (int p = 0; p < priceInfo.size(); p++) {
					String price = priceInfo.get(p).getSaleTotal();
					System.out.println("Price " + price);
				}

			}
			return;
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.exit(1);

	}
}