/**
 * The <code>InvalidLedgerPositionException</code> is a custom exception 
 * that is thrown if the position in the <class>GeneralLedger</class> does not
 *  exist.
 * 
 *  @author Erik Bracamonte 
 *    e-mail: erik.bracamonte@stonybrook.edu
 *    Stony Brook ID: 111230826
 **/
public class InvalidLedgerPositionException extends Exception {
	
	/**
	 * The <code>InvalidLedgerPositionException</code> is a custom exception 
	 * that is thrown if the position in the <class>GeneralLedger</class> does 
	 * not exist.
	 * 
	 * @param message
	 * 	message indicating the error
	 */
    public InvalidLedgerPositionException(String message) {
        super(message);
    }
}
