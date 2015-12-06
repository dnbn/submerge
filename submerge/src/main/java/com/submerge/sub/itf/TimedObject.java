package com.submerge.sub.itf;

import java.time.LocalTime;

/**
 * 
 * Simple object that contains timed start ant end
 */
public interface TimedObject {

	/**
	 * Return the time elapsed during script playback at which the text will appear
	 * onscreen.
	 * 
	 * @return start time
	 */
	public abstract LocalTime getStart();

	/**
	 * Return the time elapsed during script playback at which the text will disappear
	 * offscreen.
	 * 
	 * @return end time
	 */
	public abstract LocalTime getEnd();

}