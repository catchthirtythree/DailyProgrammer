package challenges.java;

import java.util.function.Function;

public class C0229E {
	private static final double EPSILON = 0.00001;
	
	public static void main(String[] args) {
		double result = 2;
		Function<Double, Double> operation = x -> Math.cos(x);
		while (!compare(result, result = operation.apply(result))) {}
		System.out.println(result);
		
		// Optional #1
		result = 2;
		operation = x -> x - Math.tan(x);
		while (!compare(result, result = operation.apply(result))) {}
		System.out.println(result);
		
		// Optional #2
		result = 2;
		operation = x -> 1 + 1 / x;
		while (!compare(result, result = operation.apply(result))) {}
		System.out.println(result);
		
		// Optional #3
		result = 2;
		operation = x -> 4 * x * (1 - x);
		while (!compare(result, result = operation.apply(result))) {}
		System.out.println(result);
	}
	
	public static boolean compare(double a, double b) {
		return a == b ? true : Math.abs(a - b) < EPSILON;
	}
}