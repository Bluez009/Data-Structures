import java.util.EmptyStackException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The <class>Calculator</class> class will represent the company's new 
 * calculator. This class will contain the main method that acts as a driver 
 * for the program that the user will interact with.
 * 
 * @author Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *         ID: 111230826
 *
 */
public class Calculator {

	/**
	 * A driver for the program that the user will interact with.
	 */
	public static void main(String arg[]) {
		Scanner input = new Scanner(System.in);
		HistoryStack history = new HistoryStack();
		System.out.println("Welcome to calculat0r");
		boolean go = false;
		while (true) {
			System.out.print("\n[A] Add new equation\r\n"
					+ "[F] Change equation from history\r\n"
					+ "[B] Print previous equation\r\n"
					+ "[P] Print full history\r\n" + "[U] Undo\r\n"
					+ "[R] Redo\r\n" + "[C] Clear history\r\n"
					+ "[Q] Quit \n\n");

			System.out.print("Select an option: ");
			String command = input.next().toUpperCase();
			System.out.println("");
			switch (command) {
			case ("A"):
				Equation eq = null;
				try {
					input.nextLine();
					System.out.print(
							"Please enter an equation (in-fix notation): ");
					String equation = input.nextLine();
					if (equation.length() == 0) {
						System.out
								.println("Input was empty. Nothing was added.");
						break;
					}
					eq = new Equation(equation);
					eq.infixToPostfix(eq.getEquation(), true);
					eq.infixToPrefix(eq.getEquation());
					eq.decToBin((int) (Math.round(eq.getAnswer())));
					eq.decToHex((int) (Math.round(eq.getAnswer())));
					System.out.println(
							"The equation is balanced and the answer is "
									+ String.format("%.3f", eq.getAnswer()));
					history.push(eq);

				} catch (InputMismatchException ex) {
					System.out.println("Input mismatch. Added anyway.\n\n");
					eq.setPostFix("N/A");
					eq.setPrefix("N/A");
					history.push(eq);

				} catch (NotBalancedEquationException ex) {
					System.out.println(ex.getMessage() + " Added to stack. \n");
					eq.setPostFix("N/A");
					eq.setPrefix("N/A");
					history.push(eq);
				} catch (EmptyStackException ex) {
					System.out.println(
							"Equation is not balanced. Added to stack. \n");
					eq.setPostFix("N/A");
					eq.setPrefix("N/A");
					history.push(eq);
				} catch (CharacterFoundException ex) {
					System.out.println(ex.getMessage());
					eq.setPostFix("N/A");
					eq.setPrefix("N/A");
					history.push(eq);
				}
				break;

			case ("F"):
				Equation tempEq = new Equation();
				try {
					boolean confirmation = true;
					System.out
							.print("Which equation would you like to change? ");
					int eqPos = input.nextInt();

					tempEq = history.getEquation(eqPos);

					if (tempEq != null) {
						String equationTMP = tempEq.getEquation();
						System.out.println("\nEquation at position " + eqPos
								+ ": " + equationTMP);
						input.nextLine();
						while (confirmation) {
							System.out.print(
									"What would you like to do to the equation "
									+ "(Replace / remove / add)? ");

							String instruction = input.nextLine();

							instruction = instruction.trim().toUpperCase();
							StringBuilder newSBEq = new StringBuilder(
									equationTMP);
							if (instruction.equals("REPLACE")) {
								System.out.print(
										"What position would you like to "
										+ "change? ");
								int pos1 = input.nextInt();
								System.out.print(
										"What would you like to replace it "
										+ "with? ");
								input.nextLine();
								String val = input.nextLine();
								newSBEq.replace(pos1 - 1, pos1, val);
								equationTMP = newSBEq.toString();
							} else if (instruction.equals("REMOVE")) {
								System.out.print(
										"What position would you like to "
										+ "remove? ");
								int pos1 = input.nextInt();
								pos1 = pos1 - 1;
								newSBEq.deleteCharAt(pos1);
								equationTMP = newSBEq.toString();
								input.nextLine();
							} else if (instruction.equals("ADD")) {
								System.out.print(
										"What position would you like to add "
										+ "something? ");
								int pos1 = input.nextInt();
								System.out
										.print("What would you like to add? ");
								input.nextLine();
								String val = input.nextLine();
								newSBEq.insert(pos1 - 1, val);
								equationTMP = newSBEq.toString();
							} else {
								System.out.println(
										"Incorrect command. The equation was "
										+ "not added/altered to stack.");
								break;
							}

							System.out.println("\nEquation: " + equationTMP);
							System.out.print(
									"Would you like to make any more "
									+ "changes? ");
							String command2 = input.nextLine().toUpperCase();
							switch (command2) {
							case ("Y"):
								break;
							case ("YES"):
								break;
							case ("N"):
								confirmation = false;
								tempEq.setEquation(equationTMP);
								tempEq.infixToPostfix(tempEq.getEquation(),
										true);
								tempEq.infixToPrefix(tempEq.getEquation());
								tempEq.decToBin(
										(int) (Math.round(tempEq.getAnswer())));
								tempEq.decToHex(
										(int) (Math.round(tempEq.getAnswer())));

								System.out.println(
										"The equation is balanced and the "
										+ "answer is " + String.format("%.3f",
														tempEq.getAnswer()));
								history.push(tempEq);
								break;
							case ("NO"):
								confirmation = false;
								tempEq.setEquation(equationTMP);
								tempEq.infixToPostfix(tempEq.getEquation(),
										true);
								tempEq.infixToPrefix(tempEq.getEquation());

								tempEq.decToBin(
										(int) (Math.round(tempEq.getAnswer())));
								tempEq.decToHex(
										(int) (Math.round(tempEq.getAnswer())));

								System.out.println(
										"The equation is balanced and the "
										+ "answer is " + String.format("%.3f",
														tempEq.getAnswer()));
								history.push(tempEq);
								break;
							default:
								System.out.println(
										"Wrong input. Equation not added to "
										+ "stack nor changed.");
								confirmation = false;
								break;
							}
						}

					} else {
						System.out.println(
								"Something wrong happened. No equation was "
								+ "found.");
					}
				} catch (InvalidPositionException e) {
					System.out.println(e.getMessage() + "\n");
				} catch (InputMismatchException e) {
					System.out.println(
							"Input Mismatch. Failed to retrieve equation. "
							+ "Equation not added to stack.");
					break;
				} catch (NotBalancedEquationException e) {
					System.out.println(e.getMessage() + "\n");
					tempEq.setPostFix("N/A");
					tempEq.setPrefix("N/A");
					history.push(tempEq);
				} catch (CharacterFoundException e) {
					System.out.println(e.getMessage() + "\n");
					tempEq.setPostFix("N/A");
					tempEq.setPrefix("N/A");
					history.push(tempEq);
				} catch (StringIndexOutOfBoundsException e) {
					System.out.println("Position was out of Range. Equation"
							+ " was not added/altered to stack.");
				}

				break;
			case ("B"):
				if (history.empty()) {
					System.out.println("There is no equation, stack is empty.");
				} else {
					System.out.println(String.format("%-4s", "#")
							+ String.format("%-35s%-35s%-30s%15s%20s  %10s",
									"Equation", "Pre-Fix", "Post-Fix", "Answer",
									"Binary", "Hexadecimal"));
					System.out.println(
							"-------------------------------------------------"
							+ "----------------------------------------------"
							+ "-----------------------------------------------"
							+ "-----------");
					System.out.println(String.format("%-4d", history.size())
							+ history.peek().toString());
				}

				break;
			case ("P"):
				System.out.println(history.toString());
				break;
			case ("U"):
				try {
					history.undo();
				} catch (EmptyHistoryStackException e) {
					System.out.println(e.getMessage());
				}
				break;
			case ("R"):
				try {
					history.redo();
				} catch (EmptyHistoryStackException e) {
					System.out.println(e.getMessage());
				}
				break;
			case ("C"):
				history = new HistoryStack();
				System.out.println("Resetting calculator.");
				break;
			case ("Q"):
				System.out.println("Program terminating normally...");
				return;
			default:
				System.out.println("Wrong Input. Try again.");
			}

		}

	}
}
