/**
 * The <code>NoSuchNodeException</code> is a custom exception that is thrown if
 * the node does not exist.
 * 
 * @author Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *         ID: 111230826
 **/
public class NoSuchNodeException extends Exception {

	/**
	 * The <code>NoSuchNodeException</code> is a custom exception that is thrown
	 * if the node does not exist.
	 * 
	 * @param message message indicating the error.
	 */
	public NoSuchNodeException(String message) {
		super(message);
	}
}
