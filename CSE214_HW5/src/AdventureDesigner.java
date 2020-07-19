import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The <class>AdventureDesigner</class> class will guide the user through making
 * and possibly playing the game. Upon starting the program, the user should
 * immediately be prompted to create the information of the first scene, which
 * will be stored in the root of the tree (see Sample I/O). This class should
 * also act as the main driver for the program, containing the main method and
 * allowing the user to select from menu options.
 * 
 * @author Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *         ID: 111230826
 *
 */
public class AdventureDesigner {

	/**
	 * This method start at the root of the tree and displays the scene. It asks
	 * the user to select an option and prints the scene for that option. This
	 * continues until a leaf node has been displayed, at which point the
	 * adventure is concluded and the program returns to the main method.
	 */
	public static void playGame(SceneTree story) {
		Scanner input = new Scanner(System.in);

		SceneNode originalCursor = story.getCursor();

		story.setCursor(story.getRoot());

		story.getRoot().displayScene();
		while (!story.getCursor().isEnding()) {
			boolean isWrongOption = true;
			while (isWrongOption) {
				System.out.print("Please enter an option: ");
				String option = input.next().toUpperCase();
				try {
					story.moveCursorForwards(option);
					isWrongOption = false;
				} catch (NoSuchNodeException e) {
					System.out.println("Option does not exist. Try Again.\n");
					input.nextLine();
				}
			}
			System.out.println("");
			story.getCursor().displayScene();
		}
		story.setCursor(originalCursor);
	}

	/**
	 * The main driver for the program, which should initialize a SceneTree
	 * object and guide users through the menu to help them create their own
	 * adventures.
	 *
	 * @param arg the arguments
	 */
	public static void main(String arg[]) {
		Scanner input = new Scanner(System.in);
		System.out.println("Creating a story...\n");
		SceneTree story = new SceneTree();
		String title = "";
		String description = "";
		try {
			System.out.print("Please enter a title: ");
			title = input.nextLine();
			System.out.print("Please enter a scene: ");
			description = input.nextLine();
			story.addNewNode(title, description);
			System.out.println(
					"\nScene #" + (SceneNode.getNumScenes()) + " added.");
			SceneNode.incrementNumScenes();
		} catch (FullSceneException e1) {
			e1.printStackTrace();
		} catch (InputMismatchException e) {
			System.out.println("Input Mismatch. You broke something somehow..."
					+ " Ending Program.");
			return;
		}

		while (true) {
			System.out.println("\n(A) Add Scene \n" + "(R) Remove Scene \n"
					+ "(S) Show Current Scene \n"
					+ "(P) Print Adventure Tree \n" + "(B) Go Back A Scene \n"
					+ "(F) Go Forward A Scene \n" + "(G) Play Game \n"
					+ "(N) Print Path To Cursor \n" + "(M) Move Scene \n"
					+ "(Q) Quit \n");
			System.out.print("Please enter a selection: ");
			String command = input.next().toUpperCase();
			input.nextLine();
			System.out.println("");
			switch (command) {
			case ("A"):
				try {
					System.out.print("Please enter a title: ");
					title = input.nextLine();
					System.out.print("Please enter a scene: ");
					description = input.nextLine();
					story.addNewNode(title, description);
					System.out.println("\nScene #" + (SceneNode.getNumScenes())
							+ " added.");
					SceneNode.incrementNumScenes();
				} catch (InputMismatchException ex) {
					System.out.println("Input mismatch.");
				} catch (FullSceneException e) {
					System.out.println("\n" + e.getMessage());
				}

				break;
			case ("R"):
				try {
					System.out.print("Please enter an option (A, B, or C): ");
					String option = input.next().toUpperCase();
					story.removeScene(option);
					System.out.println("");
				} catch (InputMismatchException ex) {
					System.out.println("Input mismatch. Scene not removed.");
				} catch (NoSuchNodeException e) {
					System.out.println("\n" + e.getMessage());
				}

				break;
			case ("S"):
				story.getCursor().displayFullScene();
				break;
			case ("P"):
				System.out.println(story.toString());
				break;
			case ("B"):
				try {
					story.moveCursorBackwards();
					System.out.println("Succesfully moved back to '"
							+ story.getCursor().getSceneTitle() + "' scene.");
				} catch (NoSuchNodeException e) {
					System.out.println("\n" + e.getMessage());
				}
				break;

			case ("F"):
				try {
					System.out.print("Please enter an option (A, B, or C): ");
					String option = input.next().toUpperCase();
					story.moveCursorForwards(option);
					System.out.println("Succesfully moved to '"
							+ story.getCursor().getSceneTitle() + "' scene.");
				} catch (InputMismatchException ex) {
					System.out.println(
							"Input mismatch. Scene did not move forward.");
				} catch (NoSuchNodeException e) {
					System.out.println("\n" + e.getMessage());
				}

				break;
			case ("G"):
				System.out.println("Now beginning game...\n");
				playGame(story);
				System.out.println("Returning back to creation mode...\n");
				break;

			case ("N"):
				System.out.println("PATH: " + story.getPathFromRoot());
				break;
			case ("M"):
				try {
					System.out.print("Move currrent scene to: ");
					int sceneIDToMoveTo = input.nextInt();
					System.out.println("");
					story.moveScene(sceneIDToMoveTo);
				} catch (NoSuchNodeException e) {
					System.out.println("\n\n" + e.getMessage());
				} catch (FullSceneException e) {
					System.out.println("\n\n" + e.getMessage());
				} catch (InputMismatchException e) {
					System.out.println(
							"\n\nInput Mismatch. Scene was not moved.");
				}
				break;

			case ("Q"):
				System.out.println("Program terminating normally...");
				return;
			default:
				System.out.print("The command does not exist. Try again. \n");
			}
		}
	}
}
