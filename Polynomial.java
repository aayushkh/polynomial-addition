
// Name: Aayush Khanna
// USC NetID: aayushkh@usc.edu
// CS 455 PA1
// Spring 2017

import java.util.ArrayList;

/**
 * Polynomials can be added together, evaluated, and converted to a string form
 * for printing.
 */
public class Polynomial {
	private ArrayList<Term> polyterm;

	/**
	 * Creates the 0 polynomial.
	 */
	public Polynomial() {
		polyterm = new ArrayList<Term>();
	}

	/**
	 * Creates polynomial with a single term given
	 */
	public Polynomial(Term term) {
		polyterm = new ArrayList<Term>();
		this.polyterm.add(term);
		validate(this);
		assert this.isValidPolynomial();
	}

	/**
	 * Returns the Polynomial that is the sum of this polynomial and b (neither
	 * polynomial is modified)
	 * 
	 * @param t1
	 *            increment attached to b polynomial
	 * @param t2
	 *            increment attached to this polynomial
	 * @param sizeofb
	 *            number of terms in b
	 * @param sizeofthis
	 *            number of terms in this
	 * @param result
	 *            the polynomial to which this and b are added
	 */
	public Polynomial add(Polynomial b) {
		int t1 = 0;
		int t2 = 0;
		assert b.isValidPolynomial();
		assert this.isValidPolynomial();
		Polynomial result = new Polynomial();
		int sizeOfb = b.polyterm.size();
		int sizeOfthis = this.polyterm.size();

		for (int i = 0; i < (this.polyterm.size()) && (sizeOfb - t2 != 0) && (sizeOfthis - t1 != 0); i++) {
			if (b.polyterm.get(t2).getExpon() > this.polyterm.get(t1).getExpon()) {
				result.polyterm.add(b.polyterm.get(t2));
				t2++;
			} else if (b.polyterm.get(t2).getExpon() < this.polyterm.get(t1).getExpon()) {
				result.polyterm.add(this.polyterm.get(t1));
				t1++;
			} else if (b.polyterm.get(t2).getExpon() == this.polyterm.get(t1).getExpon()) {
				double temp = b.polyterm.get(t2).getCoeff() + this.polyterm.get(t1).getCoeff();
				result.polyterm.add(new Term(temp, b.polyterm.get(t2).getExpon()));
				t1++;
				t2++;
			}
		}
		for (int i = t2; i < b.polyterm.size(); i++) {
			result.polyterm.add(b.polyterm.get(i));
		}

		for (int i = t1; i < this.polyterm.size(); i++) {
			result.polyterm.add(this.polyterm.get(i));
		}

		validate(result); // Removing the 0 Coefficient terms in the result
							// before returning it
		assert result.isValidPolynomial();
		if (result.isValidPolynomial()) {
			return result;
		} else {
			System.out.println("One or more terms in the polynomial were Invalid ! ");
			return this;
		}
	}

	/**
	 * Returns the value of the polynomial at a given value of x.
	 * 
	 * @param result
	 *            the computed output of type double
	 */
	public double eval(double x) {
		double result = 0;
		for (int i = 0; i < this.polyterm.size(); i++) {
			result = result + (this.polyterm.get(i).getCoeff() * (Math.pow(x, this.polyterm.get(i).getExpon())));
		}
		return result;
	}

	/**
	 * Return a String version of the polynomial with the following format,
	 * shown by example: zero poly: "0.0" 1-term poly: "3.2x^2" 4-term poly:
	 * "3.0x^5 + -x^2 + x + -7.9"
	 * 
	 * Polynomial is in a simplified form (only one term for any exponent), with
	 * no zero-coefficient terms, and terms are shown in decreasing order by
	 * exponent.
	 */
	public String toFormattedString() {
		String str = "";
		int size = this.polyterm.size();
		if (this.polyterm.isEmpty()) {
			System.out.print("0.0");
		} else if ((this.polyterm.get(0).getExpon() == 0)) {
			str = str + this.polyterm.get(0).getCoeff();
		} else if ((this.polyterm.get(0).getExpon() == 1)) {
			str = str + this.polyterm.get(0).getCoeff() + "x";
		} else if ((this.polyterm.get(0).getExpon() != 0) && (this.polyterm.get(0).getExpon() != 0)) {
			str = str + this.polyterm.get(0).getCoeff() + "x^" + this.polyterm.get(0).getExpon();
		}
		for (int i = 1; i < size; i++) {
			if ((this.polyterm.get(i).getExpon() == 0)) {
				str = str + " + ";
				str = str + this.polyterm.get(i).getCoeff();
			} else if ((this.polyterm.get(i).getExpon() == 1)) {
				str = str + " + ";
				str = str + this.polyterm.get(i).getCoeff() + "x";
			} else {
				str = str + " + ";
				str = str + this.polyterm.get(i).getCoeff() + "x^" + this.polyterm.get(i).getExpon();
			}
		}
		return str;
	}

	// **************************************************************
	// PRIVATE METHOD(S)

	/**
	 * Validates the Polynomial by removing the 0 Coefficient terms
	 */

	private void validate(Polynomial a) {
		for (int i = 0; i < a.polyterm.size(); i++) {
			if ((a.polyterm.get(i).getCoeff() == 0)) {
				a.polyterm.remove(i);
				i--;
			}
		}
	}

	/**
	 * Returns true iff the polynomial data is in a valid state. Checks the
	 * following Representation Invariants - 1) Exponential value is never less
	 * than 0. 2) No Coefficient term is 0. 3) The Polynomial Terms are in
	 * Descending order of printing.
	 */
	private boolean isValidPolynomial() {

		for (int i = 0; i < polyterm.size(); i++) {
			if (polyterm.get(i).getExpon() < 0) {
				return false;
			}
		}
		if (polyterm.size() > 1) {

			for (int i = 0; i < polyterm.size() - 1; i++) {
				if (polyterm.get(i + 1).getExpon() > polyterm.get(i).getExpon()) {
					return false;
				} else if (polyterm.get(i).getCoeff() == 0) {
					return false;
				}
			}
			if (polyterm.get(polyterm.size() - 1).getCoeff() == 0) {
				// Check if the last terms Coeff!=0
				return false;
			}
		}

		return true;
	}

}