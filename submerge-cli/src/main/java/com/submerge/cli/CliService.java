package com.submerge.cli;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;

import com.submerge.api.SubmergeAPI;
import com.submerge.api.parser.ParserFactory;
import com.submerge.api.parser.SubtitleParser;
import com.submerge.api.parser.exception.InvalidFileException;
import com.submerge.api.parser.exception.InvalidSubException;
import com.submerge.api.subtitle.ass.ASSSub;
import com.submerge.api.subtitle.common.TimedTextFile;
import com.submerge.api.subtitle.config.SimpleSubConfig;
import com.submerge.api.subtitle.srt.SRTSub;
import com.submerge.cli.configuration.ConfigurationLoader;
import com.submerge.cli.configuration.user.AdjustTimecodes;
import com.submerge.cli.configuration.user.DualAssConfig;
import com.submerge.cli.configuration.user.SubtitleConfig;

public class CliService {

	private SubmergeAPI api = new SubmergeAPI();

	/**
	 * Convert a file (srt or ass) to srt and write the new file on disk
	 * 
	 * @param file the file to convert
	 * @param outputFilename the destination file name
	 * @throws InvalidSubException
	 * @throws InvalidFileException
	 * @throws IOException
	 */
	public void toSRT(File file, String outputFilename) throws InvalidSubException, InvalidFileException, IOException {

		validFiles(file);
		String ext = FilenameUtils.getExtension(file.getName());

		SubtitleParser parser = ParserFactory.getParser(ext);

		TimedTextFile ttf = parser.parse(file);
		SRTSub srt = this.api.toSRT(ttf);

		String finalName = getFinalFilename(outputFilename, file, ext, "srt");
		FileUtils.writeStringToFile(new File(finalName), srt.toString());
	}

	/**
	 * Convert a file (srt or ass) to ass and write the new file on disk
	 * 
	 * @param file the file to convert
	 * @param outputFilename the destination file name
	 * @throws InvalidSubException
	 * @throws InvalidFileException
	 * @throws IOException
	 */
	public void toASS(File file, String outputFilename) throws InvalidSubException, InvalidFileException, IOException {

		validFiles(file);
		String ext = FilenameUtils.getExtension(file.getName());

		SubtitleParser parser = ParserFactory.getParser(ext);
		TimedTextFile ttf = parser.parse(file);

		SubtitleConfig config = ConfigurationLoader.loadUserConfiguration().getSimpleAssConfig();

		SimpleSubConfig subConfig = createSimpleSubConfig(ttf, config);
		subConfig.setAlignment(2);

		ASSSub ass = this.api.toASS(subConfig);

		String finalName = getFinalFilename(outputFilename, file, ext, "ass");
		FileUtils.writeStringToFile(new File(finalName), ass.toString());
	}

	/**
	 * Change the file encoding
	 * 
	 * @param file the file to change encoding from
	 * @param outputFilename the destination file name, null to overwrite the input file
	 * @throws IOException
	 * @throws InvalidFileException
	 */
	public void toUTF8(File file, String outputFilename) throws IOException, InvalidFileException {

		validFiles(file);
		String encoding = com.submerge.api.utils.FileUtils.guessEncoding(file);
		String content = FileUtils.readFileToString(file, encoding);

		if (StringUtils.isEmpty(outputFilename)) {
			FileUtils.writeStringToFile(file, content, "UTF-8");
		} else {
			FileUtils.writeStringToFile(new File(outputFilename), content, "UTF-8");
		}
	}

	/**
	 * Merge 2 files (srt or ass) in one ass and write the new file on disk
	 * 
	 * @param topFile the filename of top subtitle
	 * @param botFile the filename of bottom subtitle
	 * @param outputFilename the targer filename
	 * @throws InvalidSubException
	 * @throws InvalidFileException
	 * @throws IOException
	 */
	public void mergeToASS(File topFile, File botFile, String outputFilename) throws InvalidSubException,
			InvalidFileException, IOException {

		validFiles(topFile, botFile);

		String extOne = FilenameUtils.getExtension(topFile.getName());
		String extTwo = FilenameUtils.getExtension(botFile.getName());

		TimedTextFile subOne = ParserFactory.getParser(extOne).parse(topFile);
		TimedTextFile subTwo = ParserFactory.getParser(extTwo).parse(botFile);

		DualAssConfig config = ConfigurationLoader.loadUserConfiguration().getDualAssConfig();

		// Clean ASS formatting
		if (config.isCleanSubtitles()) {
			subOne = this.api.toSRT(subOne);
			subTwo = this.api.toSRT(subTwo);
		}

		// Disallow multi-lines
		if (config.isMergeIntoOneLine()) {
			this.api.mergeTextLines(subOne);
			this.api.mergeTextLines(subTwo);
		}

		// Adjust timecodes
		AdjustTimecodes adjustmentConfig = config.getAdjustTimecodes();
		if (adjustmentConfig.getValue()) {
			this.api.adjustTimecodes(subTwo, subOne, adjustmentConfig.getTolerance());
		}

		SimpleSubConfig subConfigOne = createSimpleSubConfig(subOne, config.getOne());
		SimpleSubConfig subConfigTwo = createSimpleSubConfig(subTwo, config.getTwo());

		// If both subs have the same position, add margin to the first one
		if (config.isFixPosition() && subConfigOne.getAlignment() == subConfigTwo.getAlignment()) {
			subConfigOne.setVerticalMargin(40);
			if (subConfigTwo.getFontconfig().getSize() > 12) {
				subConfigOne.setVerticalMargin(45);
				subConfigTwo.setVerticalMargin(5);
			}
			if (config.isMergeIntoOneLine()) {
				subConfigOne.setVerticalMargin(35);
				subConfigTwo.setVerticalMargin(10);
			}
		}

		ASSSub ass = this.api.mergeToAss(subConfigOne, subConfigTwo);

		String finalName = outputFilename;
		if (StringUtils.isEmpty(finalName)) {
			finalName = config.getFilename();
		}

		String topName = FilenameUtils.getBaseName(topFile.getName());
		String botName = FilenameUtils.getBaseName(botFile.getName());

		Map<String, String> substitutes = new HashMap<>();

		substitutes.put("top", topName);
		substitutes.put("bottom", botName);
		substitutes.put("baseTop", StringUtils.substringBeforeLast(topName, "."));
		substitutes.put("baseBottom", StringUtils.substringBeforeLast(botName, "."));

		StrSubstitutor substitutor = new StrSubstitutor(substitutes);
		finalName = substitutor.replace(finalName);

		FileUtils.writeStringToFile(new File(finalName + ".ass"), ass.toString());
	}

	/**
	 * Determine the filename of the generated file
	 * 
	 * @param outputFilename
	 * @param input
	 * @param ext
	 * @param targetExt
	 * @return
	 */
	private static String getFinalFilename(String outputFilename, File input, String ext, String targetExt) {

		String finalExt = "." + targetExt;
		String finalName = outputFilename;
		if (StringUtils.isEmpty(outputFilename)) {
			finalName = input.getName();
			finalName = StringUtils.removeEnd(input.getName(), "." + ext);
		}
		if (ext.equals(targetExt)) {
			finalName = StringUtils.removeEnd(finalName, finalExt) + "_out" + finalExt;
		}
		if (!finalName.endsWith(finalExt)) {
			finalName = finalName + finalExt;
		}
		return finalName;
	}

	private static SimpleSubConfig createSimpleSubConfig(TimedTextFile ttf, SubtitleConfig config) {

		SimpleSubConfig subConfig = new SimpleSubConfig(ttf, config.getFontConfig());
		subConfig.setStyleName(config.getStyleName());
		subConfig.setStyleName(config.getStyleName());
		subConfig.setAlignment(config.getAlignment().value() + config.getAlignmentOffset().value());
		return subConfig;
	}

	/**
	 * Check is the file is valid
	 * 
	 * @param files
	 * @throws InvalidFileException
	 */
	private static void validFiles(File... files) throws InvalidFileException {

		for (File file : files) {
			if (!file.exists()) {
				throw new InvalidFileException("File " + file.getName() + " does not exists.");
			}
			if (!file.isFile()) {
				throw new InvalidFileException(file.getName() + " is not a file.");
			}
		}
	}

}
