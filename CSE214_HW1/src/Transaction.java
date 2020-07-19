/**
 * The <code>Transaction</code> class contains the date (String), amount
 * (double) , and description (String) of a transaction.
 * 
 *
 * @author 
 * 	Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu
 *	Stony Brook ID: 111230826
 **/

public class Transaction {
	private String date;
	private Double amount;
	private String description;

	/**
	 * Constructs an instance of the transaction with date, amount, and
	 * description.
	 * 
	 * <dt>Postcondition:
	 *  <dd>This Transaction has been initialized with date, amount, and
	 * 	description.
	 */
	Transaction() {
		this.date = "";
		this.amount = 0.0;
		this.description = "";
	}

	/**
	 * Constructs an instance of the transaction with date, amount, and
	 * description.
	 * 
	 * @param date        
	 * 	date of transaction made
	 * @param amount      
	 * 	amount of the transaction
	 * @param description 
	 * 	purpose of transaction
	 * 
	 * <dt>Postcondition:
	 *	<dd>This Transaction has been initialized with date,
	 *	amount, and description.
	 */
	Transaction(String date, Double amount, String description) {
		this.date = date;
		this.amount = amount;
		this.description = description;
	}

	/**
	 * Retrieve date string from Transaction class.
	 * 
	 * @return 
	 * 	date string of a Transaction object.
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Retrieve amount from Transaction class.
	 * 
	 * @return 
	 * 	amount of a Transaction object.
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * Retrieve description from Transaction class.
	 * 
	 * @return description string of a Transaction object.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Creates a copy of this Transaction. Subsequent changes to the copy will
	 * not affect the original and vice versa.
	 * 
	 * <dt>Preconditions:
	 * <dd>This Transaction object has been instantiated.
	 * 
	 * @return 
	 * 	A copy (backup) of this Transaction object.

	 */
	public Object clone() {
		Transaction clonedTransaction = new Transaction(this.date, this.amount,
				this.description);
		return clonedTransaction;
	}

	/**
	 * Compare transaction parameters and states whether they are the same or
	 * not.
	 * 
	 * @param transaction The transaction that is being compared with.
	 * 
	 * <dt>Preconditions
	 * 	<dd>Both Transactions has both been instantiated.
	 * 
	 * @return 
	 * 	True if the two Transactions are the same. Otherwise, false.
	 */
	public boolean equals(Transaction transaction) {
		if (transaction.getDate().equals(this.date)
				&& transaction.getAmount().compareTo(this.amount) == 0
				&& transaction.getDescription().equals(this.description)) {
			return true;
		} else {
			return false;
		}
	}
}
