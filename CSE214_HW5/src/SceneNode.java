/**
 * The <class>SceneNode</class> represents a specific scene. A scene has a title
 * (String), a sceneDescription (String), a sceneID (int), and up to three
 * possible child SceneNode references (called left, middle, and right)
 * 
 * @author Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *         ID: 111230826
 *
 */
public class SceneNode {

	/** The title. */
	private String title;

	/** The scene description. */
	private String sceneDescription;

	/** The scene ID. */
	private int sceneID;

	/** The left. */
	private SceneNode left;

	/** The middle. */
	private SceneNode middle;

	/** The right. */
	private SceneNode right;

	/** The num scenes. */
	private static int numScenes = 1;

	/**
	 * Instantiates a new scene node.
	 */
	public SceneNode() {

	}

	/**
	 * Instantiates a new scene node.
	 *
	 * @param title            the title of scene
	 * @param sceneDescription the scene description
	 */
	public SceneNode(String title, String sceneDescription) {
		this.title = title;
		this.sceneDescription = sceneDescription;
		left = null;
		middle = null;
		right = null;
		sceneID = numScenes;
	}

	/**
	 * Gets the left.
	 *
	 * @return the left
	 */
	public SceneNode getLeft() {
		return left;
	}

	/**
	 * Gets the middle.
	 *
	 * @return the middle
	 */
	public SceneNode getMiddle() {
		return middle;
	}

	/**
	 * Gets the right.
	 *
	 * @return the right
	 */
	public SceneNode getRight() {
		return right;
	}

	/**
	 * Sets the left.
	 *
	 * @param scene the new left
	 */
	public void setLeft(SceneNode scene) {
		left = scene;
	}

	/**
	 * Sets the middle.
	 *
	 * @param scene the new middle
	 */
	public void setMiddle(SceneNode scene) {
		middle = scene;
	}

	/**
	 * Sets the right.
	 *
	 * @param scene the new right
	 */
	public void setRight(SceneNode scene) {
		right = scene;
	}

	/**
	 * Gets the scene title.
	 *
	 * @return the scene title
	 */
	public String getSceneTitle() {
		return title;
	}

	/**
	 * Gets the scene ID.
	 *
	 * @return the scene ID
	 */
	public int getSceneID() {
		return sceneID;
	}

	/**
	 * Gets the number of scenes.
	 *
	 * @return the number of scenes
	 */
	public static int getNumScenes() {
		return numScenes;
	}

	/**
	 * Increment number of scenes
	 */
	public static void incrementNumScenes() {
		numScenes = numScenes + 1;
	}

	/**
	 * Sets the scene to the first available slot in the child nodes. Children
	 * should be added in the left-most node.
	 *
	 * @param scene the scene
	 * 
	 * @throws FullSceneException if the current node does not have any empty
	 *                            child nodes.
	 */
	public void addScene(SceneNode scene) throws FullSceneException {
		if (left == null) {
			left = scene;
		} else if (middle == null) {
			middle = scene;
		} else if (right == null) {
			right = scene;
		} else {
			throw new FullSceneException(
					"There is no more room to add new scene to this node.");
		}
	}

	/**
	 * Determines if this scene is an ending for a storyline. If this node has
	 * no children (a leaf), then the user is deemed to have reached the end of
	 * that particular story path.
	 *
	 * @return true if this scene is the ending of a storyline, false otherwise.
	 */
	public boolean isEnding() {
		if (left == null && middle == null && right == null) {
			return true;
		}
		return false;
	}

	/**
	 * Determines if this scene has the max amount of choices. If each child
	 * node is not null, then the node is full.
	 *
	 * @return true if this scene is the full false otherwise.
	 */
	public boolean isFull() {
		if (left != null && middle != null && right != null) {
			return true;
		}
		return false;
	}

	/**
	 * Outputs the scene information, as would be shown during gameplay (option
	 * G).
	 */
	public void displayScene() {
		System.out.println(title + "\n" + sceneDescription + "\n");

		if (isEnding()) {
			System.out.println("The End\n");
		}

		else if (middle == null) {
			System.out.println("A) " + left.getSceneTitle() + "\n");
		} else if (right == null) {
			System.out.println("A) " + left.getSceneTitle() + "\nB) "
					+ middle.getSceneTitle() + "\n");
		} else {
			System.out.println("A) " + left.getSceneTitle() + "\nB) "
					+ middle.getSceneTitle() + "\nC) " + right.getSceneTitle()
					+ "\n");
		}
	}

	/**
	 * Outputs all information about a scene, as would be shown in creation mode
	 * (option S).
	 */
	public void displayFullScene() {
		String sceneStr = "Scene ID #" + sceneID + "\nTitle: " + title
				+ "\nScene: " + sceneDescription + "\nLeads to: ";
		if (isEnding()) {
			System.out.println(sceneStr += "NONE");
		}

		else if (middle == null) {
			sceneStr = sceneStr + "'" + left.getSceneTitle() + "' (#"
					+ left.getSceneID() + ")";
			System.out.println(sceneStr);
		}

		else if (right == null) {
			sceneStr = sceneStr + "'" + left.getSceneTitle() + "' (#"
					+ left.getSceneID() + "), " + "'" + middle.getSceneTitle()
					+ "' (#" + middle.getSceneID() + ") ";
			System.out.println(sceneStr);
		} else {
			sceneStr = sceneStr + "'" + left.getSceneTitle() + "' (#"
					+ left.getSceneID() + "), " + "'" + middle.getSceneTitle()
					+ "' (#" + middle.getSceneID() + "), " + "'"
					+ right.getSceneTitle() + "' (#" + right.getSceneID() + ")";
			System.out.println(sceneStr);
		}
	}

	/**
	 * A String representing a brief summary of this node, as would be shown in
	 * the tree (option P).
	 *
	 * @return the string
	 */
	public String toString() {
		return title + " (#" + sceneID + ")";
	}

}
