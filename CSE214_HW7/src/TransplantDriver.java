import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The Class TransplantDriver will act as the main driver for the application.
 * This class contains the main method for the program, which will first attempt
 * to load any previously serialized TransplantGraph object located in
 * 'transplant.obj'. If such an object is not found, you should instead
 * construct a new TransplantGraph object using the information stored in the
 * files denoted by DONOR_FILE and RECIPIENT_FILE by calling the static method
 * buildFromFiles in the TransplantGraph class. The program should serialize its
 * TransplantGraph object on program termination, so that it may be loaded at a
 * later time.
 * 
 * @author Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *         ID: 111230826
 */
public class TransplantDriver implements Serializable {

	/** The Constant DONOR_FILE. */
	public static final String DONOR_FILE = "donors.txt";

	/** The Constant RECIPIENT_FILE. */
	public static final String RECIPIENT_FILE = "recipients.txt";

	/** The Constant SERIAL_NAME. */
	public static final String SERIAL_NAME = "transplant1.obj";

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * The main method that act as a menu-driven application, prompting the user
	 * for input.
	 *
	 * @param args the arguments
	 */
	public static void main(String args[]) {
		Scanner input = new Scanner(System.in);

		TransplantGraph transplantgraph = null;
		try {
			FileInputStream file = new FileInputStream(SERIAL_NAME);
			ObjectInputStream fin = new ObjectInputStream(file);
			transplantgraph = (TransplantGraph) fin.readObject();
			fin.close();
			System.out.println("Loading data from " + SERIAL_NAME + "...\n");
		} catch (IOException e) {
			System.out.println(SERIAL_NAME
					+ " not found. Creating new TransplantGraph object...");
			System.out.println("Loading data from '" + DONOR_FILE + "'...");
			System.out.println("Loading data from '" + RECIPIENT_FILE + "'...");

			transplantgraph = TransplantGraph.buildFromFiles(DONOR_FILE,
					RECIPIENT_FILE);

		} catch (ClassNotFoundException e) {
			System.out.println(
					"Something broke here... Class not found...\nCreated "
					+ "TransplantGraph...");

			transplantgraph = new TransplantGraph();
		}

		boolean test = true;
		while (test) {

			System.out.println("\nMenu:\n" + "    (LR) - List all recipients\n"
					+ "    (LO) - List all donors\n"
					+ "    (AO) - Add new donor\n"
					+ "    (AR) - Add new recipient\n"
					+ "    (RO) - Remove donor\n"
					+ "    (RR) - Remove recipient\n"
					+ "    (SR) - Sort recipients\n"
					+ "    (SO) - Sort donors\n" + "    (Q) - Quit\n");
			System.out.print("Please enter a selection: ");
			String command = input.next().toUpperCase();
			switch (command) {
			case ("LR"):
				transplantgraph.printAllRecipients();
				break;
			case ("LO"):
				transplantgraph.printAllDonors();
				break;
			case ("AO"):
				try {
					input.nextLine();
					System.out.print("Please enter the organ donor name: ");
					String organDonor = input.nextLine();
					System.out.print("Please enter the organs " + organDonor
							+ " is donating: ");
					String organ = input.next().toUpperCase();
					System.out.print("Please enter the blood type of "
							+ organDonor + ": ");
					String donorBloodType = input.next().toUpperCase();
					if (BloodType.isValidBloodType(donorBloodType)) {
						BloodType bloodtype = new BloodType(
								donorBloodType.trim());
						System.out.print(
								"Please enter the age of " + organDonor + ": ");
						int donorAge = input.nextInt();
						int numID = TransplantGraph.getDonorPatientsAdded();
						Patient donorPatient = new Patient(organDonor, organ,
								donorAge, bloodtype, numID, true);
						transplantgraph.addDonor(donorPatient);

						System.out.println("\nThe organ donor with ID " + numID
								+ " was successfully added to the donor list!");
					} else {
						System.out.println(
								"Invalid bloodtype. Donor was not created.");
					}
				} catch (InputMismatchException e) {
					input.nextLine();
					System.out
							.println("Input mismatch. Donor was not created.");
				}

				break;
			case ("AR"):
				try {
					input.nextLine();
					System.out.print("Please enter new recipient's name: ");
					String recipientName = input.nextLine();
					System.out
							.print("Please enter the recipient's blood type: ");
					String recpientsBloodType = input.next().toUpperCase();
					if (BloodType.isValidBloodType(recpientsBloodType)) {
						BloodType bloodtype = new BloodType(
								recpientsBloodType.trim());
						System.out.print("Please enter the recipient's age: ");
						int recipientsAge = input.nextInt();
						System.out.print("Please enter the organ needed: ");
						String organNeeded = input.next();
						Patient recipientPatient = new Patient(
								recipientName.trim(), organNeeded.trim(),
								recipientsAge, bloodtype,
								TransplantGraph.getRecipientPatientsAdded(),
								false);
						transplantgraph.addRecipient(recipientPatient);

						System.out.println("\n" + recipientName
								+ " is now on the " + organNeeded
								+ " transplant waitlist!");
					} else {
						System.out.println(
								"Invalid bloodtype. Donor was not created.");
					}

				} catch (InputMismatchException e) {
					input.nextLine();
					System.out.println(
							"Input mismatch. Recipient was not created and was"
							+ " not added to the waitlist.");
				}

				break;
			case ("RO"):
				input.nextLine();
				System.out.print(
						"\nPlease enter the name of the organ donor to remove: ");
				String donorName = input.nextLine();
				try {
					transplantgraph.removeDonor(donorName);
				} catch (NameNotFoundException e) {
					System.out.println(e.getMessage());
				}

				break;
			case ("RR"):
				input.nextLine();
				System.out.print(
						"\nPlease enter the name of the recipient to remove: ");
				String recipientName = input.nextLine();
				try {
					transplantgraph.removeRecipient(recipientName);
				} catch (NameNotFoundException e) {
					System.out.println(e.getMessage());
				}
				break;
			case ("SR"):
				boolean leaveSR = true;

				while (leaveSR) {
					System.out.println("\n(I) Sort by ID\n"
							+ "(N) Sort by Number of Donors\n"
							+ "(B) Sort by Blood Type\n"
							+ "(O) Sort by Organ Needed\n"
							+ "(Q) Back to Main Menu\n");
					System.out.print("Please select an option: ");
					String commandSR = input.next().toUpperCase();
					switch (commandSR) {
					case ("I"):
						// ID SORT
						Collections.sort(transplantgraph.getRecipientsList());
						transplantgraph.printAllRecipients();
						break;
					case ("N"):
						// NUM OF DONORS SORT
						Collections.sort(transplantgraph.getRecipientsList(),
								new NumConnectionsComparator());
						transplantgraph.printAllRecipients();
						break;
					case ("B"):
						// BLOODTYPE SORT
						Collections.sort(transplantgraph.getRecipientsList(),
								new BloodTypeComparator());
						transplantgraph.printAllRecipients();
						break;
					case ("O"):
						// ORGAN TYPE SORT
						Collections.sort(transplantgraph.getRecipientsList(),
								new OrganComparator());
						transplantgraph.printAllRecipients();
						break;
					case ("Q"):
						Collections.sort(transplantgraph.getRecipientsList());
						leaveSR = false;
						break;
					default:
						System.out.print(
								"The command does not exist. Try again. \n");
					}
				}

				break;

			case ("SO"):
				// Same as SR
				boolean leaveSO = true;

				while (leaveSO) {
					System.out.println("(I) Sort by ID\n"
							+ "(N) Sort by Number of Recipients\n"
							+ "(B) Sort by Blood Type\n"
							+ "(O) Sort by Organ Donated\n"
							+ "(Q) Back to Main Menu\n");
					System.out.print("Please select an option: ");
					String commandSO = input.next().toUpperCase();
					switch (commandSO) {
					case ("I"):
						// ID SORT
						Collections.sort(transplantgraph.getDonorsList());
						transplantgraph.printAllDonors();
						break;
					case ("N"):
						// NUM OF DONORS SORT
						Collections.sort(transplantgraph.getDonorsList(),
								new NumConnectionsComparator());
						transplantgraph.printAllDonors();
						break;
					case ("B"):
						// BLOODTYPE SORT
						Collections.sort(transplantgraph.getDonorsList(),
								new BloodTypeComparator());
						transplantgraph.printAllDonors();
						break;
					case ("O"):
						// ORGAN TYPE SORT
						Collections.sort(transplantgraph.getDonorsList(),
								new OrganComparator());
						transplantgraph.printAllDonors();
						break;
					case ("Q"):
						Collections.sort(transplantgraph.getDonorsList());
						leaveSO = false;
						break;
					default:
						System.out.print(
								"The command does not exist. Try again. \n");
					}

				}
				break;

			case ("Q"):

				try {
					FileOutputStream file = new FileOutputStream(SERIAL_NAME);
					ObjectOutputStream fout = new ObjectOutputStream(file);

					fout.writeObject(transplantgraph);
					fout.close();
					file.close();
					System.out.println(
							"\nWriting data to " + SERIAL_NAME + ".\n");
				} catch (IOException ex) {
					System.out.println(
							"\nSomething Broke. Object was not serialized.\n");
				}

				System.out.println("Program Terminating normally...");
				input.close();
				return;
			default:
				System.out.print("The command does not exist. Try again. \n");

			}
		}

	}
}
