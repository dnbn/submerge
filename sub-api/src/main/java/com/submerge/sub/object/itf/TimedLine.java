package com.submerge.sub.object.itf;

import java.util.List;

/**
 * Simple object that contains a text line with a time
 *
 */
public interface TimedLine {

	/**
	 * Get the text lines
	 * 
	 * @return textLines
	 */
	public abstract List<String> getTextLines();

	/**
	 * Get the timed object
	 * 
	 * @return the time
	 */
	public abstract TimedObject getTime();

}