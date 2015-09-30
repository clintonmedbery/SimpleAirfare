import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;


@SuppressWarnings("javadoc")
public class RestfulServiceSupport {

	public static String queryWithXMLReturn( String baseURL, String dbQuery, String resultQuery ) throws Exception
	{
		String rtnval = null;
		
		String serviceAddress = baseURL + dbQuery;
		
		// change spaces to %20
		serviceAddress = serviceAddress.replace(" ", "%20");

		URL restURL = new URL(serviceAddress);
		HttpURLConnection connection = (HttpURLConnection)restURL.openConnection();
		
		connection.setRequestMethod( "GET" );
		
		connection.connect();
			
		if( connection.getResponseCode() == 200 )
		{
			// grab the input stream associated with the content
			InputStream in = connection.getInputStream();
			
			StringBuffer sb = new StringBuffer();
			
			// establish an inputStreamReader and tell it the data is in UTF-8
		    Reader reader = new InputStreamReader(in, "UTF-8");
		    int c;
		    
		    //read a character at a time, appending into the StringBuffer
		    while ((c = reader.read()) != -1) sb.append((char) c);
		    
			connection.disconnect();
			
			if( resultQuery != null )
			{
			    // convert the returned data into a DOM
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				factory.setNamespaceAware(true);
				DocumentBuilder parser = factory.newDocumentBuilder();
                                
                                
				InputSource inputSource = new InputSource( new StringReader( sb.toString() ) );
			    Document xmldoc = parser.parse( inputSource ); 
			    
				XPath xpath = XPathFactory.newInstance().newXPath(); 
				
				 Element root = xmldoc.getDocumentElement();
				 String prefix = root.getPrefix();
				 String namespaceURI = root.lookupNamespaceURI( prefix );
				 xpath.setNamespaceContext( new NamespaceContextImpl( prefix, namespaceURI ) );
                                 
					
			    rtnval = xpath.evaluate( resultQuery, xmldoc );
			}
			else
				rtnval = sb.toString();
		}
		else
			connection.disconnect();

		return rtnval;
	}
}	
