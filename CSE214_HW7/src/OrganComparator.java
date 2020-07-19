import java.util.Comparator;

/**
 * The Class OrganComparator that will be used to sort the patient lists.
 * 
 * @author Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *         ID: 111230826
 */
public class OrganComparator implements Comparator<Patient>{

	/**
	 * Compare Patient objects with their organ variables.
	 *
	 * @param o1 patient object 1
	 * @param o2 patient object 2
	 * @return the int
	 */
	@Override
	public int compare(Patient o1, Patient o2) {
		return (o1.getOrgan().compareTo(o2.getOrgan()));
	}

}
