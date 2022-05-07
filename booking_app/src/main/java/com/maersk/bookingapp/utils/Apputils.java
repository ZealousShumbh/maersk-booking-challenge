package com.maersk.bookingapp.utils;

import java.util.ArrayList;
import java.util.List;

public class Apputils {
	/**
	 * Checks if the given string is valid or not.
	 * <p>
	 * Returns {@code false}, if the given string is null or empty. Else return
	 * {@code true}.
	 * 
	 * @param inputString The given string.
	 * @return A boolean representing the validity of the given string.
	 */
	public static boolean isValidString(String inputString) {
		if (inputString != null && !inputString.isEmpty())
			return true;
		else
			return false;
	}

	public static List<Integer> getAllowedContainerSizes() {
		List<Integer> allowedContainerSizes = new ArrayList<Integer>();
		allowedContainerSizes.add(20);
		allowedContainerSizes.add(40);
		return allowedContainerSizes;
	}
}
