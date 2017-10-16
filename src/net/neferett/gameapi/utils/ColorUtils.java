package net.neferett.gameapi.utils;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.DyeColor;

public class ColorUtils {

	public static Color getRandomColor() {
		Color color = Color.AQUA;
		int r = new Random().nextInt(15);
		if (r == 1) return Color.BLACK;
		else if (r == 2) return Color.BLUE;
		else if (r == 3) return Color.FUCHSIA;
		else if (r == 4) return Color.GRAY;
		else if (r == 5) return Color.GREEN;
		else if (r == 6) return Color.LIME;
		else if (r == 7) return Color.MAROON;
		else if (r == 8) return Color.NAVY;
		else if (r == 9) return Color.ORANGE;
		else if (r == 10) return Color.PURPLE;
		else if (r == 11) return Color.RED;
		else if (r == 12) return Color.SILVER;
		else if (r == 13) return Color.TEAL;
		else if (r == 14) return Color.WHITE;
		else if (r == 15) return Color.YELLOW;
		return color;
	}

	public static DyeColor getRandomDyeColor() {
		DyeColor color = DyeColor.WHITE;
		int r = new Random().nextInt(15);
		if (r == 1) return DyeColor.ORANGE;
		else if (r == 2) return DyeColor.MAGENTA;
		else if (r == 3) return DyeColor.LIGHT_BLUE;
		else if (r == 4) return DyeColor.YELLOW;
		else if (r == 5) return DyeColor.LIME;
		else if (r == 6) return DyeColor.PINK;
		else if (r == 7) return DyeColor.GRAY;
		else if (r == 8) return DyeColor.SILVER;
		else if (r == 9) return DyeColor.CYAN;
		else if (r == 10) return DyeColor.PURPLE;
		else if (r == 11) return DyeColor.BLUE;
		else if (r == 12) return DyeColor.BROWN;
		else if (r == 13) return DyeColor.GREEN;
		else if (r == 14) return DyeColor.RED;
		else if (r == 15) return DyeColor.BLACK;
		return color;
	}

}
