import java.util.Comparator;

/**
 * The Class BloodTypeComparator that will be used to sort the patient lists.
 * 
 * @author Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *         ID: 111230826
 */
public class BloodTypeComparator implements Comparator<Patient>{

	/**
	 * Compare Patient objects with their bloodtype variables.
	 *
	 * @param o1 patient object 1
	 * @param o2 patient object 2
	 * @return the int
	 */
	@Override
	public int compare(Patient o1, Patient o2) {
		String bloodtype1 = o1.getPatientBloodType().getBloodType();
		String bloodtype2 = o2.getPatientBloodType().getBloodType();
		return (bloodtype1.compareTo(bloodtype2));
	}

}
