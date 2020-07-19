import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Class TransplantGraph contains an adjacency matrix for the organ donors
 * and recipients.
 * 
 * @author Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *         ID: 111230826
 */
public class TransplantGraph implements Serializable {

	/** The donor patients added. */
	private static int donorPatientsAdded = 0;
	
	/** The recipient patients added. */
	private static int recipientPatientsAdded = 0;
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Contains all organ donors in our system. */
	private ArrayList<Patient> donors;

	/** Contains all recipients in our system. */
	private ArrayList<Patient> recipients;

	/** The Constant MAX_PATIENTS. */
	public static final int MAX_PATIENTS = 100;

	/**
	 * Adjacency matrix used to track compatibility. You should set up your
	 * matrix such that the rows are used to store information about the donors,
	 * and the columns are used to store information about the recipients (ie,
	 * if you read row 0 left to right, each cell will denote whether donor 0 is
	 * compatible with a recipient. Likewise, if you read column 0 from top to
	 * bottom, each cell will denote whether recipient 0 is compatible with a
	 * donor). Doing so, connections[i][j] represents whether or not a link
	 * between donor i and recipient j exists.
	 */
	private boolean[][] connections;

	/**
	 * Instantiates a new transplant graph.
	 */
	public TransplantGraph() {
		donors = new ArrayList<Patient>();
		recipients = new ArrayList<Patient>();
		connections = new boolean[MAX_PATIENTS][MAX_PATIENTS]; //EDIT MAYBE AT SOME POINT
	}
	
	/**
	 * Gets number of the donor patients added.
	 *
	 * @return the donor patients added
	 */
	public static int getDonorPatientsAdded() {
		return donorPatientsAdded;
	}
	
	/**
	 * Gets number of the recipient patients added.
	 *
	 * @return the recipient patients added
	 */
	public static int getRecipientPatientsAdded() {
		return recipientPatientsAdded;
	}
	
	/**
	 * Gets the recipients list.
	 *
	 * @return the recipients list
	 */
	public ArrayList<Patient> getRecipientsList() {
		return recipients;
	}
	
	/**
	 * Gets the donors list.
	 *
	 * @return the donors list
	 */
	public ArrayList<Patient> getDonorsList() {
		return donors;
	}

	/**
	 * Sets the recipients list.
	 *
	 * @param list the new recipients list
	 */
	public void setRecipientsList(ArrayList<Patient> list) {
		recipients = list;
		updateConnections();
	}
	
	/**
	 * Sets the donors list.
	 *
	 * @param list the new donors list
	 */
	public void setDonorsList(ArrayList<Patient> list) {
		donors = list;
		updateConnections();
	}
	
	/**
	 * Creates and returns a new TransplantGraph object, intialized with the
	 * donor information found in donorFile and the recipient information found
	 * in recipientFile.
	 *
	 * @param donorFile     the donor file
	 * @param recipientFile the recipient file
	 * @return the transplant graph
	 */
	public static TransplantGraph buildFromFiles(String donorFile,
			String recipientFile) {
		
		TransplantGraph transplantgraph = new TransplantGraph();
		
		
		try {
			Scanner scanner = new Scanner(new File(donorFile));
			String[] itemStr = null;
			while (scanner.hasNextLine()) {
				
				String fileLine = scanner.nextLine();
				itemStr = fileLine.split(", |\n");
					
				int index = Integer.parseInt(itemStr[0]);
				String name = itemStr[1].trim();
				int age = Integer.parseInt(itemStr[2]);
				String organ = itemStr[3].trim();
				String bloodtype = itemStr[4].toUpperCase().trim();
				BloodType blood = new BloodType(bloodtype);
				Patient listedDonor = new Patient(name, organ, age, blood, index, true);
				transplantgraph.addDonor(listedDonor);
				
			}
			
			scanner = new Scanner(new File(recipientFile));
			
			while (scanner.hasNextLine()) {
				String fileLine = scanner.nextLine();
				itemStr = fileLine.split(", |\n");

				int index = Integer.parseInt(itemStr[0]);
				String name = itemStr[1].trim();
				int age = Integer.parseInt(itemStr[2]);
				String organ = itemStr[3].trim();
				String bloodtype = itemStr[4].toUpperCase().trim();
				BloodType blood = new BloodType(bloodtype);
				Patient listedRecipient = new Patient(name, organ, age, blood, index, false);
				transplantgraph.addRecipient(listedRecipient);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("File was not found.\n");
		} catch (NumberFormatException e) {
			System.out.println("Error found from loading text.");
		}
		return transplantgraph;
	}

	/**
	 * Adds the specified Patient to the recipients list. This method must also
	 * update the connections adjacency matrix, as necessary.
	 *
	 * @param patient the patient
	 */
	public void addRecipient(Patient patient) {
		recipients.add(patient);
		recipientPatientsAdded++;
		int i = recipients.size()-1;
		for(int j=0; j<donors.size();j++) {
			if(BloodType.isCompatible(patient.getPatientBloodType(), donors.get(j).getPatientBloodType())) {
				String recipientOrgan = patient.getOrgan().trim().toUpperCase();
				String donorsOrgan = donors.get(j).getOrgan().trim().toUpperCase();
				
				if(donorsOrgan.equals(recipientOrgan)) {
					connections[i][j] = true;
				}
			}
		}
	}

	/**
	 * Adds the specified Patient to the donors list. This method must also
	 * update the connections adjacency matrix, as necessary.
	 *
	 * @param patient the patient
	 */
	public void addDonor(Patient patient) {
		donors.add(patient);
		donorPatientsAdded++;
		int j = donors.size()-1;
		for(int i=0; i<recipients.size();i++) {
			if(BloodType.isCompatible(recipients.get(i).getPatientBloodType(), patient.getPatientBloodType())) {
				String donorsOrgan = patient.getOrgan().trim().toUpperCase();
				String recipientsOrgan = recipients.get(i).getOrgan().trim().toUpperCase();
				if(recipientsOrgan.equals(donorsOrgan)) {
					connections[i][j] = true;
				}
			}
		}
		
	}
	
	/**
	 * Helper method to update connections through the list when it is 
	 * sorted/changed through other means when removing or calling the 
	 * printAllRecipients/printAllDonors methods.
	 * 
	 */
	private void updateConnections() {
		connections = new boolean[MAX_PATIENTS][MAX_PATIENTS];
		for(int i=0; i<recipients.size(); i++) {
			int numOfConnections = 0;
			for(int j=0; j<donors.size();j++) {
				Patient recipient = recipients.get(i);
				Patient donor = donors.get(j);
				if(BloodType.isCompatible(recipient.getPatientBloodType(), donor.getPatientBloodType())) {
					String donorsOrgan = donor.getOrgan().trim().toUpperCase();
					String recipientsOrgan = recipient.getOrgan().trim().toUpperCase();
					if(recipientsOrgan.equals(donorsOrgan)) {
						numOfConnections++;
						connections[i][j] = true;
					}
				}
			}
			recipients.get(i).setNumOfConnections(numOfConnections);
		}
	}

	/**
	 * Removes the specified Patient from the recipients list. This method must
	 * also update the connections adjacency matrix, removing all connections to
	 * this recipient, and removing the column associated with the patient from
	 * the matrix.
	 *
	 * @param name the name
	 * @throws NameNotFoundException the name not found exception
	 */
	public void removeRecipient(String name) throws NameNotFoundException {
		name = name.trim();
		for(int i=0; i < recipients.size(); i++) {
			if(recipients.get(i).getName().equals(name)) {
				System.out.println("\n" + name + " was removed from the organ transplant waitlist.");
				recipients.remove(i);
				updateConnections();
				return;
			}
		}
		throw new NameNotFoundException("\n" + name + " was not found in the list.");
	}

	/**
	 * Removes the specified Patient from the donors list. This method must also
	 * update the connections adjacency matrix, removing all connections to this
	 * donor, and removing the row associated with the patient from the matrix.
	 *
	 * @param name the name
	 * @throws NameNotFoundException the name not found exception
	 */
	public void removeDonor(String name) throws NameNotFoundException{
		name = name.trim();
		for(int i=0; i < donors.size(); i++) {
			if(donors.get(i).getName().equals(name)) {
				System.out.println("\n" + name + " was removed from the organ donor list.");
				donors.remove(i);
				updateConnections();
				return;
			}
		}
		throw new NameNotFoundException("\n" + name + " was not found in the list.");
	}

	/**
	 * Prints all organ recipients' information in a neatly formatted table.
	 */
	public void printAllRecipients() {
		updateConnections();
		System.out.println("\nIndex | Recipient Name     | Age | Organ Needed | Blood Type | Donor IDs");
		System.out.println("========================================================================");
		
		for(int i=0; i < recipients.size(); i++) {
			String donorIDs = "";
			for(int j=0; j < donors.size(); j++) {
				if(connections[i][j]) {
					//donorIDs += j + ", ";
					donorIDs += donors.get(j).getPatientID() + ", ";
				}
			}
			if(donorIDs.equals("")) {
				//System.out.println(String.format("%5d ", i) + recipients.get(i).toString() + donorIDs);
				System.out.println(recipients.get(i).toString() + donorIDs);
			}
			else {
				donorIDs = donorIDs.substring(0, donorIDs.length()-2);
				//System.out.println(String.format("%5d ", i) + recipients.get(i).toString() + donorIDs);
				System.out.println(recipients.get(i).toString() + donorIDs);
			}

		}
	}

	/**
	 * Prints all organ donors' information in a neatly formatted table.
	 */
	public void printAllDonors() {
		
		updateConnections();
		System.out.println("\nIndex | Donor Name         | Age | Organ Needed | Blood Type | Recipient IDs");
		System.out.println("============================================================================");
		for(int j=0; j < donors.size(); j++) {
			String recipientIDs = "";
			for(int i=0; i < recipients.size(); i++) {
				if(connections[i][j]) {
					//recipientIDs += i + ", ";
					recipientIDs += recipients.get(i).getPatientID() + ", ";
				}
			}
			if(recipientIDs.equals("")) {
				//System.out.println(String.format("%5d ", j) + donors.get(j).toString() + recipientIDs);
				System.out.println(donors.get(j).toString() + recipientIDs);
			}
			else {
				recipientIDs = recipientIDs.substring(0, recipientIDs.length()-2);
				//System.out.println(String.format("%5d ", j) + donors.get(j).toString() + recipientIDs);
				System.out.println(donors.get(j).toString() + recipientIDs);
			}
		}
	}

}
