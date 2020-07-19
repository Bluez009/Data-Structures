/**
 * The <code>InvalidPositionException</code> is a custom exception 
 * that is thrown if a position in a stack is invalid.
 * 
 *  @author Erik Bracamonte 
 *    e-mail: erik.bracamonte@stonybrook.edu
 *    Stony Brook ID: 111230826
 **/
public class InvalidPositionException extends Exception {
	
	/**
	 * The <code>InvalidPositionException</code> is a custom exception 
	 * that is thrown if a position in a stack is invalid.
	 * 
	 * @param message
	 * 	message indicating the error.
	 */
    public InvalidPositionException(String message) {
        super(message);
    }
}
