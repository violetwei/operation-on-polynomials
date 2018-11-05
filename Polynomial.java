package polynomials;

import java.math.BigInteger;

/* Polynomial class that builds upon the functionality of a linked list class.
 * For each method below, we indicate the worst case run time using O() notation. 
 * This worst case may depend on either the order 𝑚 of the polynomial as in the definition above, or it may depend on the number 𝑛 of terms in the polynomial.
 * Note that 𝑛 ≤ 𝑚 + 1.
 */

/*
 * This class defines a polynomial in one variable with positive integer exponents. 
 * Most of your work goes into this function. You should use the methods provided with the linked list class to implement the methods of this class. 
 * You will notice that the template we provided use Java BigInteger for storing the polynomial coefficient and variable. 
 * We intentionally chose BigInteger over floating point (e.g. double) to avoid numerical issues associated with polynomial evaluation.
 */

public class Polynomial 
{
	private SLinkedList<Term> polynomial;
	public int size()
	{	
		return polynomial.size();
	}
	private Polynomial(SLinkedList<Term> p)
	{
		polynomial = p;
	}
	
	public Polynomial()
	{
		polynomial = new SLinkedList<Term>();
	}

	
	// Returns a deep copy of the object.
	public Polynomial deepClone()
	{	
		return new Polynomial(polynomial.deepClone());
	}
	
	/* 
	 * Use the methods provided by the SLinkedList class. The runtime of this method should be 𝑂(𝑛).
	 * Also ensure the polynomial is in decreasing order of exponent.
	 */
	public void addTerm(Term t)
	{	
		// Notice that the function SLinkedList.get(index) method is O(n), 
		// so if this method were to call the get(index) 
		// method n times then the method would be O(n^2).
		// Instead, use a Java enhanced for loop to iterate through 
		// the terms of an SLinkedList.
		
		if(t.getCoefficient().equals(BigInteger.valueOf(0))) {
			System.out.println("Coefficient if 0.");
			return;
		}
		
		if(t.getExponent()<0) {
			System.out.println("Exponent is negative");
			return;
		}
		
		int index=0;
		
		for (Term currentTerm: polynomial){
			    // The for loop iterates over each term in the polynomial!!
			    // Example: System.out.println(currentTerm.getExponent()) should print the exponents of each term in the polynomial when it is not empty.
			    
			    if(currentTerm.getExponent() < t.getExponent()) {
			    	this.polynomial.add(index, t);
			    	return;
			    }
			    else if(currentTerm.getExponent() == t.getExponent()){
			    	if(currentTerm.getCoefficient().add(t.getCoefficient()).equals(BigInteger.valueOf(0))) {
			    	   this.polynomial.remove(index);
			    	   return;   
			    	}
			    	currentTerm.setCoefficient(currentTerm.getCoefficient().add(t.getCoefficient()));
			    	return;
			    }
			    index++;
		}
		
		if(index==this.polynomial.size()) {
			this.polynomial.addLast(t);
		}
		return;
				
	}
	
	public Term getTerm(int index)
	{
		return polynomial.get(index);
	}
	
	
	/* The add() method is a static method that adds two polynomials and returns a new polynomial as result. 
	 * You may use any of the class methods. Be careful not to modify either of the two polynomials. 
	 * The runtime of this method should be 𝑂(𝑛1 + 𝑛2) where 𝑛1, 𝑛2 are the number of terms in the two polynomials being added.
	 */
	//TODO: Add two polynomial without modifying either
	public static Polynomial add(Polynomial p1, Polynomial p2)
	{
		Polynomial new_poly = new Polynomial();
		
		Polynomial newP1 = p1.deepClone();  //clone polynomial using deepClone() 
		Polynomial newP2 = p2.deepClone();
		
		Term current_term1; //current term variable
		Term current_term2;
		
		while(!newP1.polynomial.isEmpty() && !newP2.polynomial.isEmpty()) {  //while the clone copy of both two polynomials are not empty
			current_term1 = newP1.getTerm(0);
			current_term2 = newP2.getTerm(0);
			
			if(current_term1.getExponent() < current_term2.getExponent()) {  //case when p1's exponent is smaller than p2's exponent
				new_poly.addTermLast(current_term2.deepClone());
				newP2.polynomial.removeFirst();
			}
			else if(current_term1.getExponent() == current_term2.getExponent()) {  //case when two exponents are equal
				if(current_term1.getCoefficient().add(current_term2.getCoefficient()).compareTo(new BigInteger("0")) != 0) {
					new_poly.addTermLast(new Term(current_term1.getExponent(), current_term1.getCoefficient().add(current_term2.getCoefficient())));
				}
				newP1.polynomial.removeFirst();
				newP2.polynomial.removeFirst();
			}
			else { 
				new_poly.addTermLast(current_term1.deepClone());
				newP1.polynomial.removeFirst();
			}
		}
		
		if(newP1.polynomial.isEmpty()) {  //case when clone copy of p1 is empty
			
			while(!newP2.polynomial.isEmpty()) {
				current_term2 = newP2.getTerm(0);
				new_poly.polynomial.addLast(current_term2.deepClone());
				newP2.polynomial.removeFirst();
			}
		}
		else {
			while(!newP1.polynomial.isEmpty()) {
				current_term1 = newP1.getTerm(0);
				new_poly.polynomial.addLast(current_term1.deepClone());
				newP1.polynomial.removeFirst();	
			}
		}
		return new_poly;
			
		
	}
	
	/* This is a private helper method used by the multiply function. 
	 * The polynomial object multiplies each of its terms by an argument term and updates itself. 
	 * The runtime of this method should be 𝑂(𝑛).
	 */
	//TODO: multiply this polynomial by a given term.
	private void multiplyTerm(Term t)
	{	
		int exp = t.getExponent();
		BigInteger biginteger = t.getCoefficient();
		
		for(Term currentTerm : polynomial) {
			currentTerm.setCoefficient(currentTerm.getCoefficient().multiply(biginteger));
			currentTerm.setExponent(currentTerm.getExponent()+exp);
		}
		
	}
	
	/*
	 * This is a static method that multiply two polynomials and returns a new polynomial as result. 
	 * Careful not to modify either of the two polynomials. 
	 * recommend use Polynomial.multiplyTerm, Polynomial.add and any other helper methods that you need. 
	 * The runtime of this method should be 𝑂(𝑛1𝑛2) where 𝑛1, 𝑛2 are the number of terms in the two polynomials being multiplied.
	 */
	//TODO: multiply two polynomials
	public static Polynomial multiply(Polynomial p1, Polynomial p2)
	{
		/**** ADD CODE HERE ****/
		if(p1.size() == 0 || p2.size()==0) {
			return null;
		}
		
		int upperBound = p2.size();
		
		Polynomial result = new Polynomial();
		Polynomial newP1  = p1.deepClone();
		
		for(int i =0; i<upperBound; i++) {
			
			newP1.multiplyTerm(p2.getTerm(i));
			result=add(result, newP1);
			newP1  = p1.deepClone();
		}
		
		return result;
		
		
		
	}
	
	/* 
	 * The polynomial object evaluates itself for a given value of 𝑥 using Horner’s method. 
	 * The variable 𝑥 is of BigInteger data type, as mentioned earlier. 
	 * Horner’s method greatly speeds up the evaluation for exponents with many terms. 
	 * It does so by not having to re-compute the 𝑥𝑖 fresh for each term. 
	 * Note that you should not use Term.eval( ) method to evaluate a particular term. 
	 * That method is provided for you only to help with testing, namely to ensure that your implementation of Horner’s method is correct.
	 */
	
	//TODO: evaluate this polynomial.
	// The time complexity of eval() must be order O(m), where m is the largest degree of the polynomial. 
	// Notice that the function SLinkedList.get(index) method is O(m), 
	// so if your eval() method were to call the get(index) 
	// method m times then your eval method would be O(m^2).
	// Instead, use a Java enhanced for loop to iterate through 
	// the terms of an SLinkedList.
	
	/*
	 * Horner’s method can be used to evaluate polynomial in O(n) time. 
	 * To understand the method, let us consider the example of 2x^3 – 6x^2 + 2x – 1.
	 * The polynomial can be evaluated as ((2x – 6)x + 2)x – 1. 
	 * The idea is to initialize result as coefficient of x^n which is 2 in this case, 
	 * repeatedly multiply result with x and add next coefficient to result. 
	 * Finally return result.
	 */

	public BigInteger eval(BigInteger x)
	{
		BigInteger final_bigint = new BigInteger("0");
		
		if(polynomial.size() == 0) {
			return final_bigint;
		}
		
		if(polynomial.size() == 1) {  //case when only a constant exist in the polynomial 
			
				for(Term currentTerm : polynomial) {
					
					int exp = currentTerm.getExponent();
					final_bigint = currentTerm.getCoefficient();
					
					for(int i = 0; i < exp; i++) {
						final_bigint = final_bigint.multiply(x);
					}
				}
				return final_bigint;
				
	     }else {
				
				//case when the exponent is equal or greater than 1
				int remainExp = getTerm(0).getExponent();
				BigInteger lastOne = new BigInteger("0");
				
				final_bigint = final_bigint.add(getTerm(0).getCoefficient());
				final_bigint = final_bigint.multiply(x);
		    	remainExp = remainExp - 1;
		    	
		    	Term prevTerm = getTerm(0);
		    	boolean firstTime = true;
				
				for(Term currTerm : polynomial) {
					
					if(firstTime) {
						firstTime = false;
					}
					else {
						int prevExp = prevTerm.getExponent();
					    int currExp = currTerm.getExponent();
					    int gap = prevExp - currExp - 1;
					
					    if(gap == 0 && currExp != 0) {   //case when the exponent gap is 0
						    final_bigint = final_bigint.add(currTerm.getCoefficient());
				    	    final_bigint = final_bigint.multiply(x);
				    	    remainExp = remainExp - 1;
				    	    prevTerm = currTerm;
					    }
					    else if(gap != 0 && currExp != 0) { //case when the exponent gap is non-zero
					    	
						    for(int j = 0; j < gap; j++) {
							    final_bigint = final_bigint.multiply(x);
							    remainExp = remainExp - 1;
						    }
						
						    final_bigint = final_bigint.add(currTerm.getCoefficient());
				    	    final_bigint = final_bigint.multiply(x);
				    	    remainExp = remainExp - 1;
				    	    prevTerm = currTerm;   
					    }
					    else{
						    lastOne = currTerm.getCoefficient();
					    }
					}
				}
			    
			    
			    //final_bigint = final_bigint.divide(x);
			    if(remainExp == 0) {
			    	final_bigint = final_bigint.add(lastOne);
			    	return final_bigint;
			    }
			    else {
			    	//System.out.println(remainExp);
			    	for(int i = 0; i < remainExp; i++) {
			    		final_bigint = final_bigint.multiply(x);
			    	}
			    }
			    final_bigint = final_bigint.add(lastOne);
			    
			    return final_bigint;
		}	
		
	}
	
	// Checks if this polynomial is same as the polynomial in the argument
	// Used for testing whether two polynomials have same content but occupy disjoint space in memory.
    
	public boolean checkEqual(Polynomial p)
	{	
		// Test for null pointer exceptions!!
		// Clearly two polynomials are not same if they have different number of terms
		if (polynomial == null || p.polynomial == null || size() != p.size())
			return false;
		
		int index = 0;
		// Simultaneously traverse both this polynomial and argument. 
		for (Term term0 : polynomial)
		{
			// This is inefficient, ideally you'd use iterator for sequential access.
			Term term1 = p.getTerm(index);
			
			if (term0.getExponent() != term1.getExponent() ||  // Check if the exponents are not same
				term0.getCoefficient().compareTo(term1.getCoefficient()) != 0 || // Check if the coefficients are not same
				term1 == term0)  // Check if the both term occupy same memory location.
					return false;
			
			index++;
		}
		return true;
	}
	
	// This method blindly adds a term to the end of LinkedList polynomial. 
	// Avoid using this method in your implementation as it is only used 
	public void addTermLast(Term t)
	{	
		polynomial.addLast(t);
	}
	
	// This is used for testing multiplyTerm
	public void multiplyTermTest(Term t)
	{
		multiplyTerm(t);
	}
	
	@Override
	public String toString()
	{	
		if (polynomial.size() == 0) return "0";
		return polynomial.toString();
	}
}
