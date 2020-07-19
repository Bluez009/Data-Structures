
/**
 * The Class Customer.
 */
public class Customer {

	/**
	 * The total number of customers that have showed up to the chain. This
	 * number is incremented by one every time a customer enters one of the
	 * restaurants.
	 */
	private static int totalCustomers = 0;

	/**
	 * Every time a customer enters one of the restaurants, they will be
	 * assigned a number. This will be automatically determined using
	 * totalCustomers.
	 */
	private int orderNumber;

	/**
	 * This String is a representation of the food that a customer has ordered.
	 */
	private String food;

	/**
	 * The overall price of the order that will be added to the restaurant's
	 * profit once the customer pays the bill.
	 */
	private int priceOfFood;

	/** The simulation unit when the customer arrived at the restaurant. */
	private int timeArrived;

	/**
	 * The max time it will take to recieve their order, pay and leave the
	 * restaurant
	 */
	private int maxTime;

	/**
	 * The time it will take to serve the customer before the customer leaves
	 * the restaurant in minutes, dictated by the guidelines set out above
	 * regarding which food the customer ordered. For every simulation unit,
	 * this number will decrease. Once it reaches zero, it means that the
	 * customer has finished their food and that they are going to leave at the
	 * beginning of the next simulation unit.
	 */
	private int timeToServe;

	/**
	 * Instantiates a new customer.
	 */
	public Customer() {
	}

	/**
	 * Instantiates a new customer with food, the price, and time arrived.
	 */
	public Customer(String food, int priceOfFood, int timeArrived,
			int timeToServe) {
		this.food = food;
		this.priceOfFood = priceOfFood;
		this.timeArrived = timeArrived;
		this.orderNumber = totalCustomers;
		this.timeToServe = timeToServe;
		this.maxTime = timeToServe;
	}

	/**
	 * Returns a String representation of this Customer object. This should be
	 * in the format [#orderNumber, food, timeToServe min.], as shown in the
	 * Sample Output below. To shorten the I/O, your food should be abbreviated
	 * by initial, i.e. "S" for steak, "CW" for chicken wings, "CT" for chicken
	 * tenders, etc.
	 *
	 * @return the string
	 */
	public String toString() {
		return "[#" + orderNumber + ", " + food + ", " + timeToServe + " min.]";
	}

	/**
	 * Retreives total customers.
	 * 
	 * @return amount of customers
	 */
	public static int getTotalCustomers() {
		return totalCustomers;
	}

	/**
	 * Increment amount of total customers by 1.
	 */
	public static void customerEntered() {
		++Customer.totalCustomers;
	}

	/**
	 * Retrieves the food string.
	 * 
	 * @return String of food
	 */
	public String getFood() {
		return food;
	}

	/**
	 * Retrieve order number.
	 * 
	 * @return Customers order number
	 */
	public int getOrderNumber() {
		return orderNumber;
	}

	/**
	 * Retrieve the price of food.
	 * 
	 * @return integer price of food
	 */
	public int getPriceOfFood() {
		return priceOfFood;
	}

	/**
	 * Retrieve time left to finish their meal.
	 * 
	 * @return integer of customers time left
	 * 
	 */
	public int getTimeToServe() {
		return timeToServe;
	}

	/**
	 * Retrieve max time it takes to complete their meal.
	 * 
	 * @return integer max time
	 */
	public int getMaxTime() {
		return maxTime;
	}

	/**
	 * Determines if the customer still has time left to finish their meal.
	 * 
	 * @return true if timeToServe is 0, else false
	 */
	public boolean isFinished() {
		timeToServe = timeToServe - 5;
		if (timeToServe == 0) {
			return true;
		}
		return false;
	}
}
