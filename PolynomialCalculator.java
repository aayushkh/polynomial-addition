
// Name: Aayush Khanna
// USC NetID: aayushkh@usc.edu
// CS 455 PA1
// Spring 2017

import java.util.Scanner;

public class PolynomialCalculator {
	/**
	 * All the constants declared
	 */
	private static final int FINAL_INDEX = 10;
	static Polynomial[] poly = new Polynomial[FINAL_INDEX];

	/**
	 * The main class which keeps taking in the command input as long as the
	 * command is not "quit"
	 * 
	 * @param input
	 *            reads the command from the user
	 * @param parts
	 *            String array that stores the input
	 */

	public static void main(String[] args) {
		String input;
		String[] parts = new String[FINAL_INDEX];
		Scanner in = new Scanner(System.in);
		do {
			menu();
			System.out.print("cmd> ");
			input = in.nextLine();
			input = input.toLowerCase();
			parts = input.split("\\s+");
			checkString(parts, in);
		} while (!(parts[0].compareTo("quit") == 0));
		in.close();
	}

	/**
	 * The menu class which stores the list of commands available
	 */
	public static void menu() {
		System.out.println("******************* HELP MENU *******************");
		System.out.println("Here is a the list of Commands you can perform - ");
		System.out.println("1. Create a polynomial : create <polynomial number> eg. create 1");
		System.out.println("2. Add 2 polynomials : add <polynomial sum> <polynomial number 1> <polynomial number 2> eg add 2 1");
		System.out.println("3. Evaluate the polynomial : eval <polynomial number> eg. eval 1");
		System.out.println("4. Print the polynomial : print <polynomial number> eg. print 1");
		System.out.println("5. Exit the program : quit");
		System.out.println("");
	}

	/**
	 * Checks if the input command is a valid 1, and proceeds to do the
	 * respective method call.
	 * 
	 * @param temp
	 *            this is the polynomial number reference
	 * @param strLen
	 *            length of String array parts
	 */
	public static void checkString(String[] parts, Scanner in) {
		int[] index = new int[FINAL_INDEX];
		int strlen = parts.length;
		if ((parts[0].compareTo("create") == 0) && (strlen == 2) && (parts[1].matches("\\d+"))) {
			index[0] = Integer.parseInt(parts[1]);
			doCreate(index[0], in);
		} else if ((parts[0].compareTo("add") == 0) && (strlen == 4)) {
			if ((parts[1].matches("\\d+")) && (parts[2].matches("\\d+")) && (parts[3].matches("\\d+"))) {
				index[0] = Integer.parseInt(parts[1]);
				index[1] = Integer.parseInt(parts[2]);
				index[2] = Integer.parseInt(parts[3]);
				doAdd(index, in);
			}
		} else if ((parts[0].compareTo("eval") == 0) && (strlen == 2) && (parts[1].matches("\\d+"))) {
			index[0] = Integer.parseInt(parts[1]);
			doEval(index[0], in);
		} else if ((parts[0].compareTo("print") == 0) && (strlen == 2) && (parts[1].matches("\\d+"))) {
			index[0] = Integer.parseInt(parts[1]);
			doPrint(index[0]);
		} else if ((parts[0].compareTo("quit") == 0) && (strlen == 1)) {
			System.out.println("Exiting ..");
		} else if ((parts[0].compareTo("help") == 0) && (strlen == 1)) {
			menu();
		} else {
			System.out.println("ERROR : Please type a valid command.");
			System.out.println("Type Help to get Command Options.");
		}
	}

	/**
	 * Creates the polynomial. Does the following - i) Checks if the polynomial
	 * temp is greater than 9 and less than 0, inclusive. ii) Checks if the
	 * entered polynomial exponential - coefficient terms have Integer
	 * Coefficient and Double Coefficients respectively. iii) Makes the
	 * Exponential term absolute if it is negative.
	 */
	public static void doCreate(int temp, Scanner in) {
		if ((temp >= 0) && (temp <= 9)) {
			int oddtermchecker = 0;
			double[] coeff = new double[FINAL_INDEX];
			int[] expon = new int[FINAL_INDEX];
			poly[temp] = new Polynomial();
			String[] parts;
			do {
				System.out.println("Enter a space-separated sequence of coeff-power pairs terminated by enter eg. 3 2 3 1 creates 3x^2 + 3x");
				String input = in.nextLine();
				parts = input.split("\\s+");
				if ((isExponInt(parts)) && (isCoeffDouble(parts))) {
					if ((parts.length % 2 == 1)) {
						System.out.println("WARNING : One exponent term is missing. Last Coefficient ignored.");
						oddtermchecker++;
					}
					for (int i = 0; i < parts.length - oddtermchecker; i++) {
						if (i % 2 == 0) {
							coeff[i / 2] = Double.parseDouble(parts[i]);
						} else {
							if (Integer.parseInt(parts[i]) < 0) {
								System.out.println("WARNING : You have entered a negative exponent. Absolute value taken.");
							}
							expon[i / 2] = Math.abs(Integer.parseInt(parts[i]));
						}
					}
					for (int i = 0; i < parts.length / 2; i++) {
						poly[temp] = poly[temp].add(new Polynomial(new Term(coeff[i], expon[i])));
					}
				} else {
					System.out.println("ERROR : You seem to have entered something wrong. Please re-enter your values.");
				}
			} while (!(isExponInt(parts)) && !(isCoeffDouble(parts)));
		} else {
			System.out.println("ERROR: 1. illegal index for a poly.  must be between 0 and 9, inclusive");
			System.out.println("or     2. the polynomial does not exist. Make the polynomial first)");
		}
	}

	/**
	 * Adds 2 polynomials. It Checks if the polynomial temp is greater than 9
	 * and less than 0, inclusive.
	 */

	public static void doAdd(int[] temp, Scanner in) {
		if ((temp[0] >= 0) && (temp[0] <= 9) && (temp[1] >= 0) && (temp[1] <= 9) && (temp[2] >= 0) && (temp[2] <= 9)
				&& (poly[temp[1]] != null) && (poly[temp[2]] != null)) {
			poly[temp[0]] = poly[temp[1]].add(poly[temp[2]]);
		} else {
			System.out.println("ERROR: 1. illegal index for a poly.  must be between 0 and 9, inclusive");
			System.out.println("or     2. the polynomial does not exist. Make the polynomial first)");

		}
	}

	/**
	 * Evaluates the polynomial for an x input
	 * 
	 * @param input
	 *            the value for x to be computed
	 */

	public static void doEval(int temp, Scanner in) {
		double input;
		if ((temp >= 0) && (temp <= 9) && (poly[temp] != null)) {
			System.out.println("Enter a floating point value for x :");
			input = in.nextDouble();
			in.nextLine();
			System.out.println(poly[temp].eval(input));
		} else {
			System.out.println("ERROR: 1. illegal index for a poly.  must be between 0 and 9, inclusive");
			System.out.println("or     2. the polynomial does not exist");
		}
	}

	/**
	 * Prints the polynomial
	 */
	public static void doPrint(int temp) {
		if ((temp >= 0) && (temp <= 9) && (poly[temp] != null)) {
			System.out.println(poly[temp].toFormattedString());
		} else {
			System.out.println("ERROR: 1. illegal index for a poly.  must be between 0 and 9, inclusive");
			System.out.println("or     2. the polynomial does not exist. Make the polynomial first)");
		}
	}

	/**
	 * Checks if the string array holds integer values. Comes handy to check if
	 * the exponential terms are Integer type. Regular expressions used to
	 * achieve this.
	 */

	public static boolean isExponInt(String[] parts) {
		for (int i = 1; i < parts.length; i = i + 2) {
			if (!(parts[i].matches("-?\\d+"))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if the string array holds double values. Comes handy to check if
	 * the Coefficients are of Double type. Regular expressions are used to
	 * achieve this.
	 */

	public static boolean isCoeffDouble(String[] parts) {
		for (int i = 0; i < parts.length; i = i + 2) {
			if (!(parts[i].matches("-?\\d+\\.?\\d+")) && !(parts[i].matches("-?\\d+"))) {

				return false;
			}
		}
		return true;
	}
}