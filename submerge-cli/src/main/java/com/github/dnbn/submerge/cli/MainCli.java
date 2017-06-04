package com.github.dnbn.submerge.cli;

import java.io.File;
import java.util.List;

import com.github.dnbn.submerge.cli.CliParser.ParsingResult;
import com.github.dnbn.submerge.cli.exception.ParsingOptionException;

public class MainCli {

	public static void main(String[] args) {

		try {
			ParsingResult result = CliParser.getInstance().parse(args);

			CliService service = new CliService();
			String outputName = result.getOutputName();

			if (result.hasHelp()) {

				result.printHelpOn(System.out);

			} else if (result.hasSRTOption()) {

				List<File> files = result.getSrt();
				for (File file : files) {
					service.toSRT(file, files.size() > 1 ? null : outputName);
				}

			} else if (result.hasASSOption()) {

				List<File> files = result.getAss();
				for (File file : files) {
					service.toASS(file, files.size() > 1 ? null : outputName);
				}

			} else if (result.hasUTF8Option()) {

				List<File> files = result.getUtf8();
				for (File file : files) {
					service.toUTF8(file, files.size() > 1 ? null : outputName);
				}

			} else if (result.hasMergeOption()) {

				File fileTop = result.getMerge().get(0);
				File fileBot = result.getMerge().get(1);
				service.mergeToASS(fileTop, fileBot, outputName);

			} else if (result.hasFramerateOption()) {

				double source = Double.parseDouble(result.getSource());
				double destination = Double.parseDouble(result.getDestination());

				List<File> files = result.getFramerate();
				for (File file : files) {
					service.convertFramerate(file, source, destination, files.size() > 1 ? null : outputName);
				}

			} else if (result.hasRemoveLasLinesOption()) {

				for (File file : result.getRemoveLastLines()) {
					service.removeLastLines(file);
				}
			} else {

				result.printHelpOn(System.out);
			}

		} catch (ParsingOptionException e) {
			System.err.println(e.getMessage());
			System.out.println("Try --help for more information");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
