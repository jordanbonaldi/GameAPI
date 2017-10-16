package net.neferett.gameapi.utils;

import java.util.Arrays;

import org.bukkit.Location;

public class WaitingRoomUtils {

	static Location loc1;
	static Location loc2;
	
	public static void setLoc1(Location loc1) {
		WaitingRoomUtils.loc1 = loc1;
	}
	
	public static void setLoc2(Location loc2) {
		WaitingRoomUtils.loc2 = loc2;
	}
	
	public static boolean isInBase(Location loc)
	{
	    double[] dim = new double[2];
	   
	    dim[0] = loc1.getX();
	    dim[1] = loc2.getX();
	    Arrays.sort(dim);
	    if(loc.getX() > dim[1] || loc.getX() < dim[0])
	        return false;
	 
	    dim[0] = loc1.getY();
	    dim[1] = loc2.getY();
	    Arrays.sort(dim);
	    if(loc.getY() > dim[1] || loc.getY() < dim[0])
	        return false;
	    
	    dim[0] = loc1.getZ();
	    dim[1] = loc2.getZ();
	    Arrays.sort(dim);
	    if(loc.getZ() > dim[1] || loc.getZ() < dim[0])
	        return false;
	 
	 
	    return true;
	}
	
}
