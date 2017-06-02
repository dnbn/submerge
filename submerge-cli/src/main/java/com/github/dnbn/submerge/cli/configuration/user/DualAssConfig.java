package com.github.dnbn.submerge.cli.configuration.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dualAssConfig", propOrder = { 
		"filename", 
		"cleanSubtitles", 
		"mergeIntoOneLine", 
		"fixPosition",
		"adjustTimecodes", 
		"one", 
		"two" 
})
public class DualAssConfig {

	@XmlElement(required = true)
	protected String filename;
	protected boolean cleanSubtitles;
	protected boolean mergeIntoOneLine;
	protected boolean fixPosition;
	@XmlElement(required = true)
	protected AdjustTimecodes adjustTimecodes;
	@XmlElement(required = true)
	protected SubtitleConfig one;
	@XmlElement(required = true)
	protected SubtitleConfig two;

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String value) {
		this.filename = value;
	}
	
	public boolean isCleanSubtitles() {
		return this.cleanSubtitles;
	}

	public void setCleanSubtitles(boolean value) {
		this.cleanSubtitles = value;
	}

	public boolean isMergeIntoOneLine() {
		return this.mergeIntoOneLine;
	}

	public void setMergeIntoOneLine(boolean value) {
		this.mergeIntoOneLine = value;
	}

	public boolean isFixPosition() {
		return this.fixPosition;
	}

	public void setFixPosition(boolean value) {
		this.fixPosition = value;
	}

	public AdjustTimecodes getAdjustTimecodes() {
		return this.adjustTimecodes;
	}

	public void setAdjustTimecodes(AdjustTimecodes value) {
		this.adjustTimecodes = value;
	}

	public SubtitleConfig getOne() {
		return this.one;
	}

	public void setOne(SubtitleConfig value) {
		this.one = value;
	}

	public SubtitleConfig getTwo() {
		return two;
	}

	public void setTwo(SubtitleConfig value) {
		this.two = value;
	}

}
