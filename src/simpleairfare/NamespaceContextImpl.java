import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.NamespaceContext;

public class NamespaceContextImpl implements NamespaceContext
{
	private HashMap<String, String> prefixToUri;

	 public NamespaceContextImpl( String prefix, String uri )
	 {
		 prefixToUri = new HashMap<String, String>();
		 prefixToUri.put( prefix, uri );
	 }	 
	 
	 public NamespaceContextImpl( String prefix [], String uri )
	 {
		 prefixToUri = new HashMap<String, String>();
		 for( int i=0; i<prefix.length; i++ )
			 prefixToUri.put( prefix[i], uri ); 
	 }
	 
	 public String getNamespaceURI( String prefix )
	 {
	   return prefixToUri.get( prefix );
	 }

	 public String getPrefix( String uri )
	 {
		 String rtnval = null;
		 Set<String> keys = prefixToUri.keySet();
		 Iterator<String> itr = keys.iterator();
		 while( itr.hasNext() )
		 {
			 String prefix = (String)itr.next();
			 if( (prefixToUri.get(prefix)).equals( uri ) )
			 {
				 rtnval = prefix;
				 break;
			 }
		 }
		 
		 return rtnval;
	 }
	 
	 public Iterator<String> getPrefixes( String uri )
	 {
		 ArrayList<String> prefixes = new ArrayList<String>();
		 
		 Set<String> keys = prefixToUri.keySet();
		 Iterator<String> itr = keys.iterator();
		 while( itr.hasNext() )
		 {
			 String prefix = (String)itr.next();
			 if( (prefixToUri.get(prefix)).equals( uri ) )
			 {
				 prefixes.add( prefix );
			 }
		 }
		 
		 return prefixes.iterator();	 
	 }
	 
	 
	 public static void main( String args [] )
	 {
		 String URI = "URI used for testing purposes";
		 
		 NamespaceContextImpl namespaceContext = new NamespaceContextImpl( "m", URI );
		 
		 System.out.println( "The uri is: " + namespaceContext.getNamespaceURI( "m" ));
		 
		 System.out.println( "The prefix is: " + namespaceContext.getPrefix( URI ));
		 
		 String prefixes[] = { "m", "n", "o", "p"};
		 namespaceContext = new NamespaceContextImpl( prefixes, URI );
		 
		 Iterator<String> itr = namespaceContext.getPrefixes( URI );
		 
		 System.out.print( "Prefixes for URI: " );
		 while( itr.hasNext() )
		 {
			 System.out.print( itr.next() + " " );
		 }
		 System.out.println();
	 }
	 
}
