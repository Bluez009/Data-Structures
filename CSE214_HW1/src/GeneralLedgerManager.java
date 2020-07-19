import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The <code>GeneralLedgerManager</code> class will act as a driver for the
 * ledger.
 *
 * @author 
 * 	Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu 
 * 	Stony Brook ID: 111230826
 **/

public class GeneralLedgerManager {
	/**
	 * The main method runs a menu driven application which first creates an
	 * empty GeneralLedger object. The program prompts the user for a command to
	 * execute an operation. Once a command has been chosen, the program may ask
	 * the user for additional information if necessary and performs the
	 * operation.
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		GeneralLedger generalLedger = new GeneralLedger();
		GeneralLedger backupGeneralLedger = new GeneralLedger();
		boolean isBackupCreated = false;

		while (true) {
			System.out.println(
					"\n(A) - Add Transaction<date><amount><description> \n"
					+ "(G) - Get Transaction <position> \n"
					+ "(R) - Remove Transaction <position> \n"
					+ "(P) - Prints Transaction in General Ledger \n"
					+ "(F) - Filter by Transaction Date <date> \n"
					+ "(L) - Look for Transaction<date><amount><description>\n"
					+ "(S) - Size \n" + "(B) - Backup \n"
					+ "(PB) - Print Transactions in Backup \n"
					+ "(RB) - Revert to Backup \n"
					+ "(CB) - Compare Backup with Current \n"
					+ "(PF) - Print Financial Information \n"
					+ "(Q) - Quit \n");
			System.out.print("Enter a selection: ");
			String command = input.next().toUpperCase();
			System.out.println("");
			switch (command) {
			case ("A"):
				System.out.print("ENTER DATE: ");
				try {
					String date = input.next();
					date = date.trim();
					System.out.print("Enter Amount ($): ");
					double amount = input.nextDouble();
					System.out.print("Enter Description: ");
					input.nextLine();
					String desc = input.nextLine();
					Transaction newTransaction = new Transaction(date, amount,
							desc);
					generalLedger.addTransaction(newTransaction);
					System.out.println("Transaction successfully added to the "
							+ "general ledger.");
				} catch (InvalidTransactionException ex) {
					System.out.println(ex.getMessage());
				} catch (TransactionAlreadyExistsException ex) {
					System.out.println(ex.getMessage());
				} catch (FullGeneralLedgerException ex) {
					System.out.println(ex.getMessage());
				} catch (InputMismatchException ex) {
					System.out.println("Input mismatch.");
				}

				break;

			case ("G"):
				System.out.print("Enter Position: ");
				try {
					int inputPos = input.nextInt();
					Transaction transactionRetrieved = generalLedger
							.getTransaction(inputPos);
					double val = transactionRetrieved.getAmount();
					System.out.println("");
					System.out.println(
							String.format("%-7s%-11s%11s%11s    %-25s", "No.",
									"Date", "Debit", "Credit", "Description"));
					System.out.println(
							"--------------------------------------------------"
							+ "----------------------------------");
					if (val < 0.0) {
						val = Math.abs(val);
						System.out.println(String.format(
								"%-7d%-11s%11.2s%11.2f    %-25s", inputPos,
								transactionRetrieved.getDate(), "", val,
								transactionRetrieved.getDescription()) + "\n");
					} else {
						System.out.println(String.format(
								"%-7d%-11s%11.2f%11.2s    %-25s", inputPos,
								transactionRetrieved.getDate(), val, "",
								transactionRetrieved.getDescription()) + "\n");
					}

				} catch (InvalidLedgerPositionException e) {
					System.out.println(e.getMessage());
				} catch (InputMismatchException e) {
					System.out.println("Input mismatch.");
				}
				break;

			case ("R"):
				System.out.print("Enter Position: ");
				try {
					int inputPos = input.nextInt();
					generalLedger.removeTransaction(inputPos);
					System.out.println("Transaction has been successfully"
							+ " removed from the general ledger.");
				} catch (InvalidLedgerPositionException e) {
					System.out.println(e.getMessage());
				} catch (InputMismatchException e) {
					System.out.println("Input mismatch.");
				}
				break;

			case ("P"):
				if (generalLedger.size() == 0) {
					System.out.println(
							"No transactions currently in the general ledger.");
				} else {
					generalLedger.printAllTransactions();
				}
				break;

			case ("F"):
				System.out.print("Enter Date: ");
				try {
					String inputStr = input.next();
					GeneralLedger.filter(generalLedger, inputStr);
				} catch (InputMismatchException e) {
					System.out.println("Input mismatch.");
				}

				break;

			case ("L"):
				try {
					System.out.print("Enter Date: ");
					String date2 = input.next();
					date2 = date2.trim();
					System.out.print("Enter Amount ($): ");
					double amount2 = input.nextDouble();
					System.out.print("Enter Description: ");
					input.nextLine();
					String desc2 = input.nextLine();
					Transaction refTransaction = new Transaction(date2, amount2,
							desc2);
					generalLedger.exists(refTransaction);

				} catch (InputMismatchException ex) {
					System.out.println("Input mismatch.");
				}
				break;

			case ("S"):
				System.out.println("There are " + generalLedger.size()
						+ " transactions currently in the general ledger.");
				break;
			case ("B"):
				backupGeneralLedger = generalLedger.clone();
				isBackupCreated = true;
				System.out.println("Created a backup of the current ledger.");
				break;

			case ("PB"):
				if (isBackupCreated == false) {
					System.out.println("There is no backup created for this "
							+ "general ledger.");
				} else {
					backupGeneralLedger.printAllTransactions();
				}
				break;

			case ("RB"):
				if (isBackupCreated == false) {
					System.out.println("There is no backup created for this "
							+ "general ledger to revert back.");
				} else {
					generalLedger = backupGeneralLedger.clone();
					System.out.println("General ledger successfully reverted to "
							+ "the backup copy.");
				}
				break;

			case ("CB"):
				if (isBackupCreated == false) {
					System.out.println(
							"There is currently no backup to compare.");
				} else {
					if (generalLedger.equals(backupGeneralLedger)) {
						System.out.println("The current general ledger is the "
								+ "same as the backup copy.");
					} else {
						System.out.println("The current general ledger is NOT "
								+ "the same as the backup copy.");
					}

				}
				break;

			case ("PF"):
				System.out.println("Financial Data for Erik's Account");
				System.out.println(
						"-----------------------------------------------------"
						+ "-------------------------------");
				double creditVal = Math
						.abs(generalLedger.getTotalCreditAmount());
				double debitVal = generalLedger.getTotalDebitAmount();
				double net = debitVal - creditVal;
				System.out.println(
						String.format("%12s $%-11.2f", "Assets:", debitVal));
				System.out.println(String.format("%12s $%-11.2f",
						"Liabilities:", creditVal));
				System.out.println(
						String.format("%12s $%-11.2f", "Net Worth:", net));
				break;
			case ("Q"):
				System.out.println("Program terminating successfully...");
				return;
			default:
				System.out.print("The command does not exist. Try again. \n\n");
			}
		}

	}
}
