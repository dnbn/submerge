package com.submerge.utils;

import java.awt.Color;

public final class SubUtils {

	/**
	 * Convert the hexadecimal color code to BGR code
	 * 
	 * @param hex
	 * @return
	 */
	public static int hexToBGR(String hex) {
		Color color = Color.decode(hex);
		int in = Integer.decode(Integer.toString(color.getRGB()));
		int red = (in >> 16) & 0xFF;
		int green = (in >> 8) & 0xFF;
		int blue = (in >> 0) & 0xFF;
		return (blue << 16) | (green << 8) | (red << 0);
	}

}
