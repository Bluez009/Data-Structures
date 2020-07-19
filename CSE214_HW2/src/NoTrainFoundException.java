/**
 * The <code>NoTrainFoundException</code> is a custom exception that is thrown 
 * if a there was no train found during selection.
 * 
 * 
 *  @author Erik Bracamonte 
 *    e-mail: erik.bracamonte@stonybrook.edu
 *    Stony Brook ID: 111230826
 **/
public class NoTrainFoundException extends Exception {
	
	/**
	 * The <code>NoTrainFoundException</code> is a custom exception that is 
	 * thrown if a there was no train found during selection.
	 * 
	 * @param message
	 * 	message indicating the error.
	 */
    public NoTrainFoundException(String message) {
        super(message);
    }
}
