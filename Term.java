package polynomials;
import java.math.BigInteger;

/* 
 * This class Term represents a single term in the polynomial
 * 
 * The terms with zero coefficients should be removed from the 
 * representation.
 * 
 * Under no circumstances the exponent should be negative.
 */

public class Term implements DeepClone<Term> {

	/* instance fields */
	private int exponent;
	private BigInteger coefficient;

	/* Constructors */
	public Term(int e, BigInteger c)
	{
		exponent = e;
		coefficient = c;
	}
	
	// We must implement deepClone() method as the Term class implements  the DeepClone interface.
	// Returns a deep copy of the term. Hence the returned object resides in a different 
	// memory location than this object. 
	@Override
	public Term deepClone()
	{
		return new Term(exponent, coefficient);
	}

	/* Instance methods */
	BigInteger getCoefficient()
	{	
		return coefficient;
	}

	int getExponent()
	{
		return exponent;
	}

	void setCoefficient(BigInteger d)
	{
		coefficient = d;
	}

	void setExponent(int a){
		exponent = a;
	}
	
	// Hint: Notice that in your final submission, you are not supposed to use this
	// method for evaluating a polynomial. This method is provided for 
	// validating your implementation of Horner's method. 
	BigInteger eval(BigInteger x){
		BigInteger result = BigInteger.valueOf(1);
		int n = exponent;
		while (n != 0){
			result = result.multiply(x);
			n--;
		}
		return (result.multiply(coefficient));
	}
	
	@Override
	public String toString()
	{
		String s = "";
		// Check if the coefficient is less than zero. 
		if (coefficient.compareTo(new BigInteger("0")) < 0)
			s += "-";
		// Check if the coefficient is greater than zero. 
		else if (coefficient.compareTo(new BigInteger("0")) > 0)
			s += "+";
		
		// Check if the coefficient +1 or -1.
		if (coefficient.abs().compareTo(new BigInteger("1")) != 0)
			s += coefficient.abs();
		
		// Check if the term is not a constant
		if (exponent > 0)
		{
			s+="x";
			if (exponent > 1){
				s+="^" + exponent;
			}
		}
		// If the term is a constant and coefficient is +1 or -1
		else if (coefficient.abs().compareTo(new BigInteger("1")) == 0)
			s+="1";
		
		return s;
	}
}
