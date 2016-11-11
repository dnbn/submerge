package com.submerge.web.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "merge_histo", schema = "public")
public class MergeHisto {

	private int id;
	private String primaryColor;
	private String outlineColor;
	private int outlineWidth;
	private String fontName;
	private int fontSize;
	private Date histoDate;
	private int alignment;
	private Integer fkHisto;
	private boolean avoidSwitch = true;
	private boolean clean = true;
	private boolean adjustTimecodes;
	private boolean oneLine;
	private String filename;
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_merge_histo_id")
	@SequenceGenerator(name = "s_merge_histo_id", sequenceName = "s_merge_histo_id", allocationSize = 1)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "primary_color", nullable = false, length = 7)
	public String getPrimaryColor() {
		return this.primaryColor;
	}

	public void setPrimaryColor(String primaryColor) {
		this.primaryColor = primaryColor;
	}

	@Column(name = "outline_color", nullable = false, length = 7)
	public String getOutlineColor() {
		return this.outlineColor;
	}

	public void setOutlineColor(String outlineColor) {
		this.outlineColor = outlineColor;
	}

	@Column(name = "outline_width", nullable = false)
	public int getOutlineWidth() {
		return this.outlineWidth;
	}

	public void setOutlineWidth(int outlineWidth) {
		this.outlineWidth = outlineWidth;
	}

	@Column(name = "font_name", nullable = false, length = 20)
	public String getFontName() {
		return this.fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	@Column(name = "font_size", nullable = false)
	public int getFontSize() {
		return this.fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "histo_date", nullable = false, length = 13)
	public Date getHistoDate() {
		return this.histoDate;
	}

	public void setHistoDate(Date histoDate) {
		this.histoDate = histoDate;
	}

	@Column(name = "alignment", nullable = false)
	public int getAlignment() {
		return this.alignment;
	}

	public void setAlignment(int alignment) {
		this.alignment = alignment;
	}

	@Column(name = "fk_merge_histo", nullable = true)
	public Integer getFkHisto() {
		return this.fkHisto;
	}

	public void setFkHisto(Integer fkHisto) {
		this.fkHisto = fkHisto;
	}

	@Column(name = "avoid_switch", nullable = false)
	public boolean isAvoidSwitch() {
		return this.avoidSwitch;
	}

	public void setAvoidSwitch(boolean avoidSwitch) {
		this.avoidSwitch = avoidSwitch;
	}

	@Column(name = "clean", nullable = false)
	public boolean isClean() {
		return this.clean;
	}

	public void setClean(boolean clean) {
		this.clean = clean;
	}

	@Column(name = "adjust_timecodes", nullable = false)
	public boolean isAdjustTimecodes() {
		return this.adjustTimecodes;
	}

	public void setAdjustTimecodes(boolean adjustTimecodes) {
		this.adjustTimecodes = adjustTimecodes;
	}

	@Column(name = "one_line", nullable = false)
	public boolean isOneLine() {
		return this.oneLine;
	}

	public void setOneLine(boolean oneLine) {
		this.oneLine = oneLine;
	}

	
	@Column(name = "filename", nullable = true)
	public String getFileName() {
		return this.filename;
	}

	public void setFileName(String filename) {
		this.filename = filename;
	}

	@Override
	public String toString() {
		return "MergeHisto [id=" + this.id + ", primaryColor=" + this.primaryColor + ", outlineColor="
				+ this.outlineColor + ", outlineWidth=" + this.outlineWidth + ", fontName=" + this.fontName
				+ ", fontSize=" + this.fontSize + ", histoDate=" + this.histoDate + ", alignment=" + this.alignment
				+ "]";
	}

}
