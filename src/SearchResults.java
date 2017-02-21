import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Youcef Laidi
 *
 */
public class SearchResults extends ArrayList<Flight> {

	private static final long serialVersionUID = 1L;
	
	
	public SearchResults(){		
		Flight f1 = new Flight(1,200,"Delta","Boston","New York","C","D",1200,1400,2000,200);
		add(f1);
	}

}
