/**
 * The <code>NameNotFoundException</code> is a custom exception that is thrown 
 * if the patient name was not found in the list.
 * 
 * @author Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *         ID: 111230826
 **/
public class NameNotFoundException extends Exception {

	/**
	 * The <code>NameNotFoundException</code>  is a custom exception that is thrown 
	 * if the patient name was not found in the list.
	 * 
	 * @param message message indicating the error.
	 */
	public NameNotFoundException(String message) {
		super(message);
	}
}
