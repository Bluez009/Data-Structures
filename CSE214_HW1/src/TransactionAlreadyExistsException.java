/**
 * The <code>TransactionAlreadyExistsException</code> is a custom exception 
 * that is thrown if the transaction has the same values as the one its being 
 * compared to.
 * 
 *  @author Erik Bracamonte 
 *    e-mail: erik.bracamonte@stonybrook.edu
 *    Stony Brook ID: 111230826
 **/
public class TransactionAlreadyExistsException extends Exception {
	/**
	 * The <code>TransactionAlreadyExistsException</code> is a custom exception 
	 * that is thrown if the transaction has the same values as the one its 
	 * being compared to.
	 * 
	 * @param message
	 * 	message indicating the error.
	 */
    public TransactionAlreadyExistsException(String message) {
        super(message);
    }
}
