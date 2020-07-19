import java.util.Stack;
/**
 * The <class>EquationStack</class> will be used to evaluate an Equation to get
 * the answer. This class is extended using the Java Stack API. It can keep
 * track of the different types of stacks using a variable, depending on 
 * whether the stack is to store operators or operands.
 * 
 * @author Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *         ID: 111230826
 */
public class EquationStack extends Stack<String>{
	
	private int size = 0;
	/**
	 * Instantiates a new equation stack.
	 */
	EquationStack() {
		size = 0;
	}
	
	/**
	 * Pushes str (a new operator or operand) to the top of the stack.
	 *
	 * @param str 
	 * 	the operator/operand to put into stack
	 */
	public String push(String str) {
		size++;
		return super.push(str);
	}
	
	public String peek() {
		return (super.peek());
	}
	
	/**
	 * Removes the operator or operand stored at the top of the stack.
	 *
	 * @return 
	 * 	the string of the top of the stack
	 */
	public String pop() {
		size--;
		return (super.pop());
	}
	
	
	/**
	 * Checks if stack is empty.
	 *
	 * @return 
	 * 	true, if is empty, else false
	 */
	public boolean isEmpty() {
		return (super.empty());
	}
	
}
