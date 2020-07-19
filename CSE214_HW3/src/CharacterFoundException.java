/**
 * The <code>CharacterFoundException</code> is a custom exception 
 * that is thrown if the stack finds a character representing a letter.
 * 
 *  @author Erik Bracamonte 
 *    e-mail: erik.bracamonte@stonybrook.edu
 *    Stony Brook ID: 111230826
 **/
public class CharacterFoundException extends Exception {
	
	/**
	 * The <code>CharacterFoundException</code> is a custom exception 
	 * that is thrown if the stack finds a character representing a letter.
	 * 
	 * @param message
	 * 	message indicating the error.
	 */
    public CharacterFoundException(String message) {
        super(message);
    }
}