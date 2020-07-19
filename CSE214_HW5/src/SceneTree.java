/**
 * The <class>SceneTree</class> represents a collection of SceneNode objects
 * arranged as a ternary tree. It has references to the root of the tree, as
 * well as a cursor which can be used to navigate the tree (NOTE: The root of
 * this tree should never be removed!). This class also contains methods which
 * can be used to add and remove SceneNodes, move the cursor, update SceneNodes,
 * and print them.
 * 
 * @author Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *         ID: 111230826
 */
public class SceneTree {

	/** The root. */
	private SceneNode root;

	/** The cursor. */
	private SceneNode cursor;

	/**
	 * Instantiates a new scene tree.
	 */
	public SceneTree() {
		root = null;
		cursor = null;
	}

	/**
	 * Gets the root.
	 *
	 * @return the root
	 */
	public SceneNode getRoot() {
		return root;
	}

	/**
	 * Sets the cursor.
	 *
	 * @param scenenode the new cursor
	 */
	public void setCursor(SceneNode scenenode) {
		cursor = scenenode;
	}

	/**
	 * Gets the cursor.
	 *
	 * @return the cursor
	 */
	public SceneNode getCursor() {
		return cursor;
	}

	/**
	 * Moves the cursor to the parent node.
	 *
	 * @throws NoSuchNodeException if the current node does not have a parent.
	 */

	public void moveCursorBackwards() throws NoSuchNodeException {
		if (cursor == root) {
			throw new NoSuchNodeException(
					"Cursor did not move, your currently at the root.");
		}
		int[] treePath = new int[90];
		int sceneNum = cursor.getSceneID();
		cursor = root;
		if (traverseTreeByID(root, sceneNum, treePath, 0)) {
			try {
				for (int i = 1; i < treePath.length; i++) {
					if (cursor.getLeft().getSceneID() == treePath[i]
							&& treePath[i + 1] != 0) {
						moveCursorForwards("A");
					} else if (cursor.getMiddle().getSceneID() == treePath[i]
							&& treePath[i + 1] != 0) {
						moveCursorForwards("B");
					} else if (cursor.getRight().getSceneID() == treePath[i]
							&& treePath[i + 1] != 0) {
						moveCursorForwards("C");
					} else {
						break;
					}
				}
			} catch (NullPointerException e) {

			}
		}
	}

	/**
	 * Move the cursor to the appropriate child node.
	 *
	 * @param option A String ("A", "B", or "C") dictating whether we should
	 *               select the left, middle, or right child, respectively.
	 * @throws NoSuchNodeException if the current node does not have any such
	 *                             child.
	 */
	public void moveCursorForwards(String option) throws NoSuchNodeException {
		if (option.equals("A") && cursor.getLeft() != null) {
			cursor = cursor.getLeft();

		} else if (option.equals("B") && cursor.getMiddle() != null) {
			cursor = cursor.getMiddle();

		} else if (option.equals("C") && cursor.getRight() != null) {
			cursor = cursor.getRight();

		} else {
			throw new NoSuchNodeException(
					"Cursor did not move, there is no scene to be found.");
		}
	}

	/**
	 * Creates a new SceneNode object as a child of the current node with the
	 * supplied values. It will be set in the first empty left, middle, or right
	 * node.
	 *
	 * @param title            the title of the scene
	 * @param sceneDescription the scene description
	 * @throws FullSceneException if the current node does not have any
	 *                            available child positions.
	 */
	public void addNewNode(String title, String sceneDescription)
			throws FullSceneException {
		if (root == null) {
			root = new SceneNode(title, sceneDescription);
			cursor = root;
		} else {
			SceneNode newScene = new SceneNode(title, sceneDescription);
			cursor.addScene(newScene);
		}
	}

	/**
	 * Removes the specified child node from the tree.
	 *
	 * @param option A String ("A", "B", or "C") dictating whether we should
	 *               remove the left, middle, or right child, respectively.
	 * 
	 * @throws NoSuchNodeException if the current node does not have any such
	 *                             child.
	 */
	public void removeScene(String option) throws NoSuchNodeException {
		if (option.equals("A") && cursor.getLeft() != null) {
			System.out.println(
					"'" + cursor.getLeft().getSceneTitle() + "' removed.");
			cursor.setLeft(cursor.getMiddle());
			cursor.setMiddle(cursor.getRight());
			cursor.setRight(null);
		} else if (option.equals("B") && cursor.getMiddle() != null) {
			System.out.println(
					"'" + cursor.getMiddle().getSceneTitle() + "' removed.");
			cursor.setMiddle(cursor.getRight());
			cursor.setRight(null);
		} else if (option.equals("C") && cursor.getRight() != null) {
			System.out.println(
					"'" + cursor.getRight().getSceneTitle() + "' removed.");
			cursor.setRight(null);
		} else {
			throw new NoSuchNodeException("There is no scene to remove.");
		}
	}

	/**
	 * Moves the node at cursor to be a child of the node with the specified ID.
	 *
	 * @param sceneIDToMoveTo An integer which specifies which node this
	 *                        SceneNode object should be moved under.
	 * @throws NoSuchNodeException if there does not exist a SceneNode with the
	 *                             supplied Scene ID.
	 * @throws FullSceneException  if the SceneNode specified by the supplied
	 *                             Scene ID does not have any available child
	 *                             positions.
	 */
	public void moveScene(int sceneIDToMoveTo)
			throws NoSuchNodeException, FullSceneException {
		if (cursor == root) {
			throw new NoSuchNodeException(
					"Unable to move scene as it is the root.");
		}
		int[] treePath = new int[90];
		SceneNode tmpCursor = cursor;
		cursor = root;
		if (traverseTreeByID(root, sceneIDToMoveTo, treePath, 0)) {
			for (int i = 1; i < treePath.length; i++) {
				if (cursor.getSceneID() == sceneIDToMoveTo) {
					break;
				} else if (cursor.getLeft().getSceneID() == treePath[i]) {
					moveCursorForwards("A");
				} else if (cursor.getMiddle().getSceneID() == treePath[i]) {
					moveCursorForwards("B");
				} else if (cursor.getRight().getSceneID() == treePath[i]) {
					moveCursorForwards("C");
				} else {
					System.out.println("Something BROKE IN LOOP");
					return;
				}
			}
			if (cursor.isFull()) {
				cursor = tmpCursor;
				throw new FullSceneException(
						"There is no more room to move a scene to the selected sceneID node.");
			}
			SceneNode destination = cursor;
			int originalCursorID = tmpCursor.getSceneID();
			cursor = tmpCursor;
			moveCursorBackwards();
			if (cursor.getLeft().getSceneID() == originalCursorID) {
				removeScene("A");
			} else if (cursor.getMiddle().getSceneID() == originalCursorID) {
				removeScene("B");
			} else if (cursor.getRight().getSceneID() == originalCursorID) {
				removeScene("C");
			} else {
				System.out.println("Something broke... in move Scene...");
				return;
			}
			destination.addScene(tmpCursor);
			System.out
					.println("Successfully moved scene '" + tmpCursor.toString()
							+ "' to '" + destination.toString() + "'.");
			cursor = tmpCursor;
		} else {
			cursor = tmpCursor;
			throw new NoSuchNodeException(
					"Unable to find specified scene ID in tree, the node was not moved.");
		}

	}

	/**
	 * Helper method used to traverse the tree recursively to locate a specific
	 * node using the sceneID and storing the path into an int array path ID.
	 * 
	 * @param root    root of the tree
	 * @param sceneID scene of the ID to find
	 * @param pathID  array to store the path
	 * @param index   the index of the pathID array to store the scene ID
	 * @return true if there is a path found, false otherwise.
	 */
	private boolean traverseTreeByID(SceneNode root, int sceneID, int[] pathID,
			int index) {
		if (root == null) {
			return false;
		}
		pathID[index] = root.getSceneID();

		if (root.getSceneID() == sceneID) {
			return true;
		}
		if (traverseTreeByID(root.getLeft(), sceneID, pathID, index + 1)
				|| traverseTreeByID(root.getMiddle(), sceneID, pathID,
						index + 1)
				|| traverseTreeByID(root.getRight(), sceneID, pathID,
						index + 1)) {
			return true;
		} else {
			pathID[index] = 0;
			return false;
		}
	}

	/**
	 * Helper method used in getPathToRoot() method to traverse the tree
	 * recursively to locate the path of the cursor using the title of each
	 * scene and storing the path into a string array titlePath.
	 * 
	 * @param root      root of the tree
	 * @param cursor    scene of the ID to find
	 * @param titlePath array to store the path
	 * @param index     the index of the titlePath array to store the title of
	 *                  each scene
	 * @return true if there is a path found, false otherwise
	 */
	private boolean traverseTreeByTitle(SceneNode root, SceneNode cursor,
			String[] titlePath, int index) {
		if (root == null || cursor == null) {
			return false;
		}
		titlePath[index] = root.getSceneTitle();

		if (root.getSceneID() == cursor.getSceneID()) {
			return true;
		}
		if (traverseTreeByTitle(root.getLeft(), cursor, titlePath, index + 1)
				|| traverseTreeByTitle(root.getMiddle(), cursor, titlePath,
						index + 1)
				|| traverseTreeByTitle(root.getRight(), cursor, titlePath,
						index + 1)) {
			return true;
		} else {
			titlePath[index] = null;
			return false;
		}
	}

	/**
	 * Constructs a String showing the path from the root of the tree to the
	 * currently selected SceneNode, illustrating the decisions which would have
	 * to be made to arrive at the selected node (option N).
	 *
	 * @return A String representing the path between the root of the tree and
	 *         the currently selected SceneNode.
	 */
	public String getPathFromRoot() {
		String[] arr = new String[70];
		String str = null;
		if (traverseTreeByTitle(root, cursor, arr, 0)) {
			str = arr[0];
			int counter = 1;
			while (arr[counter] != null) {
				str += ", " + arr[counter];
				counter++;
			}
		}
		return str;
	}

	/**
	 * Helper method that is used in toString() method. Recursively stores all
	 * possible paths in this tree into a string.
	 * 
	 * @param root      root of the tree
	 * @param cursor    cursor of the tree
	 * @param tabs      indents corresponding to the child of the parent node
	 * @param choice    the path of each child of the parent node
	 * @param treePaths the string containing all possible paths in the tree
	 * @return the string representation of all tree paths
	 */
	private String printPreOrder(SceneNode root, SceneNode cursor, String tabs,
			String choice, String treePaths) {
		if (root.getSceneID() == cursor.getSceneID()) {
			treePaths += tabs + choice + root.toString() + " <---\n";
		} else {
			treePaths += tabs + choice + root.toString() + "\n";
		}

		if (root.getLeft() != null) {
			treePaths += printPreOrder(root.getLeft(), cursor, tabs + "   ",
					"A) ", "");
		}
		if (root.getMiddle() != null) {
			treePaths += printPreOrder(root.getMiddle(), cursor, tabs + "   ",
					"B) ", "");
		}
		if (root.getRight() != null) {
			treePaths += printPreOrder(root.getRight(), cursor, tabs + "   ",
					"C) ", "");
		}
		return treePaths;
	}

	/**
	 * Constructs a String representation of the tree.
	 *
	 * @return A String representation of the tree.
	 */
	public String toString() {
		return printPreOrder(root, cursor, "", "", "");
	}

}
