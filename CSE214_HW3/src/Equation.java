
/**
 * The <class> Equation </class> class will store the actual equation as a
 * String (in in-fix notation), pre- and post-fix notations as separate Strings,
 * the answer in decimal (base 10) as a double, the answer in binary (base 2) as
 * a String, the answer in hex (base 16) as a String, and a boolean balanced
 * flag to denote whether the equation is balanced or not.
 * 
 * @author Erik Bracamonte e-mail: erik.bracamonte@stonybrook.edu Stony Brook
 *         ID: 111230826
 *
 */
public class Equation {
	private String equation;
	private String prefix;
	private String postfix;
	private double answer;
	private String binary = "0";
	private String hex = "0";
	private boolean balanced;

	/**
	 * Instantiates a new equation.
	 */
	Equation() {
		equation = "";
		prefix = "";
		postfix = "";
		answer = 0.0;
		binary = "0";
		hex = "0";
		balanced = false;
	}

	/**
	 * Instantiates a new equation.
	 */
	Equation(String equation) {
		this.equation = equation;
		prefix = "";
		postfix = "";
		answer = 0.0;
		binary = "0";
		hex = "0";
		balanced = false;
	}

	/**
	 * Return the string equation
	 * 
	 * @return equation
	 */
	public String getEquation() {
		return equation;
	}

	/**
	 * Set string prefix
	 * 
	 * @param postfix string to set prefix
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * Set string postfix
	 * 
	 * @param postfix string to set postfix
	 */
	public void setPostFix(String postfix) {
		this.postfix = postfix;
	}

	/**
	 * Return the double answer
	 * 
	 * @return answer
	 */
	public double getAnswer() {
		return answer;
	}

	/**
	 * Set the equation string
	 * 
	 * @param eq string to replace equation
	 */
	public void setEquation(String eq) {
		equation = eq;
	}

	/**
	 * Used during evaluation of numbers when using stacks to help calculate
	 * answer.
	 * 
	 * @param operator the operator to do the operation
	 * @param op1      the operand string to convert to number
	 * @param op2      the second operand string to convert to number
	 * @return calculated answer as a string
	 */
	public String solveOperation(String operator, String op1, String op2) {
		double value = 0;
		switch (operator.charAt(0)) {
		case '+':
			value = Double.parseDouble(op2) + Double.parseDouble(op1);
			break;
		case '-':
			value = Double.parseDouble(op2) - Double.parseDouble(op1);
			break;
		case '*':
			value = Double.parseDouble(op2) * Double.parseDouble(op1);
			break;
		case '/':
			value = Double.parseDouble(op2) / Double.parseDouble(op1);
			break;
		case '^':
			value = Math.pow(Double.parseDouble(op2), Double.parseDouble(op1));
			break;
		case '%':
			value = Double.parseDouble(op2) % Double.parseDouble(op1);
			break;
		default:
			value = 0;
		}
		return Double.toString(value);
	}

	/**
	 * Convert infix to Prefix notation by flipping infix string, using
	 * infixToPostFix method and then flipping the string back.
	 * 
	 * @param infix String to convert to prefix
	 * @throws CharacterFoundException checks if character representing a letter
	 *                                 is found
	 * @return prefix string                               
	 */
	public String infixToPrefix(String infix) throws CharacterFoundException {
		if (!isBalanced()) {
			return prefix = "N/A";
		}
		if (postfix.equals("N/A")) {
			return prefix = "N/A";
		}

		String reversedEq = "";
		int size = infix.length() - 1;
		while (size >= 0) {
			char c = infix.charAt(size);
			if (c == '(') {
				reversedEq = reversedEq + ')';
			} else if (c == ')') {
				reversedEq = reversedEq + '(';
			} else {
				reversedEq = reversedEq + infix.charAt(size);
			}
			size--;
		}
		prefix = infixToPostfix(reversedEq, false);

		reversedEq = "";

		size = prefix.length() - 1;
		while (size >= 0) {
			char c = prefix.charAt(size);
			if (c == '(') {
				reversedEq = reversedEq + ')';
			} else if (c == ')') {
				reversedEq = reversedEq + '(';
			} else {
				reversedEq = reversedEq + prefix.charAt(size);
			}
			size--;
		}

		prefix = reversedEq;
		return reversedEq;
	}

	/**
	 * Converts infix to postfix notation.
	 * 
	 * @param infix        infix String
	 * @param enableAnswer to allow the user to write to answer variable.
	 * @throws CharacterFoundException if a letter is found throw
	 * @return postfix string
	 */
	public String infixToPostfix(String infix, Boolean enableAnswer)
			throws CharacterFoundException {
		if (!isBalanced()) {
			return postfix = "N/A";
		}
		EquationStack operator = new EquationStack();
		EquationStack numbers = new EquationStack();
		String strPostFix = "";
		String strNum = "";
		for (int i = 0; i < infix.length(); i++) {

			char c = infix.charAt(i);
			String str = String.valueOf(c);

			if (c >= '0' && c <= '9') {
				strNum += c;

				if (i == infix.length() - 1) {
					if (!strNum.equals("")) {
						strPostFix += " " + strNum;
						numbers.push(strNum);
					}
				}
			}

			else if (c == ')') {
				if (!strNum.equals("")) {
					numbers.push(strNum);

					strPostFix += " " + strNum;
					strNum = "";
				}

				while (!operator.peek().equals("(")) {
					String command = operator.pop();
					strPostFix += " " + command;
					numbers.push(solveOperation(command, numbers.pop(),
							numbers.pop()));
				}
				operator.pop();
			}

			else if (c == '(') {
				operator.push(str);
			}

			else if (c == '+' || c == '-') {

				if (!strNum.equals("")) {
					numbers.push(strNum);

					strPostFix += " " + strNum;
					strNum = "";
				}

				while (!operator.isEmpty()) {
					if (operator.peek().equals("(")) {
						operator.push(str);
						break;
					} else {
						String command = operator.pop();
						strPostFix += " " + command;
						String op1 = numbers.pop();
						String op2 = numbers.pop();
						numbers.push(solveOperation(command, op1, op2));
					}
				}
				if (operator.isEmpty()) {
					operator.push(str);
				}
			}
			else if (c == '/' || c == '*' || c == '%') {
				if (!strNum.equals("")) {
					numbers.push(strNum);

					strPostFix += " " + strNum;
					strNum = "";
				}

				while (!operator.isEmpty()) {
					if (operator.peek().equals("+")
							|| operator.peek().equals("-")
							|| operator.peek().equals("(")) {
						operator.push(str);
						break;
					} else {
						String command = operator.pop();
						strPostFix += " " + command;
						numbers.push(solveOperation(command, numbers.pop(),
								numbers.pop()));
					}
				}
				if (operator.isEmpty()) {
					operator.push(str);
				}
			}
			else if (c == '^') {
				if (!strNum.equals("")) {
					numbers.push(strNum);

					strPostFix += " " + strNum;
					strNum = "";
				}

				while (!operator.isEmpty()) {
					if (operator.peek().equals("+")
							|| operator.peek().equals("-")
							|| operator.peek().equals("/")
							|| operator.peek().equals("*")
							|| operator.peek().equals("%")
							|| operator.peek().equals("(")) {
						operator.push(str);
						break;
					} else {
						String command = operator.pop();
						strPostFix += " " + command;
						numbers.push(solveOperation(command, numbers.pop(),
								numbers.pop()));
					}
				}
				if (operator.isEmpty()) {
					operator.push(str);
				}
			}

			else if (c == ' ') {
				continue;
			} else {
				if (Character.isLetter(c)) {
					throw new CharacterFoundException(
							"The equation is not balanced.");
				}
				if (!strNum.equals("")) {
					numbers.push(strNum);

					strPostFix += " " + strNum;
					strNum = "";
				}
			}
		}

		while (!operator.isEmpty()) {
			if (operator.peek().equals("(")) {
				operator.pop();
			} else {
				String command = operator.pop();
				strPostFix += " " + command;
				numbers.push(
						solveOperation(command, numbers.pop(), numbers.pop()));
			}
		}

		strPostFix = strPostFix.trim().replaceAll(" +", " ");
		if (enableAnswer) {
			answer = Double.parseDouble(numbers.pop());
			postfix = strPostFix;
		}
		return strPostFix;

	}

	/**
	 * Checks if equation is balanced when parentheses, and brackets are used.
	 *
	 * @return true, if is balanced, else false
	 */
	public boolean isBalanced() {
		EquationStack tmp = new EquationStack();

		for (int i = 0; i < equation.length(); i++) {
			char c = equation.charAt(i);
			String str = String.valueOf(c);
			if (c == '(') {
				tmp.push(str);
			} else if (c == '{') {
				tmp.push(str);
			} else if (c == '[') {
				tmp.push(str);
			} else if (c == ' ') {
				continue;
			} else if (c == ')') {
				if (tmp.isEmpty()) {
					return balanced = false;
				} else if (tmp.peek().equals("(")) {
					tmp.pop();
				} else {
					return balanced = false;
				}
			} else if (c == '}') {
				if (tmp.isEmpty()) {
					return balanced = false;
				} else if (tmp.peek().equals("{")) {
					tmp.pop();
				} else {
					return balanced = false;
				}
			} else if (c == ']') {
				if (tmp.isEmpty()) {
					return balanced = false;
				} else if (tmp.peek().equals("[")) {
					tmp.pop();
				} else {
					return balanced = false;
				}
			}
		}
		if (tmp.isEmpty()) {
			return balanced = true;
		}
		return balanced = false;
	}

	/**
	 * Decimal to binary conversion.
	 *
	 * @param number the number
	 * @throws NotBalancedEquationException if equation is not balanced throw
	 * @return the string in binary format
	 */
	public String decToBin(int number) throws NotBalancedEquationException {
		if (balanced) {
			binary = "";
			while (number > 0) {
				binary = (number % 2) + binary;
				number = number / 2;
			}
			return binary;
		} else {
			throw new NotBalancedEquationException(
					"The equation is not balanced.");
		}
	}

	/**
	 * Decimal to hexadecimal.
	 *
	 * @param number the number
	 * @throws NotBalancedEquationException if equation is not balanced throw
	 * @return the string in hexadecimal format
	 */
	public String decToHex(int number) throws NotBalancedEquationException {
		if (balanced) {
			hex = "";
			String conversion = "";
			while (number > 0) {
				int val = number % 16;
				switch (val) {
				case (0):
					conversion = "0";
					break;
				case (1):
					conversion = "1";
					break;
				case (2):
					conversion = "2";
					break;
				case (3):
					conversion = "3";
					break;
				case (4):
					conversion = "4";
					break;
				case (5):
					conversion = "5";
					break;
				case (6):
					conversion = "6";
					break;
				case (7):
					conversion = "7";
					break;
				case (8):
					conversion = "8";
					break;
				case (9):
					conversion = "9";
					break;
				case (10):
					conversion = "A";
					break;
				case (11):
					conversion = "B";
					break;
				case (12):
					conversion = "C";
					break;
				case (13):
					conversion = "D";
					break;
				case (14):
					conversion = "E";
					break;
				case (15):
					conversion = "F";
					break;
				}
				hex = conversion + hex;
				number = number / 16;
			}
			return hex;
		} else {
			throw new NotBalancedEquationException(
					"The equation is not balanced.");
		}
	}

	/**
	 * Returns a String which is a neat, tabular representation of this
	 * Equation.
	 *
	 * @return the string
	 */
	public String toString() {
		return String.format("%-35s%-35s%-30s%15.3f%20s  %10s", equation,
				prefix, postfix, answer, binary, hex);

	}

}
