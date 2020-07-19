import java.util.InputMismatchException;
import java.util.Scanner;

//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;


import java.net.*;
import java.io.*;

/**
 * The class GroceryDriver will act as the main driver for the inventory
 * management system. When the program first starts, it should attempt to
 * deserialize any previously serialized data. When the program terminates, it
 * should attempt to serialize the hash table containing all item information.
 * 
 * @author Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *         ID: 111230826
 */
public class GroceryDriver implements Serializable {

	/** The Constant serialVersionUID. */
	public static final long serialVersionUID = 1L;

	/** The Constant serialName to be associated with file name created. */
	public static final String serialName = "grocery2.obj";

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		
//		URL url;
//		String outputStr = "";
//		try {
//			url = new URL("https://raw.githubusercontent.com/Bluez009/SeniorDesign/master/GroceryItems1.txt");
//			BufferedReader read = new BufferedReader(new InputStreamReader(url.openStream()));
//	        String line;
//	        while ((line = read.readLine()) != null)
//	            outputStr += line + "\n";
//	        read.close();
//	        System.out.println(outputStr);
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		
//		JSONArray aryJSONStrings = new JSONArray(outputStr);
//		try {
//			for (int i=0; i<aryJSONStrings.length(); i++) {
//				String barcode = aryJSONStrings.getJSONObject(i).getString("itemBarcode");
//				String itemName =aryJSONStrings.getJSONObject(i).getString("itemName");
//				double itemCost = Double.parseDouble(aryJSONStrings.getJSONObject(i).getString("itemCost"));
//				System.out.println(barcode + "\n" + itemName + "\n" + itemCost);
//			}
//			System.out.println("-------------------------");
//		} catch (JSONException e) {
//			System.out.println("Not all information was retrieved.");
//		}
//		
		
//		try {
//			//FileInputStream fis = new FileInputStream(outputStr);
//			//InputStreamReader isr = new InputStreamReader(fis);
//			//JSONParser parser = new JSONParser(outputStr);
//			
//		    
//			JSONObject objs = new JSONObject(outputStr);
//			objs.keys();
//			JSONArray barcodeArr = objs.getJSONArray("itemBarcode");
//			
//			
////				JSONObject obj = (JSONObject) objs.get(i);
//				String barcode = (String) objs.get("itemBarcode");
//				String itemName = (String) objs.get("itemName");
//				double itemCost = Double.parseDouble((String) objs.get("itemCost"));
//				System.out.println(barcode + "\n" + itemName + "\n" + itemCost);
//				
//				barcode = (String) objs.get("itemBarcode");
//				itemName = (String) objs.get("itemName");
//				itemCost = Double.parseDouble((String) objs.get("itemCost"));
//				System.out.println(barcode + "\n" + itemName + "\n" + itemCost);
//			//}
//			
//		} catch (NumberFormatException e) {
//			System.out.println(
//					"File does not contain the proper format to retrieve data."
//					+ " Items and hashtable were not changed.");
//		}
//
//		    //JSONObject json = new JSONObject(outputStr);
//
//	
		
		

	
		
		HashedGrocery groceries = null;
		try {
			FileInputStream file = new FileInputStream(serialName);
			ObjectInputStream fin = new ObjectInputStream(file);
			groceries = (HashedGrocery) fin.readObject();
			fin.close();
			System.out.println(
					"HashedGrocery is loaded from " + serialName + ".\n");
		} catch (IOException e) {
			System.out.println(
					"Grocery obj does not exist. Creating new HashedGrocery "
					+ "obj.\n");
			groceries = new HashedGrocery();
		} catch (ClassNotFoundException e) {
			System.out.println("Something broke here... Class not found...");
			System.out.println(
					"Grocery obj does not exist. Creating new HashedGrocery "
					+ "obj.\n");
			groceries = new HashedGrocery();
		}

		System.out
				.println("Business Day " + groceries.getBusinessDay() + ".\n");
		while (true) {

			System.out.println("Menu:\n--------------------------------"
					+ "\n(L) Load item catalog\n" + "(A) Add items\n"
					+ "(B) Process Sales\n" + "(C) Display all items\n"
					+ "(N) Move to next business day\n" + "(Q) Quit \n");
			System.out.print("Please enter a selection: ");
			String command = input.next().toUpperCase();
			switch (command) {
			case ("L"):
				System.out.print("\nEnter file to load: ");
				String itemCatalogFile = input.next();
				System.out.println("");
				groceries.addItemCatalog(itemCatalogFile);
				System.out.println("");
				break;

			case ("A"):
				try {
					System.out.print("\nEnter item code: ");
					String itemcode = input.next();
					input.nextLine();
					System.out.print("Enter item name: ");
					String itemName = input.nextLine();
					System.out.print("Enter Quantity in store: ");
					int storeQuantity = input.nextInt();
					input.nextLine();
					System.out.print("Enter Average sales per day: ");
					int avgSales = input.nextInt();
					input.nextLine();
					System.out.print("Enter price: ");
					double price = input.nextDouble();
					input.nextLine();
					Item newItem = new Item(itemcode, itemName, storeQuantity,
							avgSales, price);
					groceries.addItem(newItem);
					System.out.println("\n" + itemcode + ": " + itemName
							+ " added to inventory.\n");

				} catch (DuplicateItemException e) {
					System.out.println("\n" + e.getMessage() + "\n");
				} catch (InputMismatchException e) {
					System.out.println(
							"Input mismatch... The item was not created or "
							+ "added to the hashtable.\n");
					input.nextLine();
				}

				break;
			case ("B"):

				try {
					System.out.print("\nEnter filename: ");
					String salesCatalogFile = input.next();
					System.out.println("");
					groceries.processSales(salesCatalogFile);
					System.out.println("");
				} catch (InputMismatchException e) {
					System.out.println(
							"Input mismatch... The item was not created or "
							+ "added to the hashtable.");
				}

				break;
			case ("C"):
				System.out.println(
						"\nItem code   Name                            Qty   "
						+ "AvgSales   Price    OnOrder    ArrOnBusDay");
				System.out.println(
						"----------------------------------------------------"
						+ "------------------------------------------");
				System.out.println(groceries.toString());
				break;
			case ("N"):
				System.out.println("");
				groceries.nextBusinessDay();
				break;
			case ("Q"):
				try {
					FileOutputStream file = new FileOutputStream(serialName);
					ObjectOutputStream fout = new ObjectOutputStream(file);

					fout.writeObject(groceries);
					fout.close();
					file.close();
					System.out.println(
							"\nHashedGrocery is saved in grocery.obj.\n");
				} catch (IOException ex) {
					System.out.println(
							"\nSomething Broke. Object was not serialized.\n");
				}

				System.out.println("Program Terminating normally...");

				return;
			default:
				System.out.print("The command does not exist. Try again. \n");
			}
		}

	}
}
