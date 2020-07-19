import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * NOTE: USE json-simple-1.1.1 file to make use of JSON methods/classes!
 * 		Go to project explorer, right click on directory -> Properties -> 
 * 		Java Build Path -> Add External JAR
 */
/**
 * The class HashedGrocery which will contain all the methods that will allow
 * employees to perform different functions on the inventory of the grocery
 * store. Here, you will use the item code of every item as the key of a hash
 * table. You can either use the HashTable or HashMap data structure to store
 * your inventory.
 * 
 * @author Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *         ID: 111230826
 */
public class HashedGrocery implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The current business day. Starts from 1. */
	private int businessDay;

	/** The hash table to store all item records. */
	private Hashtable<String, Item> hashTable = new Hashtable<String, Item>();

	/**
	 * Instantiates a new hashed grocery.
	 */
	public HashedGrocery() {
		businessDay = 1;
	}

	/**
	 * Gets the business day.
	 *
	 * @return the business day
	 */
	public int getBusinessDay() {
		return businessDay;
	}

	/**
	 * Adds item to the hash table. An exception should be thrown if an item
	 * with the same item code already exists in the hash table.
	 *
	 * @param item the item
	 * 
	 * @throws DuplicateItemException thrown if a duplicate item is found in the
	 *                                hash table
	 */
	public void addItem(Item item) throws DuplicateItemException {
		Item dupe = (Item) hashTable.get(item.getItemCode());
		if (dupe == null || !dupe.getItemCode().equals(item.getItemCode())) {
			hashTable.put(item.getItemCode(), item);
		} else {
			throw new DuplicateItemException(item.getItemCode()
					+ ": Cannot add item as item code already exists.");
		}
	}

	/**
	 * Changes the qtyInStore amount of item by adjustByQty.
	 *
	 * @param item        the item
	 * @param adjustByQty the adjust by qty
	 * @throws NotEnoughStockException thrown if adjustByQty is greater then
	 *                                 what's left in the store
	 */
	public void updateItem(Item item, int adjustByQty)
			throws NotEnoughStockException {
		int leftOvers = item.getQtyInStore() - adjustByQty;
		if (leftOvers < 0) {
			throw new NotEnoughStockException(item.getItemCode()
					+ ": Not enough stock for sale. Not updated.");
		}

		int minQty = item.getAvgSalesPerDay() * 3;

		if (leftOvers < minQty && item.getOnOrder() == 0) {
			int qtyToOrder = item.getAvgSalesPerDay() * 2;
			item.placeOrder(qtyToOrder);
			item.setQtyInStore(leftOvers);
			item.setArrivalDay(businessDay + 3);
			System.out.println(item.getItemCode() + ": " + adjustByQty
					+ " units of " + item.getName()
					+ " are sold. Order has been placed for " + qtyToOrder
					+ " more units.");
		} else {
			item.setQtyInStore(leftOvers);
			System.out.println(item.getItemCode() + ": " + adjustByQty
					+ " units of " + item.getName() + " are sold.");
		}
	}

	/**
	 * Adds all the items present in the text file. The supplied file should
	 * contain JSON information for one or more Item objects. Any duplicate
	 * items within the file should be ignored. An exception thrown if filename
	 * does not exist.
	 *
	 * @param filename the filename
	 * 
	 */
	public void addItemCatalog(String filename) {
		try {
			FileInputStream fis = new FileInputStream(filename);
			InputStreamReader isr = new InputStreamReader(fis);
			JSONParser parser = new JSONParser();

			JSONArray objs = (JSONArray) parser.parse(isr);

			for (int i = 0; i < objs.size(); i++) {
				JSONObject obj = (JSONObject) objs.get(i);
				String itemCode = (String) obj.get("itemCode");
				String name = (String) obj.get("itemName");
				int avgSales = Integer.parseInt((String) obj.get("avgSales"));
				int qtyInStore = Integer
						.parseInt((String) obj.get("qtyInStore"));
				double price = Double.parseDouble((String) obj.get("price"));
				int amtOnOrder = Integer
						.parseInt((String) obj.get("amtOnOrder"));

				if (itemCode == null || name == null) {
					System.out.println(
							"File does not contain the proper format to "
							+ "retrieve data. Items and hashtable were not "
							+ "changed.");
					return;
				}

				Item item = new Item(itemCode, name, qtyInStore, avgSales,
						price, amtOnOrder, 0);

				try {
					addItem(item);
					System.out.println(
							itemCode + ": " + name + " is added to inventory.");
				} catch (DuplicateItemException e) {
					System.out.println(e.getMessage());
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File was not found... Items were not added.");
		} catch (IOException e) {
			System.out.println("IO Exception Error");
		} catch (ParseException e) {
			System.out.println("Parse Exception Error");
		} catch (NumberFormatException e) {
			System.out.println(
					"File does not contain the proper format to retrieve data."
					+ " Items and hashtable were not changed.");
		}

	}

	/**
	 * Process sales.
	 *
	 * @param filename the filename
	 */
	public void processSales(String filename) {
		try {
			FileInputStream fis = new FileInputStream(filename);
			InputStreamReader isr = new InputStreamReader(fis);
			JSONParser parser = new JSONParser();

			JSONArray objs = (JSONArray) parser.parse(isr);
			for (int i = 0; i < objs.size(); i++) {
				JSONObject obj = (JSONObject) objs.get(i);
				String itemCode = (String) obj.get("itemCode");
				int qtySold = Integer.parseInt((String) obj.get("qtySold"));

				if (itemCode == null) {
					System.out.println(
							"File does not contain the proper format to "
							+ "retrieve data. Items and hashtable were not "
							+ "changed.");
					return;
				}
				Item item = hashTable.get(itemCode);
				if (item == null) {
					System.out.println(itemCode+ ": Cannot buy as it is not "
							+ "in the grocery store.");
				} else {
					if (item.getItemCode().equals(itemCode)) {
						try {
							updateItem(item, qtySold);
							hashTable.put(itemCode, item);
						} catch (NotEnoughStockException e) {
							System.out.println(e.getMessage());
						}
					}
				}

			}
		} catch (FileNotFoundException e) {
			System.out.println(
					"File was not found... Items and hashTable were not "
					+ "changed.");
		} catch (IOException e) {
			System.out.println("IO Exception Error");
		} catch (ParseException e) {
			System.out.println("Parse Exception Error");
		} catch (NumberFormatException e) {
			System.out.println(
					"File does not contain the proper format to retrieve data."
					+ " Items and hashtable were not changed.");
		}
	}

	/**
	 * Increases businessDay by 1, and checks to see if any orders have arrived.
	 * If so, it updates the quantities of items in the store for those orders
	 * which have arrived, then sets the onOrder and the arrivalDay variables
	 * for those items to 0.
	 */
	public void nextBusinessDay() {
		businessDay++;
		System.out.println("Advancing buisness day...");
		System.out.println("Business Day " + businessDay + ".\n");
		Set<String> itemCodes = hashTable.keySet();

		Iterator<String> iterator = itemCodes.iterator();
		String orders = "";
		boolean didOrderArrive = false;
		while (iterator.hasNext()) {

			String key = iterator.next();
			Item item = (Item) hashTable.get(key);
			if (businessDay == item.getArrivalDay()) {
				int newQty = item.getQtyInStore() + item.getOnOrder();
				item.setQtyInStore(newQty);
				orders += item.getItemCode() + ": " + item.getOnOrder()
						+ " units of " + item.getName() + ".\n";
				item.placeOrder(0);
				item.setArrivalDay(0);
				hashTable.replace(item.getItemCode(), item);
				didOrderArrive = true;
			}
		}
		if (didOrderArrive) {
			System.out.println("Orders have arrived for:\n\n" + orders);
		} else {
			System.out.println("No Orders have arrived.\n");
		}

	}

	/**
	 * Prints all items in a neatly formatted table.
	 *
	 * @return the string
	 */
	public String toString() {
		String tableStr = hashTable.toString().replace("}", "").replace("{",
				"");
		String[] itemStr = tableStr.split(", ");
		String itemList = "";

		for (int i = 0; i < itemStr.length; i++) {
			itemList += itemStr[i].substring(itemStr[i].indexOf("=") + 1)
					+ "\n";
		}

		return itemList;
	}
}
