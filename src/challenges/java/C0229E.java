package challenges.java;

import java.util.function.Function;

public class C0229E {
	private static final double EPSILON = 0.00001;
	
	public static void main(String[] args) {
		double result = Double.parseDouble(args[0]);
		Function<Double, Double> operation = x -> Math.cos(x);
		while (!compare(result, result = operation.apply(result))) {}
		System.out.println(result);
	}
	
	public static boolean compare(double a, double b) {
		return a == b ? true : Math.abs(a - b) < EPSILON;
	}
}