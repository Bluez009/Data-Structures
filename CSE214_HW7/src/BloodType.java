import java.io.Serializable;

/**
 * The BloodType class will contain a String member variable bloodType to denote
 * a specific blood type, and a static method which can be invoked to determine
 * if two blood types are compatible with each other.
 * 
 * @author Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *         ID: 111230826
 */
public class BloodType implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The bloodtype. */
	private String bloodtype;

	/**
	 * Instantiates a new blood type.
	 */
	public BloodType() {
	}

	/**
	 * Instantiates a new blood type.
	 *
	 * @param bloodtype the bloodtype
	 */
	public BloodType(String bloodtype) {
		this.bloodtype = bloodtype;
	}

	/**
	 * Gets the blood type.
	 *
	 * @return the blood type
	 */
	public String getBloodType() {
		return bloodtype;
	}

	/**
	 * Sets the blood type.
	 *
	 * @param bloodtype the new blood type
	 */
	public void setBloodType(String bloodtype) {
		this.bloodtype = bloodtype;
	}

	/**
	 * Checks if it is valid blood type.
	 *
	 * @param bloodtype the bloodtype
	 * @return true, if is valid blood type, false otherwise
	 */
	public static boolean isValidBloodType(String bloodtype) {
		if (bloodtype.equals("A") || bloodtype.equals("B")
				|| bloodtype.equals("AB") || bloodtype.equals("O")) {
			return true;
		}
		return false;
	}

	/**
	 * Determines whether two blood types are compatible, returning true if they
	 * are, and false otherwise.
	 *
	 * @param recipient the recipient
	 * @param donor     the donor
	 * @return true, if is compatible false otherwise
	 */
	public static boolean isCompatible(BloodType recipient, BloodType donor) {
		String recipientBT = recipient.getBloodType();
		String donorBT = donor.getBloodType();
		if (recipientBT.equals(donorBT)) {
			return true;
		}
		if (recipientBT.equals("AB")) {
			return true;
		}
		if (donorBT.equals("O")) {
			return true;
		}
		return false;
	}
	

}
