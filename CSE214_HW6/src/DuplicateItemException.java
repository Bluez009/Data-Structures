import java.io.Serializable;

/**
 * The <code>DuplicateItemExceptionn</code> is a custom exception that is thrown
 * if a duplicate item is found in the hash table.
 * 
 * @author Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *         ID: 111230826
 **/
public class DuplicateItemException extends Exception implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The <code>DuplicateItemException</code> is a custom exception that is
	 * thrown if a duplicate item is found in the hash table.
	 * 
	 * @param message message indicating the error.
	 */
	public DuplicateItemException(String message) {
		super(message);
	}
}
