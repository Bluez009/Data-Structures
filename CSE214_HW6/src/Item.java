import java.io.Serializable;

/**
 * The class Item which will contain all the information regarding an item.
 * 
 * @author Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *         ID: 111230826
 */
public class Item implements Serializable {

	/** The Constant serialVersionUID. */
	public static final long serialVersionUID = 1L;
	
	/**
	 * The item code. Must be unique as it will serve as the key in the hash
	 * table.
	 */
	private String itemCode;

	/** The name. */
	private String name;

	/** The quantity in store. */
	private int qtyInStore;

	/** The average sales per day. */
	private int averageSalesPerDay;

	/** How many have already been ordered for restocking. */
	private int onOrder;

	/**
	 * Keep track of the business day at which your order for this item will
	 * arrive. Every order takes 2 whole days to ship and will arrive on the
	 * beginning of the third day, so you should assign a value to this variable
	 * depending on that and the business day you are currently on. For example,
	 * if you are on business day 1, then you should set this variable to 4. If
	 * there is nothing currently on order, then the value of this variable
	 * should be 0.
	 */
	private int arrivalDay;

	/** The price. */
	private double price;

	/**
	 * Instantiates a new item.
	 */
	public Item() {

	}

	/**
	 * Instantiates a new item with set parameters.
	 *
	 * @param itemCode           the item code
	 * @param name               the name
	 * @param qtyInStore         the qty in store
	 * @param averageSalesPerDay the average sales per day
	 * @param price              the price
	 */
	public Item(String itemCode, String name, int qtyInStore,
			int averageSalesPerDay, double price) {
		this.itemCode = itemCode;
		this.name = name;
		this.qtyInStore = qtyInStore;
		this.averageSalesPerDay = averageSalesPerDay;
		this.price = price;
		onOrder = 0;
		arrivalDay = 0;
	}

	/**
	 * Instantiates a new item with set parameters.
	 *
	 * @param itemCode           the item code
	 * @param name               the name
	 * @param qtyInStore         the qty in store
	 * @param averageSalesPerDay the average sales per day
	 * @param price              the price
	 * @param onOrder            the on order
	 * @param arrivalDay         the arrival day
	 */
	public Item(String itemCode, String name, int qtyInStore,
			int averageSalesPerDay, double price, int onOrder, int arrivalDay) {
		this.itemCode = itemCode;
		this.name = name;
		this.qtyInStore = qtyInStore;
		this.averageSalesPerDay = averageSalesPerDay;
		this.price = price;
		this.onOrder = onOrder;
		this.arrivalDay = arrivalDay;
	}

	/**
	 * Gets the avg sales per day.
	 *
	 * @return the avg sales per day
	 */
	public int getAvgSalesPerDay() {
		return averageSalesPerDay;
	}

	/**
	 * Gets the on order.
	 *
	 * @return the on order
	 */
	public int getOnOrder() {
		return onOrder;
	}

	/**
	 * Place order.
	 *
	 * @param order the order
	 */
	public void placeOrder(int order) {
		onOrder = order;
	}

	/**
	 * Gets the item code.
	 *
	 * @return the item code
	 */
	public String getItemCode() {
		return itemCode;
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
	 * Gets the qty in store.
	 *
	 * @return the qty in store
	 */
	public int getQtyInStore() {
		return qtyInStore;
	}

	/**
	 * Sets the arrival day.
	 *
	 * @param day the new arrival day
	 */
	public void setArrivalDay(int day) {
		arrivalDay = day;
	}

	/**
	 * Gets the arrival day.
	 *
	 * @return the arrival day
	 */
	public int getArrivalDay() {
		return arrivalDay;
	}

	/**
	 * Sets the qty in store.
	 *
	 * @param quantity the new qty in store
	 */
	public void setQtyInStore(int quantity) {
		qtyInStore = quantity;
	}

	/**
	 * Returns a String representation of this Item.
	 *
	 * @return the string
	 */
	public String toString() {
		return String.format("%-12s%-30s%5d%11d%8.2f%11d%15d", itemCode, name,
				qtyInStore, averageSalesPerDay, price, onOrder, arrivalDay);
	}
}
