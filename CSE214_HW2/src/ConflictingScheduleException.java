/**
 * The <code>ConflictingScheduleException</code> is a custom exception 
 * that is thrown if a Train object has an arrival/departure time that 
 * conflicts with another train object.
 * 
 *  @author Erik Bracamonte 
 *    e-mail: erik.bracamonte@stonybrook.edu
 *    Stony Brook ID: 111230826
 **/
public class ConflictingScheduleException extends Exception {
	/**
	 * The <code>ConflictingScheduleException</code> is a custom exception 
	 * that is thrown if a a Train object has an arrival/departure time that 
	 * conflicts with another train object.
	 * 
	 * @param message
	 * 	message indicating the error.
	 */
    public ConflictingScheduleException(String message) {
        super(message);
    }
}
