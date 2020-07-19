import java.io.Serializable;

/**
 * Write a fully-documented class Patient which represents an active organ donor
 * or recipient in the database. This class should implement Comparable and
 * provide a compareTo method, so that it may be easily sorted by the ID field.
 * 
 * @author Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *         ID: 111230826
 */
public class Patient implements Serializable, Comparable<Patient> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The name of the donor/recipient. */
	private String name;

	/** The organ the patient is donating or receiving. */
	private String organ;

	/** The age of the patient. */
	private int age;

	/** The blood type of the patient. */
	private BloodType bloodtype;

	/**
	 * The ID number of the patient. If a donor, this must be unique amongst all
	 * donors. If a recipient, this must be unique amongst all recipients. This
	 * is used to map this patient to an index in the adjacency matrix, as it
	 * denotes which row or column this patient's connections are stored in.
	 */
	private int patientID;

	/** True if this Patient is a donor, false if a recipient. */
	private boolean isDonor;

	/** 
	 * Keeps count of number of connections.
	 */
	private int numOfConnections;
	
	/**
	 * Instantiates a new patient.
	 */
	public Patient() {

	}

	/**
	 * Instantiates a new patient.
	 *
	 * @param name the name
	 * @param organ the organ
	 * @param age the age
	 * @param bloodtype the bloodtype
	 * @param patientID the patient ID
	 * @param isDonor the is donor
	 */
	public Patient(String name, String organ, int age, BloodType bloodtype,
			int patientID, boolean isDonor) {
		this.name = name;
		this.organ = organ.toUpperCase();
		this.age = age;
		this.bloodtype = bloodtype;
		this.patientID = patientID;
		this.isDonor = isDonor;
		this.numOfConnections = 0;
	}
	
	/**
	 * Gets the patient blood type.
	 *
	 * @return the patient blood type
	 */
	public BloodType getPatientBloodType() {
		return bloodtype;
	}

	/**
	 * Gets the patient ID.
	 *
	 * @return the patient ID
	 */
	public int getPatientID() {
		return patientID;
	}
	
	/**
	 * Gets the organ.
	 *
	 * @return the organ
	 */
	public String getOrgan() {
		return organ;
	}
	
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the num of connections.
	 *
	 * @return the num of connections
	 */
	public int getNumOfConnections() {
		return numOfConnections;
	}
	
	/**
	 * Sets the num of connections.
	 *
	 * @param num the new num of connections
	 */
	public void setNumOfConnections(int num) {
		numOfConnections = num;
	}
	/**
	 * Compares this Patient object to obj, comparing by ID such that the values
	 * should be sorted in ascending order.
	 *
	 * @param obj the object
	 * @return the int
	 */
	public int compareTo(Patient obj) {
		if(this.patientID== obj.getPatientID()) {
			return 0;
		}
		else if(this.patientID>obj.getPatientID()) {
			return 1;
		}
		else {
			return -1;
		}
	}

	/**
	 * Returns a String representation of this Patient object.
	 *
	 * @return the string
	 */
	public String toString() {
//		return String.format("| %-18s | %-3d | %-12s |     %-6s | ",
//				name, age, organ, bloodtype.getBloodType());
		return String.format("%5d | %-18s | %-3d | %-12s |     %-6s | ",
				patientID, name, age, organ, bloodtype.getBloodType());
	}
	


}
