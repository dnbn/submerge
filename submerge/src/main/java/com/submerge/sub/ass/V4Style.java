package com.submerge.sub.ass;

import java.io.Serializable;

/**
 * Styles define the appearance and position of subtitles. All styles used by
 * the script are are defined by a Style line in the script.
 * 
 * Any of the the settings in the Style, (except shadow/outline type and depth)
 * can overridden by control codes in the subtitle text.
 * 
 * The fields which appear in each Style definition line are named in a special
 * line with the line type “Format:”. The Format line must appear before any
 * Styles - because it defines how SSA will interpret the Style definition
 * lines. The field names listed in the format line must be correctly spelled!
 * 
 * The fields are as follows:
 * 
 * Name, Fontname, Fontsize, PrimaryColour, SecondaryColour, TertiaryColour,
 * BackColour, Bold, Italic, Underline, StrikeOut, ScaleX, ScaleY, Spacing,
 * Angle, BorderStyle, Outline, Shadow, Alignment, MarginL, MarginR, MarginV,
 * Encoding
 * 
 * The format line allows new fields to be added to the script format in future,
 * and yet allow old versions of the software to read the fields it recognises -
 * even if the field order is changed.
 */
public class V4Style  implements Serializable {

	private static final long serialVersionUID = -4910432063071707768L;
	
	private static final String STYLE = "Style: ";
	public static final String FORMAT_STRING = "Name,Fontname,Fontsize,PrimaryColour,"
			+ "SecondaryColour,OutlineColour,BackColour,Bold,Italic,Underline,"
			+ "StrikeOut,ScaleX,ScaleY,Spacing,Angle,BorderStyle,Outline,Shadow,"
			+ "Alignment,MarginL,MarginR,MarginV,Encoding";
	private static final String COMMA = ",";

	/**
	 * The name of the Style. Case sensitive. Cannot include commas.
	 */
	private String name;

	/**
	 * The fontname as used by Windows. Case-sensitive.
	 */
	private String fontname = "Arial";

	/**
	 * The font size
	 */
	private int fontsize;

	/**
	 * A long integer BGR (blue-green-red) value. ie. the byte order in the
	 * hexadecimal equivelent of this number is BBGGRR
	 * 
	 * The color format contains the alpha channel, too. (AABBGGRR)
	 */
	private int primaryColour;

	/**
	 * long integer BGR (blue-green-red) value. ie. the byte order in the
	 * hexadecimal equivelent of this number is BBGGRR
	 * 
	 * This colour may be used instead of the Primary colour when a subtitle is
	 * automatically shifted to prevent an onscreen collsion, to distinguish the
	 * different subtitles.
	 * 
	 * The color format contains the alpha channel, too. (AABBGGRR)
	 */
	private int secondaryColour = 16777215; // #FFFFFF (white)

	/**
	 * A long integer BGR (blue-green-red) value. ie. the byte order in the
	 * hexadecimal equivelent of this number is BBGGRR
	 * 
	 * This colour may be used instead of the Primary or Secondary colour when a
	 * subtitle is automatically shifted to prevent an onscreen collsion, to
	 * distinguish the different subtitles.
	 * 
	 * The color format contains the alpha channel, too. (AABBGGRR)
	 */
	private int outlineColor;

	/**
	 * This is the colour of the subtitle outline or shadow, if these are used.
	 * A long integer BGR (blue-green-red) value. ie. the byte order in the
	 * hexadecimal equivelent of this number is BBGGRR.
	 * 
	 * The color format contains the alpha channel, too. (AABBGGRR)
	 */
	private int backColour;

	/**
	 * This defines whether text is bold (true) or not (false). -1 is True, 0 is
	 * False. This is independant of the Italic attribute - you can have have
	 * text which is both bold and italic.
	 */
	private boolean bold;

	/**
	 * This defines whether text is italic (true) or not (false). -1 is True, 0
	 * is False. This is independant of the bold attribute - you can have have
	 * text which is both bold and italic.
	 */
	private boolean italic;

	/**
	 * -1 is True, 0 is False
	 */
	private boolean underline;

	/**
	 * -1 is True, 0 is False
	 */
	private boolean strikeout;

	/**
	 * Modifies the width of the font. [percent]
	 */
	private int scaleX = 100;

	/**
	 * Modifies the height of the font. [percent]
	 */
	private int scaleY = 100;

	/**
	 * Extra space between characters. [pixels]
	 */
	private int spacing;

	/**
	 * The origin of the rotation is defined by the alignment. Can be a floating
	 * point number. [degrees]
	 */
	private int angle;

	/**
	 * 1=Outline + drop shadow, 3=Opaque box
	 */
	private int borderStyle = 1;

	/**
	 * If BorderStyle is 1, then this specifies the width of the outline around
	 * the text, in pixels. Values may be 0, 1, 2, 3 or 4.
	 */
	private int outline = 2;

	/**
	 * If BorderStyle is 1, then this specifies the depth of the drop shadow
	 * behind the text, in pixels. Values may be 0, 1, 2, 3 or 4. Drop shadow is
	 * always used in addition to an outline - SSA will force an outline of 1
	 * pixel if no outline width is given.
	 */
	private int shadow;

	/**
	 * This sets how text is "justified" within the Left/Right onscreen margins,
	 * and also the vertical placing. Values may be 1=Left, 2=Centered, 3=Right.
	 * Add 4 to the value for a "Toptitle". Add 8 to the value for a "Midtitle".
	 * eg. 5 = left-justified toptitle
	 */
	private int alignment = 2;

	/**
	 * This defines the Left Margin in pixels. It is the distance from the
	 * left-hand edge of the screen.The three onscreen margins (MarginL,
	 * MarginR, MarginV) define areas in which the subtitle text will be
	 * displayed.
	 */
	private int marginLeft = 10;

	/**
	 * This defines the Right Margin in pixels. It is the distance from the
	 * right-hand edge of the screen. The three onscreen margins (MarginL,
	 * MarginR, MarginV) define areas in which the subtitle text will be
	 * displayed.
	 */
	private int marginRight = 10;

	/**
	 * This defines the vertical Left Margin in pixels. For a subtitle, it is
	 * the distance from the bottom of the screen. For a toptitle, it is the
	 * distance from the top of the screen. For a midtitle, the value is ignored
	 * - the text will be vertically centred
	 */
	private int marginVertical = 10;

	/**
	 * This specifies the font character set or encoding and on multi-lingual
	 * Windows installations it provides access to characters used in multiple
	 * than one languages. It is usually 0 (zero) for English (Western, ANSI)
	 * Windows.
	 * 
	 * When the file is Unicode, this field is useful during file format
	 * conversions.
	 */
	private int encoding;

	public V4Style(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(STYLE);
		sb.append(this.name).append(COMMA);
		sb.append(this.fontname).append(COMMA);
		sb.append(this.fontsize).append(COMMA);
		sb.append(this.primaryColour).append(COMMA);
		sb.append(this.secondaryColour).append(COMMA);
		sb.append(this.outlineColor).append(COMMA);
		sb.append(this.backColour).append(COMMA);
		sb.append(this.bold ? -1 : 0).append(COMMA);
		sb.append(this.italic ? -1 : 0).append(COMMA);
		sb.append(this.underline ? -1 : 0).append(COMMA);
		sb.append(this.strikeout ? -1 : 0).append(COMMA);
		sb.append(this.scaleX).append(COMMA);
		sb.append(this.scaleY).append(COMMA);
		sb.append(this.spacing).append(COMMA);
		sb.append(this.angle).append(COMMA);
		sb.append(this.borderStyle).append(COMMA);
		sb.append(this.outline).append(COMMA);
		sb.append(this.shadow).append(COMMA);
		sb.append(this.alignment).append(COMMA);
		sb.append(this.marginLeft).append(COMMA);
		sb.append(this.marginRight).append(COMMA);
		sb.append(this.marginVertical).append(COMMA);
		sb.append(this.encoding);
		return sb.toString();
	}

	// ===================== getter and setter start =====================

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFontname() {
		return this.fontname;
	}

	public void setFontname(String fontname) {
		this.fontname = fontname;
	}

	public int getFontsize() {
		return this.fontsize;
	}

	public void setFontsize(int fontsize) {
		this.fontsize = fontsize;
	}

	public int getPrimaryColour() {
		return this.primaryColour;
	}

	public void setPrimaryColour(int primaryColour) {
		this.primaryColour = primaryColour;
	}

	public int getSecondaryColour() {
		return this.secondaryColour;
	}

	public void setSecondaryColour(int secondaryColour) {
		this.secondaryColour = secondaryColour;
	}

	public int getOutlineColor() {
		return this.outlineColor;
	}

	public void setOutlineColor(int outlineColor) {
		this.outlineColor = outlineColor;
	}

	public int getBackColour() {
		return this.backColour;
	}

	public void setBackColour(int backColour) {
		this.backColour = backColour;
	}

	public boolean isBold() {
		return this.bold;
	}

	public void setBold(boolean bold) {
		this.bold = bold;
	}

	public boolean isItalic() {
		return this.italic;
	}

	public void setItalic(boolean italic) {
		this.italic = italic;
	}

	public boolean isUnderline() {
		return this.underline;
	}

	public void setUnderline(boolean underline) {
		this.underline = underline;
	}

	public boolean isStrikeout() {
		return this.strikeout;
	}

	public void setStrikeout(boolean strikeout) {
		this.strikeout = strikeout;
	}

	public int getScaleX() {
		return this.scaleX;
	}

	public void setScaleX(int scaleX) {
		this.scaleX = scaleX;
	}

	public int getScaleY() {
		return this.scaleY;
	}

	public void setScaleY(int scaleY) {
		this.scaleY = scaleY;
	}

	public int getSpacing() {
		return this.spacing;
	}

	public void setSpacing(int spacing) {
		this.spacing = spacing;
	}

	public int getAngle() {
		return this.angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public int getOutline() {
		return this.outline;
	}

	public void setOutline(int outline) {
		this.outline = outline;
	}

	public int getShadow() {
		return this.shadow;
	}

	public void setShadow(int shadow) {
		this.shadow = shadow;
	}

	public int getAlignment() {
		return this.alignment;
	}

	public void setAlignment(int alignment) {
		this.alignment = alignment;
	}

	public int getMarginLeft() {
		return this.marginLeft;
	}

	public void setMarginLeft(int marginLeft) {
		this.marginLeft = marginLeft;
	}

	public int getMarginRight() {
		return this.marginRight;
	}

	public void setMarginRight(int marginRight) {
		this.marginRight = marginRight;
	}

	public int getMarginVertical() {
		return this.marginVertical;
	}

	public void setMarginVertical(int marginVertical) {
		this.marginVertical = marginVertical;
	}

	public int getEncoding() {
		return this.encoding;
	}

	public void setEncoding(int encoding) {
		this.encoding = encoding;
	}

	public int getBorderStyle() {
		return this.borderStyle;
	}

	public void setBorderStyle(int borderStyle) {
		this.borderStyle = borderStyle;
	}

}
