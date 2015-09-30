//	 imports for implementation of ErrorHandler
//	 this code has been taken from the CD that accompanies the text
//	 XML and Java 2nd Edition, Developing Web Applications 
//	 by Maruyama,Tamura, Uramoto, et al
// It was subsequently modified by PAB
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

public class MyErrorHandler implements ErrorHandler 
{

	// the following boolean values were added by PAB so that the
	// code could determine if there was a problem during validation
	private boolean validationWarning = false;
	private boolean validationError = false;
	private boolean validationFatalError = false;

    /** Constructor. */
    public MyErrorHandler(){
    }
    /** Warning. */
    public void warning(SAXParseException ex) {

		validationWarning = true;

        System.err.println("[Warning] "+
                           getLocationString(ex)+": "+
                           ex.getMessage());
    }
    /** Error. */
    public void error(SAXParseException ex) {

		validationError = true;

        System.err.println("[Error] "+
                           getLocationString(ex)+": "+
                           ex.getMessage());
    }
    /** Fatal error. */
    public void fatalError(SAXParseException ex) {

		validationFatalError = true;

        System.err.println("[Fatal Error] "+
                           getLocationString(ex)+": "+
                           ex.getMessage());
    }

    /** Returns a string of the location. */
    private String getLocationString(SAXParseException ex) {
        StringBuffer str = new StringBuffer();

        String systemId = ex.getSystemId();
        if (systemId != null) {
            int index = systemId.lastIndexOf('/');
            if (index != -1)
                systemId = systemId.substring(index + 1);
            str.append(systemId);
        }
        str.append(':');
        str.append(ex.getLineNumber());
        str.append(':');
        str.append(ex.getColumnNumber());

        return str.toString();
    }

	public boolean errorEncountered()
	{
		return( validationError | validationFatalError );
	}
}