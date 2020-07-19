/**
 * The <code>SameTrainNumberException</code> is a custom exception 
 * that is thrown if a Train object in a Track has the same train number 
 * as a new Train.
 * 
 *  @author Erik Bracamonte 
 *    e-mail: erik.bracamonte@stonybrook.edu
 *    Stony Brook ID: 111230826
 **/
public class SameTrainNumberException extends Exception {
	
	/**
	 * The <code>SameTrainNumberException</code> is a custom exception 
	 * that is thrown if a Train object in a Track has the same train number 
	 * as a new Train.
	 * 
	 * @param message
	 * 	message indicating the error.
	 */
    public SameTrainNumberException(String message) {
        super(message);
    }
}
