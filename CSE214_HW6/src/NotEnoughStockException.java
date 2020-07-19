import java.io.Serializable;

/**
 * The <code>NotEnoughStockException</code> is a custom exception that is thrown
 * if the quantity of the item is less than the requested amount to buy.
 * 
 * @author Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *         ID: 111230826
 **/
public class NotEnoughStockException extends Exception implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The <code>NotEnoughStockException</code> is a custom exception that is
	 * thrown if the quantity of the item is less than the requested amount to
	 * buy.
	 * 
	 * @param message message indicating the error.
	 */
	public NotEnoughStockException(String message) {
		super(message);
	}
}
