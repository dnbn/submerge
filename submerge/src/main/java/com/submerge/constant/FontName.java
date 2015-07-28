package com.submerge.constant;

public enum FontName {
	
	Arial("Arial"), 
	ComicSansMS("Comic Sans MS"), 
	CourierNew("Courier New"),
	Georgia("Georgia"),
	Impact("Impact"),
	LucidaConsole("Lucida Console"),
	Tahoma("Tahoma"),
	TimesNewRoman("Times New Roman"),
	TrebuchetMS("Trebuchet MS"),
	Verdana("Verdana"),
	Symbol("Symbol"),
	MSSerif("MS Serif");

	private String name;

	FontName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}



	@Override
	public String toString() {
		return this.name;
	}	

}
