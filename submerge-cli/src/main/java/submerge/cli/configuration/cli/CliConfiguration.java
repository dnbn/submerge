package submerge.cli.configuration.cli;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CliConfiguration {

	/**
	 * Print the help help
	 */
	private OptionDescription help;

	/**
	 * Convert a file to SRT
	 */
	private OptionDescription convertToSrt;

	/**
	 * Convert a file to ASS
	 */
	private OptionDescription convertToAss;

	/**
	 * Convert a file to UTF-8
	 */
	private OptionDescription convertToUtf8;

	/**
	 * Merge several sublitles into one ass
	 */
	private OptionDescription mergeToASS;

	/**
	 * Output filename
	 */
	private OptionDescription outputName;

	/**
	 * Top subtitle
	 */
	private OptionDescription topSubtitle;

	/**
	 * Bottom subtitle
	 */
	private OptionDescription bottomSubtitle;

	// ===================== getter and setter start =====================

	public OptionDescription getHelp() {
		return this.help;
	}

	@XmlElement(required = true)
	public void setHelp(OptionDescription help) {
		this.help = help;
	}

	public OptionDescription getConvertToSrt() {
		return this.convertToSrt;
	}

	@XmlElement(required = true)
	public void setConvertToSrt(OptionDescription convertToSrt) {
		this.convertToSrt = convertToSrt;
	}

	public OptionDescription getConvertToAss() {
		return this.convertToAss;
	}

	@XmlElement(required = true)
	public void setConvertToAss(OptionDescription convertToAss) {
		this.convertToAss = convertToAss;
	}

	public OptionDescription getConvertToUtf8() {
		return this.convertToUtf8;
	}

	@XmlElement(required = true)
	public void setConvertToUtf8(OptionDescription convertToUtf8) {
		this.convertToUtf8 = convertToUtf8;
	}

	public OptionDescription getMergeToASS() {
		return this.mergeToASS;
	}

	@XmlElement(required = true)
	public void setMergeToASS(OptionDescription mergeToASS) {
		this.mergeToASS = mergeToASS;
	}

	public OptionDescription getOutputName() {
		return this.outputName;
	}

	@XmlElement(required = true)
	public void setOutputName(OptionDescription outputName) {
		this.outputName = outputName;
	}

	public OptionDescription getTopSubtitle() {
		return this.topSubtitle;
	}

	@XmlElement(required = true)
	public void setTopSubtitle(OptionDescription topSubtitle) {
		this.topSubtitle = topSubtitle;
	}

	public OptionDescription getBottomSubtitle() {
		return this.bottomSubtitle;
	}

	@XmlElement(required = true)
	public void setBottomSubtitle(OptionDescription bottomSubtitle) {
		this.bottomSubtitle = bottomSubtitle;
	}

}
