package net.neferett.gameapi.utils;

public class MathUtils
{

	public static double doubleRandomInclusive(double max, double min)
	{
		if(max < min) {
			double tmp = min;
			min = max;
			max = tmp;
		}
		double r = Math.random();
		if (r < 0.5) { return ((1 - Math.random()) * (max - min) + min); }
		return (Math.random() * (max - min) + min);
	}

	public static int integerRandomInclusive(int max, int min)
	{
		if(max < min) {
			int tmp = min;
			min = max;
			max = tmp;
		}
		return (int) (Math.random() * (max - min) + min);
	}
	
	public static float random(int min, int max) {
		return (float) (Math.random() * (max - min) + min);
	}

}
