package com.submerge.sub.ass;

import java.io.Serializable;
import java.text.DecimalFormat;


public class ScriptInfo implements Serializable  {

	private static final long serialVersionUID = -6613873382621648995L;
	
	private static final String TIMER = "Timer: ";
	private static final String PLAY_DEPTH = "PlayDepth: ";
	private static final String PLAY_RES_X = "PlayResX: ";
	private static final String PLAY_RES_Y = "PlayResY: ";
	private static final String COLLISIONS = "Collisions: ";
	private static final String SCRIPT_TYPE = "ScriptType: ";
	private static final String UPDATE_DETAILS = "Update Details: ";
	private static final String SCRIPT_UPDATED_BY = "Script Updated By: ";
	private static final String SYNCH_POINT = "Synch Point: ";
	private static final String ORIGINAL_TIMING = "Original Timing: ";
	private static final String ORIGINAL_EDITING = "Original Editing: ";
	private static final String ORIGINAL_TRANSLATION = "Original Translation: ";
	private static final String ORIGINAL_SCRIPT = "Original Script: ";
	private static final String TITLE = "Title: ";
	private static final String NEW_LINE = "\n";
	
	private static final DecimalFormat timeFormatter = new DecimalFormat("#.0000");

	public enum Collision {

		NORMAL("Normal"), REVERSE("Reverse");

		private String type;

		Collision(String type) {
			this.type = type;
		}

		@Override
		public String toString() {
			return this.type;
		}

	}

	/**
	 * This is a description of the script. If the original author(s) did not
	 * provide this information then <untitled> is automatically substituted.
	 */
	private String title;

	/**
	 * The original author(s) of the script. If the original author(s) did not
	 * provide this information then <unknown> is automatically substituted.
	 */
	private String originalScript	;

	/**
	 * (optional) The original translator of the dialogue. This entry does not
	 * appear if no information was entered by the author.
	 */
	private String originalTranslation;

	/**
	 * (optional) The original script editor(s), typically whoever took the raw
	 * translation and turned it into idiomatic english and reworded for
	 * readability. This entry does not appear if no information was entered by
	 * the author.
	 */
	private String originalEditing;

	/**
	 * (optional) Whoever timed the original script. This entry does not appear
	 * if no information was entered by the author.
	 */
	private String originalTiming;

	/**
	 * (optional) Description of where in the video the script should begin
	 * playback.
	 */
	private String synchPoint;
	/**
	 * (optional) The original script editor(s), typically whoever took the raw
	 * translation and turned it into idiomatic english and reworded for
	 * readability. This entry does not appear if no information was entered by
	 * the author.
	 */
	private String originalScriptChecking;

	/**
	 * (optional) Names of any other subtitling groups who edited the original
	 * script.
	 */
	private String scriptUpdatedBy;

	/**
	 * The details of any updates to the original script made by other
	 * subtilting groups.
	 */
	private String userDetails;

	/**
	 * This is the SSA script format version eg. "V4.00". It is used by SSA to
	 * give a warning if you are using a version of SSA older than the version
	 * that created the script.
	 */
	private String scriptType = "v4.00+";

	/**
	 * This determines how subtitles are moved, when automatically preventing
	 * onscreen collisions.
	 * 
	 * If the entry says "Normal" then SSA will attempt to position subtitles in
	 * the position specified by the "margins". However, subtitles can be
	 * shifted vertically to prevent onscreen collisions. With "normal"
	 * collision prevention, the subtitles will "stack up" one above the other -
	 * but they will always be positioned as close the vertical (bottom) margin
	 * as possible - filling in "gaps" in other subtitles if one large enough is
	 * available.
	 * 
	 * If the entry says "Reverse" then subtitles will be shifted upwards to
	 * make room for subsequent overlapping subtitles. This means the subtitles
	 * can nearly always be read top-down - but it also means that the first
	 * subtitle can appear half way up the screen before the subsequent
	 * overlapping subtitles appear. It can use a lot of screen area.
	 */
	private Collision collisions = Collision.NORMAL;

	/**
	 * This is the height of the screen used by the script's author(s) when
	 * playing the script. SSA v4 will automatically select the nearest enabled
	 * setting, if you are using Directdraw playback.
	 */
	private int playResY;

	/**
	 * This is the width of the screen used by the script's author(s) when
	 * playing the script. SSA will automatically select the nearest enabled,
	 * setting if you are using Directdraw playback.
	 */
	private int playResX;

	/**
	 * This is the colour depth used by the script's author(s) when playing the
	 * script. SSA will automatically select the nearest enabled setting if you
	 * are using Directdraw playback.
	 */
	private int playDepth;

	/**
	 * This is the Timer Speed for the script, as a percentage.
	 */
	private double timer = 100.0000;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		appendNotNull(sb, TITLE, this.title);
		appendNotNull(sb, ORIGINAL_SCRIPT, this.originalScript);
		appendNotNull(sb, ORIGINAL_TRANSLATION, this.originalTranslation);
		appendNotNull(sb, ORIGINAL_EDITING, this.originalEditing);
		appendNotNull(sb, ORIGINAL_TIMING, this.originalTiming);
		appendNotNull(sb, SYNCH_POINT, this.synchPoint);
		appendNotNull(sb, SCRIPT_UPDATED_BY, this.scriptUpdatedBy);
		appendNotNull(sb, UPDATE_DETAILS, this.userDetails);
		appendNotNull(sb, SCRIPT_TYPE, this.scriptType);
		appendNotNull(sb, COLLISIONS, this.collisions.toString());
		appendPositive(sb, PLAY_RES_Y, this.playResY);
		appendPositive(sb, PLAY_RES_X, this.playResX);
		appendPositive(sb, PLAY_DEPTH, this.playDepth);
		sb.append(TIMER).append(timeFormatter.format(this.timer));
		return sb.toString();
	}

	// ======================= private methods =======================

	private static void appendNotNull(StringBuilder sb, String desc, String val) {
		if (val != null) {
			sb.append(desc).append(val).append(NEW_LINE);
		}
	}

	private static void appendPositive(StringBuilder sb, String desc, int val) {
		if (val > 0) {
			sb.append(desc).append(Integer.toString(val)).append(NEW_LINE);
		}
	}

	// ===================== getter and setter start =====================

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOriginalScript() {
		return this.originalScript;
	}

	public void setOriginalScript(String originalScript) {
		this.originalScript = originalScript;
	}

	public String getOriginalTranslation() {
		return this.originalTranslation;
	}

	public void setOriginalTranslation(String originalTranslation) {
		this.originalTranslation = originalTranslation;
	}

	public String getOriginalEditing() {
		return this.originalEditing;
	}

	public void setOriginalEditing(String originalEditing) {
		this.originalEditing = originalEditing;
	}

	public String getOriginalTiming() {
		return this.originalTiming;
	}

	public void setOriginalTiming(String originalTiming) {
		this.originalTiming = originalTiming;
	}

	public String getSynchPoint() {
		return this.synchPoint;
	}

	public void setSynchPoint(String synchPoint) {
		this.synchPoint = synchPoint;
	}

	public String getOriginalScriptChecking() {
		return this.originalScriptChecking;
	}

	public void setOriginalScriptChecking(String originalScriptChecking) {
		this.originalScriptChecking = originalScriptChecking;
	}

	public String getScriptUpdatedBy() {
		return this.scriptUpdatedBy;
	}

	public void setScriptUpdatedBy(String scriptUpdatedBy) {
		this.scriptUpdatedBy = scriptUpdatedBy;
	}

	public String getUserDetails() {
		return this.userDetails;
	}

	public void setUserDetails(String userDetails) {
		this.userDetails = userDetails;
	}

	public String getScriptType() {
		return this.scriptType;
	}

	public void setScriptType(String scriptType) {
		this.scriptType = scriptType;
	}

	public Collision getCollisions() {
		return this.collisions;
	}

	public void setCollisions(Collision collisions) {
		this.collisions = collisions;
	}

	public int getPlayResY() {
		return this.playResY;
	}

	public void setPlayResY(int playResY) {
		this.playResY = playResY;
	}

	public int getPlayResX() {
		return this.playResX;
	}

	public void setPlayResX(int playResX) {
		this.playResX = playResX;
	}

	public int getPlayDepth() {
		return this.playDepth;
	}

	public void setPlayDepth(int playDepth) {
		this.playDepth = playDepth;
	}

	public double getTimer() {
		return this.timer;
	}

	public void setTimer(double timer) {
		this.timer = timer;
	}

}
