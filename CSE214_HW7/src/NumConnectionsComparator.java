import java.util.Comparator;

/**
 * The Class NumConnectionsComparator that will be used to sort the patient lists.
 * 
 * @author Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *         ID: 111230826
 */
public class NumConnectionsComparator implements Comparator<Patient>{

	/**
	 * Compare Patient objects with their connections variable.
	 *
	 * @param o1 patient object 1
	 * @param o2 patient object 2
	 * @return the int
	 */
	@Override
	public int compare(Patient o1, Patient o2) {
		if(o1.getNumOfConnections() == o2.getNumOfConnections()) {
			return 0;
		}
		else if(o1.getNumOfConnections() > o2.getNumOfConnections()) {
			return 1;
		}
		else {
			return -1;
		}
	}
	
}
