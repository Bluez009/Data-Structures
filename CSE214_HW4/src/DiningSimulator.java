import java.util.ArrayList;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The Class DiningSimulator.
 */
public class DiningSimulator {

	/**
	 * The collection of Restaurant objects, which Customers may enter. This
	 * will be used to seat customers and to feed them. You may use any data
	 * structure that you want (ArrayList, LinkedList, etc.).
	 */
	private ArrayList<Restaurant> restaurantsList;

	/** The number of chefs preset at each restaurant. */
	private int chefs;

	/** The number of simulation units to perform. */
	private int duration;

	/** The probability ([0.0, 1.0]) of a new customer arriving. */
	private double arrivalProb;

	/**
	 * The maximum number of customers that can be seated at each restaurant.
	 */
	private int maxCustomerSize;

	/** The number of restaurants to simulate. */
	private int numRestaurants;

	/** The number of customers which were not able to be seated and left. */
	private int customerLost;

	/** The sum total of the time each customer has spent at a restaurant. */
	private int totalServiceTime;

	/** The number of customers which were properly served. */
	private int customersServed;

	/** The amount of money gained at the end of the simulation. */
	private int profit;

	public void setNumRestaurants(int num) throws InvalidNumberInputException {
		if (num <= 0) {
			throw new InvalidNumberInputException(
					"The value inputed for number of restaurants must be "
					+ "greater than 0.");
		} else {
			numRestaurants = num;
		}
	}

	/**
	 * Set max capacity of customers in a restuarant.
	 * 
	 * @param num value to set the customer size
	 * @throws InvalidNumberInputException determines if num is less than or
	 *                                     equal to 0, throw exception
	 */
	public void setMaxCustomerSize(int num) throws InvalidNumberInputException {
		if (num <= 0) {
			throw new InvalidNumberInputException(
					"Invalid input. Max number of customers in a restaurant "
					+ "must be greater than 0.");
		} else {
			maxCustomerSize = num;
		}
	}

	/**
	 * Set amount of chefs in simulation.
	 * 
	 * @param num a double to set the arrival probability
	 * @throws InvalidNumberInputException determines if num is less than 0.0
	 *                                     and greater than 1.0, throw exception
	 */
	public void setArrivalProb(double num) throws InvalidNumberInputException {
		if (num < 0.0 || num > 1.0) {
			throw new InvalidNumberInputException(
					"Invalid input. Arrival probability must be in range of "
					+ "[0.0, 1.0]");
		} else {
			arrivalProb = num;
		}
	}

	/**
	 * Set amount of chefs in simulation
	 * 
	 * @param num value to set amount of chefs
	 * @throws InvalidNumberInputException determines if num is less than 1,
	 *                                     throw exception
	 */
	public void setChefs(int num) throws InvalidNumberInputException {
		if (num < 1) {
			throw new InvalidNumberInputException(
					"Input for chefs is invalid. There must be at least 1 chef "
					+ "in the kitchen.");
		} else {
			chefs = num;
		}
	}

	/**
	 * Set duration of the simulation
	 * 
	 * @param num value to set duration
	 * @throws InvalidNumberInputException determines if num is less than 1,
	 *                                     throw exception
	 */
	public void setSimDuration(int num) throws InvalidNumberInputException {
		if (num < 1) {
			throw new InvalidNumberInputException(
					"Input for duration is invalid. Value must be 1 or greater "
					+ "to run the simulation.");
		} else {
			duration = num;
		}
	}

	/**
	 * Creates a customer object based on random number generator.
	 * 
	 * @param timeArrived the time during simulation this function was called.
	 * @return Customer with parameters otherwise return null.
	 */
	public Customer createCustomer(int timeArrived) {
		double prob = Math.random();

		if (prob <= arrivalProb) {
			Customer.customerEntered();
			double foodProb = Math.random();
			String food;
			int timeLeft;
			int foodPrice;
			//Added 15 mins to the time to include time to eat.
			if (foodProb < 0.2) {
				food = "CB"; 	// CHEESEBURGER - 25mins $15
				foodPrice = 15;
				timeLeft = 40;
			} else if (foodProb < 0.4 && foodProb >= 0.2) {
				food = "S"; 	// STEAK - 30mins $25
				foodPrice = 25;
				timeLeft = 45;
			} else if (foodProb < 0.6 && foodProb >= 0.4) {
				food = "GC"; 	// GRILLED CHEESE - 15mins $10
				foodPrice = 10;
				timeLeft = 30;
			} else if (foodProb < 0.8 && foodProb >= 0.6) {
				food = "CT";	 // CHICKEN TENDERS - 25mins $10
				foodPrice = 10;
				timeLeft = 40;
			} else if (foodProb <= 1 && foodProb >= 0.8) {
				food = "CW"; 	// CHICKEN WINGS - 30mins $20
				foodPrice = 20;
				timeLeft = 45;
			} else {
				food = "ERROR";
				foodPrice = 0;
				timeLeft = 999999;
				System.out.println(foodProb);
			}

			int chefTime = (3 - chefs) * 5;

			if (chefTime < -10) {
				chefTime = -10;
			}

			Customer c = new Customer(food, foodPrice, timeArrived,
					timeLeft + chefTime);
			return c;
		}
		return null;
	}

	/**
	 * Determines food type from string that contains an abbreviation.
	 * 
	 * @param food string containing an abbreviation of the food
	 * @return the string phrase of the abbreviation
	 */
	public String foodType(String food) {
		if (food.equals("CB")) {
			return "\"Cheese Burger\"";
		} else if (food.equals("S")) {
			return "\"Steak\"";
		} else if (food.equals("GC")) {
			return "\"Grilled Cheese\"";
		} else if (food.equals("CT")) {
			return "\"Chicken Tenders\"";
		} else if (food.equals("CW")) {
			return "\"Chicken Wings\"";
		} else {
			return "ERROR";
		}

	}

	/**
	 * Runs the simulator that calculates and return the average time the
	 * customer spent at the restaurant.
	 *
	 * @return the double
	 */
	public double simulate() {
		customerLost = 0;
		totalServiceTime = 0;
		customersServed = 0;
		profit = 0;
		restaurantsList = new ArrayList<Restaurant>();

		for (int i = 0; i < numRestaurants; i++) {
			Restaurant restaurant = new Restaurant();
			restaurantsList.add(restaurant);
		}

		int time = 1;
		while (time <= duration) {
			System.out.println("\nTime: " + time);

			for (int i = 0; i < this.numRestaurants; i++) {
				if (!restaurantsList.get(i).isEmpty()) {
					int dinerSize = restaurantsList.get(i).size();
					for (int j = 0; j < dinerSize; j++) {
						Customer customer = restaurantsList.get(i).dequeue();

						if (customer.isFinished()) {
							System.out.println("Customer #"
									+ customer.getOrderNumber()
									+ " has enjoyed their food! $"
									+ customer.getPriceOfFood() + " profit.");
							profit = profit + customer.getPriceOfFood();
							customersServed++;
							totalServiceTime = totalServiceTime
									+ customer.getMaxTime();
						} else {
							restaurantsList.get(i).enqueue(customer);
						}
					}
				}
			}

			String customersEntered = "";
			String customersResponse = "";
			for (int i = 0; i < (3 * numRestaurants); i++) {
				Customer c = createCustomer(time);
				if (c != null) {
					int val = randInt(0, numRestaurants);
					customersEntered = customersEntered + "Customer #"
							+ Customer.getTotalCustomers()
							+ " has entered Restaurant " + (val + 1) + ".\n";

					if (restaurantsList.get(val).size() < maxCustomerSize) {

						restaurantsList.get(val).enqueue(c);
						customersResponse = customersResponse + "Customer #"
								+ Customer.getTotalCustomers()
								+ " has been seated with the order "
								+ foodType(c.getFood()) + ".\n";
					} else {
						customerLost++;
						customersResponse = customersResponse + "Customer #"
								+ Customer.getTotalCustomers()
								+ " cannot be seated! They have left the "
								+ "restaurant.\n";
					}
				}
			}

			System.out.print(customersEntered + customersResponse);

			for (int i = 0; i < numRestaurants; i++) {
				System.out.print("R" + (i + 1) + ": "
						+ restaurantsList.get(i).toString());
			}

			time++;
		}

		if (customersServed == 0) {
			return 0.0;
		} else {
			return (double) totalServiceTime / customersServed;
		}
	}

	/**
	 * This is a helper method that can be used to generate a random number
	 * between minVal and maxVal, inclusively. Returns the randomly generated
	 * number.
	 *
	 * @param minVal the min val
	 * @param maxVal the max val
	 * @return the int
	 */
	private int randInt(int minVal, int maxVal) {
		double randNum = Math.random();
		randNum = randNum * maxVal + minVal;
		return ((int) randNum);
	}

	/**
	 * This is the main method, and prompts the user for the input required to
	 * perform the simulation. The simulator will run and then output the
	 * results. You should prompt the user whether or not another simulation
	 * should be performed.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		DiningSimulator sim = new DiningSimulator();
		System.out.println("Starting simulator...\n");
		while (true) {
			try {
				System.out.print("Enter the number of restaurants: ");
				sim.setNumRestaurants(input.nextInt());
				System.out.print(
						"Enter the maximum number of customers a restaurant "
						+ "can serve: ");
				sim.setMaxCustomerSize(input.nextInt());
				System.out
						.print("Enter the arrival probability of a customer: ");
				sim.setArrivalProb(input.nextDouble());
				System.out.print("Enter the number of chefs: ");
				sim.setChefs(input.nextInt());
				System.out.print("Enter the number of simulation units: ");
				sim.setSimDuration(input.nextInt());

				double avgTime = sim.simulate();

				System.out.println("\nSimulation Ending...\n");
				System.out.println("Total Customer Time: "
						+ sim.totalServiceTime + " minutes");
				System.out.println(
						"Total Customers Served: " + sim.customersServed);
				System.out.println("Average Customer Time Lapse: "
						+ String.format("%.2f", avgTime)
						+ " minutes per order");
				System.out.println("Total Profit: $" + sim.profit);
				System.out.println("Customers that left: " + sim.customerLost);

				System.out.print(
						"\nDo you want to try another simulation? (y/n): ");
				String command = input.next().toUpperCase();
				switch (command) {
				case ("Y"):
					break;
				case ("N"):
					System.out.println("\nProgram terminating normally...");
					return;
				default:
					System.out.println(
							"\nInvalid command. Restarting simulator...\n");
				}

			} catch (InputMismatchException ex) {
				System.out
						.println("Input mismatch. Restarting simulation...\n");
				input.next();

			} catch (InvalidNumberInputException ex) {
				System.out.println("ERROR: " + ex.getMessage()
						+ "\nRestarting simulation...\n");
			}
		}
	}
}
