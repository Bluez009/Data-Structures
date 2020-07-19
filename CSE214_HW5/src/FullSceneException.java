/**
 * The <code>FullSceneException</code> is a custom exception that is thrown if
 * the current node does not have any empty child nodes.
 * 
 * @author Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *         ID: 111230826
 **/
public class FullSceneException extends Exception {

	/**
	 * The <code>FullSceneException</code> is a custom exception that is thrown
	 * if the current node does not have any empty child nodes.
	 * 
	 * @param message message indicating the error.
	 */
	public FullSceneException(String message) {
		super(message);
	}
}
