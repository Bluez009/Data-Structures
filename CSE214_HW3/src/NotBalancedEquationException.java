/**
 * The <code>NotBalancedEquationException</code> is a custom exception 
 * that is thrown if an equation is determined to be not balanced with closing
 * parentheses.
 * 
 *  @author Erik Bracamonte 
 *    e-mail: erik.bracamonte@stonybrook.edu
 *    Stony Brook ID: 111230826
 **/
public class NotBalancedEquationException extends Exception {
	
	/**
	 * The <code>NotBalancedEquationException</code> is a custom exception 
	 * that is thrown if an equation is determined to be not balanced with 
	 * closing parentheses.
	 * 
	 * @param message
	 * 	message indicating the error.
	 */
    public NotBalancedEquationException(String message) {
        super(message);
    }
}
