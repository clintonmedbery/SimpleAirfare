import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class SimpleFaresTaskService {

	public static void main( String args[] ) throws Exception
	{	
                //Need to get the two airport codes
                InputStreamReader input = new InputStreamReader(System.in);
                BufferedReader reader = new BufferedReader(input);
            
                
                String departureCode = null;
                String destinationCode = null;
                
                while(departureCode == null && destinationCode == null){
                    System.out.println("This program gives you the ticket price for"
        		+ "a flight from one state to another.");
                    System.out.println("What is the abbreviation for the departure state?");
                    
                    try {
			departureCode = reader.readLine();
                        System.out.println("What is the abbreviation for the destination state?");
			destinationCode = reader.readLine();
			
			
                    } catch (IOException e) {
			System.out.println(e);
                    }
                    
                }
                
                System.out.println(departureCode);
                System.out.println(destinationCode);

            
		// call the FAA web service Airport Status web service
		// to convert airport code to state name
		String baseURL = "http://services.faa.gov/airport/status/" + departureCode + "?format=application/xml";
		String query = "";
		String resultQuery = "//State";
		String departureStateName = RestfulServiceSupport.queryWithXMLReturn( baseURL, query, resultQuery );
                
                baseURL = "http://services.faa.gov/airport/status/" + destinationCode + "?format=application/xml";
                query = "";
                String destinationStateName = RestfulServiceSupport.queryWithXMLReturn( baseURL, query, resultQuery );
                
		System.out.println( "Airport code for departure: " + departureCode + " is in the following state: " + departureStateName );
                System.out.println( "Airport code for destination: " + destinationCode + " is in the following state: " + destinationStateName );

		
		// call the exist rest service to convert state name to state code for departure
		baseURL = "http://52.26.87.189:8080/exist/rest/db/simplefares/stateZones.xml";
		query = "?_query=data(//state[@name='" + departureStateName + "']/@code)";
		resultQuery = "//exist:value";	
		String departureStateCode = RestfulServiceSupport.queryWithXMLReturn( baseURL, query, resultQuery );
                query = "?_query=data(//state[@name='" + destinationStateName + "']/@code)";
                String destinationStateCode = RestfulServiceSupport.queryWithXMLReturn( baseURL, query, resultQuery );

		System.out.println( "State name: " + departureStateName + " has the following code: " + departureStateCode );
                System.out.println( "State name: " + destinationStateName + " has the following code: " + destinationStateCode );

                
                // call the exist rest service to convert state name to state code for destination


		// call the exist rest service to convert state code to zone
		baseURL = "http://52.26.87.189:8080/exist/rest/db/simplefares/stateZones.xml";
		query = "?_query=data(//state[@code='" + departureStateCode + "']/../@id)";
		resultQuery = "//exist:value";
		String departureZone = RestfulServiceSupport.queryWithXMLReturn( baseURL, query, resultQuery );
                
                query = "?_query=data(//state[@code='" + destinationStateCode + "']/../@id)";
		String destinationZone = RestfulServiceSupport.queryWithXMLReturn( baseURL, query, resultQuery );

		System.out.println( "The zone for state: " + departureStateCode + " is: " + departureZone );
                System.out.println( "The zone for state: " + destinationStateCode + " is: " + destinationZone );
                
                baseURL = "http://52.26.87.189:8080/exist/rest/db/simplefares/airfareRules.xml";
                query = "?_query=data(//antecedent[contains(@to,'" + destinationZone+ "') and contains(@from,'"+ departureZone +"')]/../consequent/@fare)";
                resultQuery = "//exist:value";
                String totalFare = RestfulServiceSupport.queryWithXMLReturn( baseURL, query, resultQuery );
                System.out.println( totalFare );


	}
}
