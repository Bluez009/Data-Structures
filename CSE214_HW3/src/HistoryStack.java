import java.util.EmptyStackException;
import java.util.Stack;

// TODO: Auto-generated Javadoc
/**
 * The <class>HistoryStack</class> contains the user's history of equations and
 * will be similar to the EquationStack class, with some extra functionality.
 * 
 * @author Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *         ID: 111230826
 *
 */
public class HistoryStack extends Stack {

	private Stack backup = new Stack<Equation>();

	/**
	 * Instantiates a new history stack.
	 */
	HistoryStack() {
	}

	/**
	 * Adds newEquation to the top of the stack.
	 *
	 * @param newEquation the new equation
	 */
	public void push(Equation newEquation) {
		super.push(newEquation);
	}

	/**
	 * Removes and returns the Equation at the top of the stack.
	 *
	 * @return the equation
	 */
	public Equation pop() {
		return (Equation) super.pop();
	}

	/**
	 * Removes the last Equation from the top of the stack, preserving it for
	 * later.
	 * 
	 * @throws EmptyHistoryStackException if stack is empty throw exception
	 */
	public void undo() throws EmptyHistoryStackException {
		if (empty()) {
			throw new EmptyHistoryStackException("There is nothing to undo.");
		} else {
			Equation tmp = pop();
			System.out.println("Equation '" + tmp.getEquation() + "' undone.");
			backup.push(tmp);
		}

	}

	/**
	 * Replaces the last undone Equation back onto the stack. If there is no
	 * last undone Equation, throw an appropriate exception.
	 * 
	 * @throws EmptyHistoryStackException if backup stack is empty throw
	 *                                    exception
	 */
	public void redo() throws EmptyHistoryStackException {
		if (backup.empty()) {
			throw new EmptyHistoryStackException("There is nothing to redo.");
		} else {
			Equation tmp = (Equation) backup.pop();
			System.out.println("Redoing equation '" + tmp.getEquation() + "'.");
			push(tmp);
		}
	}

	/**
	 * Searches through this HistoryStack and returns the Equation located at
	 * the specified position. If the position is out of range or otherwise
	 * invalid, throw an appropriate exception.
	 *
	 * @param position the position in the stack
	 * @throws InvalidPositionException if position is out of range throw
	 * @return the equation found
	 */
	public Equation getEquation(int position) throws InvalidPositionException {
		if (empty() || position <= 0) {
			throw new InvalidPositionException(
					"Invalid position. No equation was found.");
		}
		
		Stack temp = new Stack<Equation>();
		
		Equation tempEQ = new Equation();
		String tempEquation = "";
		boolean found = false;
		int count = 1;
		while (!empty()) {
			temp.push(this.pop());
		}
		
		while (!temp.empty()) {
			if(count == position) {
				tempEquation = ((Equation) temp.peek()).getEquation();
				tempEQ.setEquation(tempEquation);
				found = true;
			}
			count++;
			this.push(temp.pop());
		}
		
		if(!found) {
			throw new InvalidPositionException(
					"Invalid position. No equation was found.");
		}
		
		return tempEQ;
	}


	/**
	 * Returns a neatly formatted table of this HistoryStack.
	 *
	 * @return the string
	 */
	public String toString() {
		Stack temp = new Stack<Equation>();
		String output = "";
		System.out.println(String.format("%-4s", "#") + String.format(
				"%-35s%-35s%-30s%15s%20s  %10s", "Equation", "Pre-Fix",
				"Post-Fix", "Answer", "Binary", "Hexadecimal"));
		System.out.println(
				"-------------------------------------------------------------"
				+ "-----------------------------------------------------------"
				+ "---------------------------------");
		while (!empty()) {
			int pos = size();
			output += String.format("%-4d", pos) + peek().toString() + "\n";
			temp.push(pop());
			pos--;
		}
		while (!temp.empty()) {
			this.push(temp.pop());
		}
		return output;
	}

}
