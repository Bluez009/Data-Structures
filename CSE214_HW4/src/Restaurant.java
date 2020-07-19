import java.util.ArrayList;
/**
 * The Restaurant represents a customer that will dine at your restaurant.
 */
public class Restaurant extends ArrayList<Customer> {

	/**
	 * Instantiates a new restaurant.
	 */
	public Restaurant() {}

	/**
	 * Adds a new customer c to the restaurant.
	 *
	 * @param c the c
	 */
	public void enqueue(Customer c) {
		super.add(c);
	}

	/**
	 * Removes and returns the first Customer in the restaurant.
	 *
	 * @return the customer
	 */
	public Customer dequeue() {
		return super.remove(0);
	}

	/**
	 * Returns, but does not remove, the first Customer in the restaurant.
	 *
	 * @return the customer
	 */
	public Customer peek() {
		return super.get(0);
	}

	/**
	 * Returns the number of Customers in the restaurant.
	 *
	 * @return the int
	 */
	public int size() {
		return super.size();
	}

	/**
	 * Returns true if there are no Customers in the restaurant, false
	 * otherwise.
	 *
	 * @return true, if is not empty, else false
	 */
	public boolean isEmpty() {
		return super.isEmpty();
	}

	/**
	 * Returns a String representation of this Restaurant object.
	 *
	 * @return the string
	 */
	public String toString() {
		String output = "{";
		int size = size();
		for(int i=0; i<size;i++) {
			Customer tmp = dequeue();
			output += tmp.toString();
			add(tmp);
			
			if(i!=size-1) {
				output+= ", ";
			}
		}
		output += "}\n";
		
		
		return output;
	}

}
