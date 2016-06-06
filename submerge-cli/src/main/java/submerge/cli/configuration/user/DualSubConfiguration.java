package submerge.cli.configuration.user;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DualSubConfiguration {

	/**
	 * File name
	 */
	private String filename;

	/**
	 * Configuration for the top subtitle
	 */
	private AssConfiguration top;

	/**
	 * Configuration for the bottom subtitle
	 */
	private AssConfiguration bottom;

	// ===================== getter and setter start =====================
	
	public String getFilename() {

		return this.filename;
	}

	@XmlElement(required = true)
	public void setFilename(String filename) {

		this.filename = filename;
	}

	public AssConfiguration getTop() {

		return this.top;
	}

	@XmlElement(required = true)
	public void setTop(AssConfiguration top) {

		this.top = top;
	}

	public AssConfiguration getBottom() {

		return this.bottom;
	}

	@XmlElement(required = true)
	public void setBottom(AssConfiguration bottom) {

		this.bottom = bottom;
	}

}
