package com.github.dnbn.submerge.cli;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.OptionSpecBuilder;

import com.github.dnbn.submerge.cli.configuration.ConfigurationLoader;
import com.github.dnbn.submerge.cli.configuration.cli.CliConfiguration;
import com.github.dnbn.submerge.cli.configuration.cli.OptionDescription;
import com.github.dnbn.submerge.cli.exception.ParsingOptionException;

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
	private OptionSpec<String> outputName;
	private OptionSpec<File> files;
	private OptionSpec<String> source;
	private OptionSpec<String> destination;
	private OptionSpec<File> framerate;

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
		this.source = build(cc.getSource(), String.class);
		this.destination = build(cc.getDestination(), String.class);
		this.framerate = build(cc.getFramerate(), File.class);

		this.files = this.parser.nonOptions().ofType(File.class);
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

			if (result.hasMergeOption() && result.getMerge().size() < 2) {
				throw new ParsingOptionException("Merge option requires 2 arguments");
			}

			if (result.hasFramerateOption()) {
				if (!result.hasSourceOption() && !result.hasDestinationOption()) {
					throw new ParsingOptionException(
							"Framerate option requires --source and --destination. Ex --source 25.000");
				}
			}
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

		public boolean hasSourceOption() {
			return this.options.has(this.cliParser.source);
		}

		public boolean hasDestinationOption() {
			return this.options.has(this.cliParser.destination);
		}

		public boolean hasFramerateOption() {
			return this.options.has(this.cliParser.framerate);
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

		public List<File> getMerge() {
			return this.options.valuesOf(this.cliParser.files);
		}

		public String getOutputName() {
			return this.options.valueOf(this.cliParser.outputName);
		}

		public String getSource() {
			return this.options.valueOf(this.cliParser.source);
		}

		public String getDestination() {
			return this.options.valueOf(this.cliParser.destination);
		}

		public File getFramerate() {
			return this.options.valueOf(this.cliParser.framerate);
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
