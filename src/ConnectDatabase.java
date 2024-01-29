import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ConnectDatabase {
    // Name, IATAkey map
    private static final Map<String, String> locations = new HashMap<>();

    /**
     * Database setup
     * Shall be called once only
     */
    public static void setup() {
     
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            Connection con = java.sql.DriverManager.getConnection("jdbc:hsqldb:file:./db/AppDB", "sa", "");
            Statement stmt = con.createStatement();       

            ResultSet resultSet = stmt.executeQuery("SELECT IATA, TYPE, NAME, PARENT_NAME FROM CITIES");
            while (resultSet.next()) {
                if (!"country".equals(resultSet.getString("TYPE"))) { // do not add countries
                    String iata = resultSet.getString("IATA");
                    String name = resultSet.getString("NAME");
                    locations.put(name, iata);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("DB SETUP DONE!");
    }

    public static List<Object> getNames() {
        return Arrays.asList(locations.keySet().parallelStream().sorted().toArray());
        //Object[] a = locations.keySet().toArray();
        //Arrays.sort(a);
        //return Arrays.asList(a);
    }

    public static String getIATAKey(String name) {
        return locations.get(name);
    }

}
