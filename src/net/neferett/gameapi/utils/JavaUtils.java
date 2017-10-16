package net.neferett.gameapi.utils;

import java.util.List;

import com.google.common.primitives.Ints;

public class JavaUtils {

	public static int[] convertListIntegerToArray(List<Integer> list) {
		return Ints.toArray(list);
	}

	public static String[] convertListStringToArray(List<String> list) {
		return list.toArray(new String[list.size()]);
	}
	
	public static void replaceAll(StringBuilder builder, String from, String to) {
	    int index = builder.indexOf(from);
	    while (index != -1) {
	        builder.replace(index, index + from.length(), to);
	        index += to.length();
	        index = builder.indexOf(from, index);
	    }
	}
}
