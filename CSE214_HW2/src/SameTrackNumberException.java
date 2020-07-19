/**
 * The <code>SameTrackNumberException</code> is a custom exception 
 * that is thrown if a Track object in a Station has the same track number 
 * as a new Track.
 * 
 *  @author Erik Bracamonte 
 *    e-mail: erik.bracamonte@stonybrook.edu
 *    Stony Brook ID: 111230826
 **/
public class SameTrackNumberException extends Exception {
	
	/**
	 * The <code>SameTrackNumberException</code> is a custom exception 
	 * that is thrown if a Track object in a Station has the same track number 
	 * as a new Track.
	 * 
	 * @param message
	 * 	message indicating the error.
	 */
    public SameTrackNumberException(String message) {
        super(message);
    }
}
