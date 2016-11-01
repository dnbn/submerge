package com.submerge.sub.object.itf;

import java.io.Serializable;
import java.util.List;

/**
 * Simple object that contains a text line with a time
 *
 */
public interface TimedLine extends Serializable {

	/**
	 * Get the text lines
	 * 
	 * @return textLines
	 */
	List<String> getTextLines();

	/**
	 * Get the timed object
	 * 
	 * @return the time
	 */
	TimedObject getTime();

}