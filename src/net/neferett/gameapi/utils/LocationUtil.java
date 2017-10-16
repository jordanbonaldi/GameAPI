package net.neferett.gameapi.utils;

import org.bukkit.Location;

public class LocationUtil {

	public static String convertLocationToString(Location loc) {
		return loc.getX() + "," + loc.getY() + "," + loc.getZ();
	}
	
}
