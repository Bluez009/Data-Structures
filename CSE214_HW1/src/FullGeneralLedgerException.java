/**
 * The <code>FullGeneralLedgerException</code> class is an exception that is
 * thrown if there is no more room in the <class>GeneralLedger</class> to 
 * record additional transactions.
 * 
 * @author Erik Bracamonte 
 *    e-mail: erik.bracamonte@stonybrook.edu
 *    Stony Brook ID: 111230826
 **/
public class FullGeneralLedgerException extends Exception {
	/**
	 * The <code>FullGeneralLedgerException</code> class is an exception that is
	 * thrown if there is no more room in the <class>GeneralLedger</class> to 
	 * record additional transactions.
	 * @param message
	 * 	message indicating the error.
	 */
    public FullGeneralLedgerException (String message) {
        super(message);
    }
}
