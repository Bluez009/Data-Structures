/**
 * The <code>InvalidTransactionException</code> is an exception that is 
 * thrown if the transaction amount is 0 or if the date is invalid.
 * 
 *  @author Erik Bracamonte 
 *    e-mail: erik.bracamonte@stonybrook.edu
 *    Stony Brook ID: 111230826
 **/
public class InvalidTransactionException extends Exception {
	/**
	 * The <code>InvalidTransactionException</code> is an exception that is 
	 * thrown if the transaction amount is 0 or if the date is invalid.
	 * 
	 * @param message
	 * 	message indicating the error.
	 */
    public InvalidTransactionException(String message) {
        super(message);
    }
}
