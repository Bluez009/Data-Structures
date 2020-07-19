/**
 * The <code>GeneralLedger</code> tores an ordered list of transaction objects
 * within an array. The array indices determine the order the transactions are
 * to be posted. An accountant can insert a transaction at any position within
 * the range of the list. The GeneralLedger can record a total of 50
 * transactions. "totalDebitAmount" and "totalCreditAmount" are used to keep
 * track of the running total of all debit and credit amounts for all
 * transactions. These variables should be modified every time a transaction is
 * added or deleted from the general ledger.
 *
 * @author 
 * 	Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *  ID: 111230826
 **/

public class GeneralLedger implements Cloneable {

	public static final int MAX_TRANSACTIONS = 50;
	private Transaction[] ledger;
	private double totalDebitAmount; 
	private double totalCreditAmount;
	private int ledgerSize;

	/**
	 * Constructs an instance of the GeneralLedger with no Transaction objects
	 * in it.
	 * 
	 * <dt>Postconditions: 
	 * 	<dd>This GeneralLedger has been initialized to an empty list of 
	 * 	Transactions.
	 */
	public GeneralLedger() {
		this.ledger = new Transaction[MAX_TRANSACTIONS];
		this.totalDebitAmount = 0.0;
		this.totalCreditAmount = 0.0;
		this.ledgerSize = 0;
	}

	/**
	 * Constructs an instance of the GeneralLedger with no Transaction objects
	 * in it.
	 * 
	 * @param debit  
	 * 	debit amount
	 * @param credit 
	 * 	credit amount
	 * @param size   
	 * 	size of the transaction
	 * 
	 * <dt>Postconditions: 
	 * 	<dd>This GeneralLedger has been initialized to an empty list of 
	 * 	Transactions.
	 */
	public GeneralLedger(double debit, double credit, int size) {
		this.ledger = new Transaction[MAX_TRANSACTIONS];
		this.totalDebitAmount = debit;
		this.totalCreditAmount = credit;
		this.ledgerSize = size;
	}

	/**
	 * Retrieve totalDebitAmount value.
	 * 
	 * @return debit amount
	 */
	public double getTotalDebitAmount() {
		return totalDebitAmount;
	}

	/**
	 * Retrieve amount of credit.
	 * 
	 * @return 
	 * 	credit amount
	 */
	public double getTotalCreditAmount() {
		return totalCreditAmount;
	}

	/**
	 * Adds the debit amount to the total debit of the
	 * <class>GeneralLedger</class>.
	 * 
	 * @param debitAmount 
	 * 	the amount to add to the total debit.
	 */
	public void updateDebitAmount(double debitAmount) {
		this.totalDebitAmount += debitAmount;
	}

	/**
	 * Adds the credit amount to the total credit of the
	 * <class>GeneralLedger</class>.
	 * 
	 * @param creditAmount 
	 * 	the amount to add to the total credit.
	 */
	public void updateCreditAmount(double creditAmount) {
		this.totalCreditAmount += creditAmount;
	}

	/**
	 * Adds newTransaction into this <code>GeneralLedger<code> if it does not
	 * already exist. Added entries should be inserted in date order. If there
	 * are multiple entries with the same date, new entries should be placed at
	 * the end of that date's boundary.
	 * 
	 * @param newTransaction 
	 * 	the new <code>Transaction<code> to add to the ledger
	 * 
	 * <dt>Preconditions:
	 * 	<dd>The Transaction object has been instantiated and the number of 
	 * 	Transaction objects in this <code>GeneralLedger<code> is less than
	 *  MAX_TRANSACTIONS.
	 * 
	 * <dt>Postconditions:
	 *  <dd>The new transaction is now listed in the
	 *  correct order in the list. All Transactions whose
	 *  date is newer than or equal to newTransaction> are
	 * 	moved back one position (e.g. If there are 5
	 *  transactions in a generalLedger, positioned 1-5,
	 *  and the transaction you insert has a date that's
	 *  newer than the transaction in position 3 but older
	 *  than the transaction in position 4, the new
	 *  transaction would be placed in position 4, the
	 *  transaction that was in position 4 will be moved to
	 *  position 5, and the transaction that was in
	 *  position 5 will be moved to position 6.) (See
	 *  sample output for more information).
	 * 
	 * @exception FullGeneralLedgerException        
	 * 	Thrown if there is no more room in this GeneralLedger to record 
	 * 	additional transactions.
	 * 
	 * @exception InvalidTransactionException      
	 *  Thrown if the transaction amount is 0 or if the date is invalid.
	 *  
	 * @exception TransactionAlreadyExistsException 
	 * 	Thrown if there is already a transaction in this GeneralLedger which is
	 * 	equivalent to newTransaction (both have the same date, amount, and 
	 * 	description).
	 */
	public void addTransaction(Transaction newTransaction)
			throws FullGeneralLedgerException, InvalidTransactionException,
			TransactionAlreadyExistsException {
		if (isDateFormatCorrect(newTransaction) == false) {
			throw new InvalidTransactionException(
					"Format of date is wrong. Correct format is YYYY/MM/DD");
		}

		else if (ledgerSize == MAX_TRANSACTIONS) {
			throw new FullGeneralLedgerException(
					"There is no more room to record additional transactions.");
		}

		else {
			String transactionDate = newTransaction.getDate();

			String[] dateSplit = transactionDate.split("/", 3);
			int[] numArray = new int[3];
			for (int i = 0; i < dateSplit.length; i++) {
				numArray[i] = Integer.parseInt(dateSplit[i]);
			}
			if (numArray[0] >= 1900 && numArray[0] <= 2050) {
				if (numArray[1] >= 1 && numArray[1] <= 12) {
					if (numArray[2] >= 1 && numArray[2] <= 30) {
						if (ledgerSize == 0) {
							double val3 = newTransaction.getAmount();
							if (val3 < 0.0) {
								updateCreditAmount(val3);
							} else {
								updateDebitAmount(val3);
							}
							ledger[0] = newTransaction;
							ledgerSize++;
						}

						else {
							transactionDate = transactionDate.replace("/", "");
							int transactionDateInt = Integer
									.parseInt(transactionDate);
							int ledgerLength = size();
							for (int i = 0; i <= ledgerLength; i++) {
								// System.out.println(size());
								if (ledger[i] == null) {
									double val2 = newTransaction.getAmount();
									if (val2 < 0.0) {
										updateCreditAmount(val2);
									} else {
										updateDebitAmount(val2);
									}
									ledger[i] = newTransaction;
									ledgerSize++;
								} else if (ledger[i].equals(newTransaction)) {
									throw new TransactionAlreadyExistsException(
											"There is already a transaction in "
											+ "this GeneralLedger which is "
											+ "equivalent to this new "
											+ "Transaction");
								} else {
									String ledgerTransactionDate = ledger[i]
											.getDate();
									ledgerTransactionDate = ledgerTransactionDate
											.replace("/", "");
									int ledgerTransactionDateInt = Integer
											.parseInt(ledgerTransactionDate);
									if (transactionDateInt <= ledgerTransactionDateInt) {
										for (int j = ledgerLength
												- 1; j >= i; j--) {
											ledger[j + 1] = ledger[j];
										}
										double val = newTransaction.getAmount();
										if (val < 0.0) {
											updateCreditAmount(val);
										} else {
											updateDebitAmount(val);
										}

										ledger[i] = newTransaction;
										ledgerSize++;
										break;
									}
								}
							}
						}
					} else {
						throw new InvalidTransactionException(
								"WRONG DAY RANGE (Must be between 01-30)");
					}
				} else {
					throw new InvalidTransactionException(
							"WRONG MONTH RANGE (Must be between 01-12)");
				}
			} else {
				throw new InvalidTransactionException(
						"WRONG YEAR (Must be between 1900-2050)");
			}
		}
	}

	/**
	 * Determines if the format for date is correct in <code>Transaction</code>
	 * object. Checks for YYYY/MM/DD format and converts string to integer to
	 * verify.
	 * 
	 * @param newTransaction 
	 * 	the new <code>Transaction<code> to add to the ledger
	 * 
	 * @return 
	 * 	true if format is correct, otherwise false.
	 */
	public boolean isDateFormatCorrect(Transaction newTransaction) {
		String transactionDate = newTransaction.getDate().trim();
		if (transactionDate.length() != 10) {
			return false;
		}

		if (transactionDate.charAt(4) != '/'
				|| transactionDate.charAt(7) != '/') {
			return false;
		}

		transactionDate = transactionDate.replace("/", "");

		try {
			int num = Integer.parseInt(transactionDate);
		} catch (NumberFormatException ex) {
			return false;
		}

		return true;

	}

	/**
	 * Removes the transaction located at position from this GeneralLedger.
	 * 
	 * @param position 
	 * 	The 1-based index of the Transaction to remove.
	 * 
	 * <dt>Preconditions:
	 *  <dd>This <code>GeneralLedger<code> has been instantiated
	 *  and 1 <= position <= items_currently_in_list.
	 *
	 * <dt>Postconditions:
	 * 	<dd>The Transaction at the desired position has been
	 * 	removed. All transactions that were originally greater
	 * 	than or equal to position are moved backward one position
	 * 	(e.g. If there are 5 Transactions in the generalLedger,
	 *  positioned 1-5, and you remove the Transaction in
	 *  position 4, the item that was in position 5 will be moved
	 *  to position 4).
	 *
	 * @throws InvalidLedgerPositionException 
	 * 	Thrown if position is not valid.
	 * 
	 * @Note position 
	 * 	refers to the position in the general ledger, not the position in the 
	 * 	array
	 */
	public void removeTransaction(int position)
			throws InvalidLedgerPositionException {
		
		if (position > size() || position > MAX_TRANSACTIONS || position <= 0) {
			throw new InvalidLedgerPositionException("Transaction not removed: "
					+ "No such transaction in the general ledger.");
		} else {
			int ledgerLength = size();
			double val = ledger[position - 1].getAmount();
			if (val > 0.0) {
				updateDebitAmount(-val);
			} else {
				updateCreditAmount(-val);
			}

			for (int i = position - 1; i <= ledgerLength; i++) {
				try {
					if (size() == 1) {
						ledger[position - 1] = null;
						ledgerSize--;
						break;
					}

					if (position == MAX_TRANSACTIONS) {
						ledger[position - 1] = null;
						ledgerSize--;
						break;
					}
					if (ledger[i + 1] == null) {
						ledgerSize--;
						break;
					} else {
						ledger[i] = ledger[i + 1];
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					ledgerSize--;
					break;
				}
			}
		}

	}

	/**
	 * Returns a reference to the Transaction object located at position.
	 * 
	 * @param position 
	 * 	The position in this <code>GeneralLedger<code> to retrieve.
	 * 
	 * <dt>Preconditions:
	 *  <dd>The <code>GeneralLedger<code> has been instantiated
	 *  and 1 <= position <= items_currently_in_list.
	 * 
	 * @return 
	 * 	The Transaction at the specified position in this GeneralLedger object.
	 * 
	 * @throws InvalidLedgerPositionException 
	 * 	Indicates that position is not within the valid range.
	 */
	public Transaction getTransaction(int position)
			throws InvalidLedgerPositionException {
		if (position > size() || position > MAX_TRANSACTIONS || position <= 0) {
			throw new InvalidLedgerPositionException("No such transaction.");
		} else {
			return ledger[position - 1];
		}

	}

	/**
	 * Prints all transactions that were posted on the specified date.
	 * 
	 * @param generalLedger 
	 * 	The ledger of transactions to search in.
	 * @param date          
	 * 	The date of the transaction(s) to search for.
	 * 
	 * <dt>Preconditions:
	 * 	<dd>This <code>GeneralLedger<code> object has been instantiated.
	 * 
	 * <dt>Postconditions:
	 *  <dd>Displays a neatly formatted table of all Transactions that have
	 *  taken place on the specified date.
	 */
	public static void filter(GeneralLedger generalLedger, String date) {
		String filteredList = "";
		for (int i = 0; i < generalLedger.size(); i++) {
			if (generalLedger.ledger[i].getDate().equals(date)) {
				String ledgerDate = generalLedger.ledger[i].getDate();
				double amount = generalLedger.ledger[i].getAmount();
				String desc = generalLedger.ledger[i].getDescription();
				if (amount > 0.0) {
					filteredList += String.format(
							"%-7d%-11s%11.2f%11.2s    %-25s", i + 1, ledgerDate,
							amount, "", desc) + "\n";
				} else {
					amount = Math.abs(amount);
					filteredList += String.format(
							"%-7d%-11s%11.2s%11.2f    %-25s", i + 1, ledgerDate,
							"", amount, desc) + "\n";
				}
			}
		}
		System.out.println(String.format("%-7s%-11s%11s%11s    %-25s", "No.",
				"Date", "Debit", "Credit", "Description"));
		System.out.println(
				"-------------------------------------------------------------"
				+ "-----------------------");
		System.out.println(filteredList);
	}

	/**
	 * Creates a copy of this GeneralLedger. Subsequent changes to the copy will
	 * not affect the original and vice versa.
	 * 
	 * <dt>Preconditions:
	 * 	<dd>This <code>GeneralLedger<code> object has been instantiated.
	 * 
	 * @return 
	 * 	A copy (backup) of this <code>GeneralLedger<code> object.
	 */
	public GeneralLedger clone() {
		GeneralLedger clonedGeneralLedger = new GeneralLedger(
				this.totalDebitAmount, this.totalCreditAmount, this.ledgerSize);
		for (int i = 0; i < clonedGeneralLedger.size(); i++) {
			clonedGeneralLedger.ledger[i] = (Transaction) ledger[i].clone();
		}
		return clonedGeneralLedger;
	}

	/**
	 * Checks whether a certain transaction is contained in the ledger.
	 * 
	 * @param transaction The Transaction to check for.
	 * 
	 * <dt>Preconditions:
	 *  <dd>This GeneralLedger and transaction have been instantiated.
	 * 
	 * @return 
	 * 	true if this GeneralLedger contains transaction, false otherwise.
	 * 
	 * @throws IllegalArgumentException 
	 * 	Thrown if transaction is not a valid Transaction object.
	 */
	public boolean exists(Transaction transaction) {
		if (transaction instanceof Transaction) {
			for (int i = 0; i < this.ledgerSize; i++) {
				if (this.ledger[i].equals(transaction)) {
					System.out.println(
							String.format("%-7s%-11s%11s%11s    %-25s", "No.",
									"Date", "Debit", "Credit", "Description"));
					System.out.println(
							"-------------------------------------------------"
							+ "-----------------------------------");
					double val = transaction.getAmount();
					if (val < 0.0) {
						val = Math.abs(val);
						System.out.println(
								String.format("%-7d%-11s%11.2s%11.2f    %-25s",
										i + 1, transaction.getDate(), "", val,
										transaction.getDescription()) + "\n");
					} else {
						System.out.println(
								String.format("%-7d%-11s%11.2f%11.2s    %-25s",
										i + 1, transaction.getDate(), val, "",
										transaction.getDescription()) + "\n");
					}

					return true;
				}
			}
			return false;
		} else {
			throw new IllegalArgumentException(
					"This is not a valid Transaction Object");
		}
	}

	/**
	 * Returns the number of Transactions currently in this ledger.
	 * 
	 * <dt>Preconditions: This GeneralLedger has been instantiated.
	 * 
	 * @return 
	 * 	The number of Transactions in this ledger.
	 */
	public int size() {
		return ledgerSize;
	}

	/**
	 * Prints a neatly formatted table of each item in the list with its
	 * position number (as shown in the sample output).
	 * 
	 * <dt>Preconditions:
	 * <dd>This GeneralLedger has been instantiated.
	 * 
	 * <dt>Postconditions:
	 * <dd>All transactions contained within this GeneralLedger are printed in a
	 * neatly formatted table. See sample i/o for format.
	 * 
	 */
	public void printAllTransactions() {
		System.out.println(this.toString());
	}

	/**
	 * Returns a String representation of this GeneralLedger object, which is a
	 * neatly formatted table of each Transaction contained in this ledger on
	 * its own line with its position number (as shown in the sample output).
	 * 
	 * @return 
	 * 	A String representation of this <code>GeneralLedger<code> object.
	 */
	public String toString() {

		String list = String.format("%-7s%-11s%11s%11s    %-25s", "No.", "Date",
				"Debit", "Credit", "Description") + "\n";
		list += "--------------------------------------------------------------"
				+ "----------------------\n";
		for (int i = 0; i < this.size(); i++) {
			String ledgerDate = this.ledger[i].getDate();
			double amount = this.ledger[i].getAmount();
			String desc = this.ledger[i].getDescription();
			if (amount > 0.0) {
				list += String.format("%-7d%-11s%11.2f%11.2s    %-25s", i + 1,
						ledgerDate, amount, "", desc) + "\n";
			} else {
				amount = Math.abs(amount);
				list += String.format("%-7d%-11s%11.2s%11.2f    %-25s", i + 1,
						ledgerDate, "", amount, desc) + "\n";
			}
		}
		return list;
	}

	/**
	 * Compares both <code>GeneralLedger<code> objects and determine if they
	 * carry the same exact transactions as each other.
	 * 
	 * @param generalLedger 
	 * 	The <class>GeneralLedger</class> object to compare with.
	 * 
	 * @return 
	 * 	false if they are not the same, otherwise true.
	 */
	public boolean equals(GeneralLedger generalLedger) {
		if (this.ledgerSize != generalLedger.ledgerSize) {
			return false;
		} else {
			for (int i = 0; i < size(); i++) {
				if (!ledger[i].equals(generalLedger.ledger[i])) {
					return false;
				}
			}
		}
		return true;

	}
}
