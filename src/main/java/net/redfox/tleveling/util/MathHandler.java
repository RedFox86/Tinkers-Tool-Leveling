package net.redfox.tleveling.util;

public class MathHandler {
	public static int round(double input) {
		return (int)Math.round(input);
	}
	public static double getUniformDecimal(double number) {
		return MathHandler.round(number*100)/100d;
	}
	public static double getUniformPercentage(double a, int b) { //This number is capped at two decimal points - do not store values with it!
		return getUniformDecimal((a/b)*100);
	}
}