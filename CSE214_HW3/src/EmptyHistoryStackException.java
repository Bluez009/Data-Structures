/**
 * The <code>EmptyHistoryStackException</code> is a custom exception 
 * that is thrown if the stack is empty and data is being popped/retrieved.
 * 
 *  @author Erik Bracamonte 
 *    e-mail: erik.bracamonte@stonybrook.edu
 *    Stony Brook ID: 111230826
 **/
public class EmptyHistoryStackException extends Exception {
	
	/**
	 * The <code>EmptyHistoryStackException</code> is a custom exception 
	 * that is thrown if the stack is empty and data is being popped/retrieved.
	 * 
	 * @param message
	 * 	message indicating the error.
	 */
    public EmptyHistoryStackException(String message) {
        super(message);
    }
}

