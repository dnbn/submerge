package com.submerge.sub.itf;

import java.util.Set;

/**
 * Object that represents a text file containing timed lines
 */
public interface TimedTextFile {

	/**
	 * Get the filename
	 * 
	 * @return the filename
	 */
	public String getFileName();

	/**
	 * Set the filename
	 * 
	 * @param fileName: the filename
	 */
	public void setFileName(String fileName);

	/**
	 * Get the timed lines
	 * 
	 * @return lines
	 */
	public Set<? extends TimedLine> getTimedLines();

}