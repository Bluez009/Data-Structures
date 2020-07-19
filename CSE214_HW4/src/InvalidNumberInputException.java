
/**
 * The <code>InvalidNumberInputException</code> is a custom exception 
 * that is thrown if a number is invalid and set beyond the boundary.
 * 
 *  @author Erik Bracamonte 
 *    e-mail: erik.bracamonte@stonybrook.edu
 *    Stony Brook ID: 111230826
 **/
public class InvalidNumberInputException extends Exception {
	
	/**
	 * The <code>InvalidNumberInputException</code> is a custom exception 
	 * that is thrown if a number is invalid and set beyond the boundary.
	 * 
	 * @param message
	 * 	message indicating the error.
	 */
    public InvalidNumberInputException(String message) {
        super(message);
    }
}
