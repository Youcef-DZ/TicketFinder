import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectDatabase {
	// Name, IATAkey map
	private Map<String, String> locations = new HashMap<>();
	// Code, Airline Name map
	private Map<String, String> airlines = new HashMap<>();

	Statement stmt = null;
	ResultSet result2;
	int result = 0;

	public ConnectDatabase() {

		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			Connection con = DriverManager.getConnection("jdbc:hsqldb:file:./db/AppDB", "sa", "");
			stmt = con.createStatement();

			// result = stmt.executeUpdate("CREATE TEXT TABLE AIRLINES (\"CODE\"
			// VARCHAR(50), \"NAME\" VARCHAR(50));");
			// result = stmt.executeUpdate("SET TABLE \"AIRLINES\" SOURCE
			// \"AIRLINES.csv\"");
			// con.commit();

			result2 = stmt.executeQuery("SELECT CODE, NAME FROM AIRLINES");
			while (result2.next()) {
				String code = result2.getString("CODE");
				String name = result2.getString("NAME");
				airlines.put(code, name);
			}

			result2 = stmt.executeQuery("SELECT IATA, TYPE, NAME, PARENT_NAME FROM CITIES");
			while (result2.next()) {
				if (!result2.getString("TYPE").equals("country")) { // do not
																	// search
																	// between
																	// countries
					String iata = result2.getString("IATA");
					String name = result2.getString("NAME");
					locations.put(name, iata);
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("DONE!");
	}

	public List<Object> getNames() {
		return Arrays.asList(locations.keySet().parallelStream().sorted().toArray());
		//Object[] a = locations.keySet().toArray();
		//Arrays.sort(a);
		//return Arrays.asList(a);
	}

	public String getIATAKey(String name) {
		return locations.get(name);
	}

	public String getAirlineName(String code) {
		return airlines.get(code);
	}
}