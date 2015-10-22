package challenges.java;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class C0226E {
	private static class Fraction {
		public BigInteger numerator, denominator;
		public Fraction(BigInteger numerator, BigInteger denominator) {
			BigInteger gcd = GCD(numerator, denominator);
			this.numerator = numerator.divide(gcd);
			this.denominator = denominator.divide(gcd);
		}
		
		public Fraction add(Fraction a) {
			BigInteger numerator = this.numerator.multiply(a.denominator).add(a.numerator.multiply(this.denominator));
			BigInteger denominator = this.denominator.multiply(a.denominator);
			BigInteger gcd = GCD(numerator, denominator);
			return new Fraction(numerator.divide(gcd), denominator.divide(gcd));
		}
		
		public BigInteger GCD(BigInteger a, BigInteger b) {
			if (b.equals(BigInteger.ZERO)) return a;
			return GCD(b, a.mod(b));
		}
		
		public String toString() {
			return numerator.toString() + " / " + denominator.toString();
		}
	}
	
	public static void main(String[] args) {
		List<Fraction> fractions = new ArrayList<Fraction>();
		
		Stream.of(args).forEach(input -> {
			String[] split = input.split("/");
			fractions.add(new Fraction(new BigInteger(split[0]), new BigInteger(split[1])));
		});
		
		Fraction f = new Fraction(new BigInteger("0"), new BigInteger("1"));
		for (Fraction frac : fractions) {
			f = f.add(frac);
		}
		
		System.out.println(f);
	}
}