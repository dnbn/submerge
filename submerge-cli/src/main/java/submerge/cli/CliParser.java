package submerge.cli;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.OptionSpecBuilder;
import submerge.cli.configuration.ConfigurationLoader;
import submerge.cli.configuration.cli.CliConfiguration;
import submerge.cli.configuration.cli.OptionDescription;
import submerge.cli.exception.ParsingOptionException;

public class CliParser {

	private static CliParser INSTANCE;

	/**
	 * The parser
	 */
	private OptionParser parser;

	private OptionSpec<Void> help;
	private OptionSpec<File> utf8;
	private OptionSpec<File> ass;
	private OptionSpec<File> srt;
	private OptionSpec<Void> merge;
	private OptionSpec<File> fileTop;
	private OptionSpec<File> fileBot;
	private OptionSpec<String> outputName;

	/**
	 * Private constructor
	 */
	private CliParser() {

		this.parser = new OptionParser();

		CliConfiguration cc = ConfigurationLoader.loadCliConfiguration();

		this.help = build(cc.getHelp(), Void.class, true, null);
		this.utf8 = build(cc.getConvertToUtf8(), File.class);
		this.ass = build(cc.getConvertToAss(), File.class);
		this.srt = build(cc.getConvertToSrt(), File.class);
		this.outputName = build(cc.getOutputName(), String.class);
		this.merge = build(cc.getMergeToASS(), Void.class);

		this.fileTop = build(cc.getTopSubtitle(), File.class, false, this.merge);
		this.fileBot = build(cc.getBottomSubtitle(), File.class, false, this.merge);

	}

	/**
	 * Return the unique instance of the parser
	 * 
	 * @return
	 */
	public static CliParser getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new CliParser();
		}
		return INSTANCE;
	}

	@SuppressWarnings("synthetic-access")
	public ParsingResult parse(String[] args) throws ParsingOptionException {

		ParsingResult result = null;
		try {
			result = new ParsingResult();

			OptionSet options = this.parser.parse(args);
			result.options = options;
			result.cliParser = this;

		} catch (OptionException e) {
			throw new ParsingOptionException(e);
		}

		return result;
	}

	@SuppressWarnings("synthetic-access")
	public class ParsingResult {

		private OptionSet options;
		private CliParser cliParser;

		public boolean hasHelp() {
			return this.options.has(this.cliParser.help);
		}

		public boolean hasSRTOption() {
			return this.options.has(this.cliParser.srt);
		}

		public boolean hasASSOption() {
			return this.options.has(this.cliParser.ass);
		}

		public boolean hasMergeOption() {
			return this.options.has(this.cliParser.merge);
		}

		public boolean hasUTF8Option() {
			return this.options.has(this.cliParser.utf8);
		}

		public File getUtf8() {
			return this.options.valueOf(this.cliParser.utf8);
		}

		public File getAss() {
			return this.options.valueOf(this.cliParser.ass);
		}

		public File getSrt() {
			return this.options.valueOf(this.cliParser.srt);
		}

		public File getTopSub() {
			return this.options.valueOf(this.cliParser.fileTop);
		}

		public File getBottomSub() {
			return this.options.valueOf(this.cliParser.fileBot);
		}

		public String getOutputName() {
			return this.options.valueOf(this.cliParser.outputName);
		}

		public void printHelpOn(OutputStream sink) throws IOException {
			CliParser.this.parser.printHelpOn(sink);
		}

	}

	/**
	 * Build the option from its description
	 * 
	 * @param optionDesc the description
	 * @param clazz the args class
	 * @return
	 */
	private <T> OptionSpec<T> build(OptionDescription optionDesc, Class<T> clazz) {
		return build(optionDesc, clazz, false, null);
	}

	/**
	 * Build the option from its description
	 * 
	 * @param optionDesc the description
	 * @param clazz the args class
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> OptionSpec<T> build(OptionDescription optionDesc, Class<T> clazz, boolean forHelp,
			OptionSpec<?> dependency) {

		OptionSpecBuilder osb = this.parser.accepts(optionDesc.getLongOption(), optionDesc.getDescription());

		if (dependency != null) {
			osb = osb.requiredIf(dependency);
		}

		if (clazz != Void.class) {
			return osb.withRequiredArg().ofType(clazz);
		}

		if (forHelp) {
			return (OptionSpec<T>) osb.forHelp();
		}

		return (OptionSpec<T>) osb;

	}
}
