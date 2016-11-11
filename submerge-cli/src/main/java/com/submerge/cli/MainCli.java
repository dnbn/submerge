package com.submerge.cli;

import java.io.File;

import com.submerge.cli.CliParser.ParsingResult;
import com.submerge.cli.exception.ParsingOptionException;

public class MainCli {

	public static void main(String[] args) {

		try {
			ParsingResult result = CliParser.getInstance().parse(args);

			CliService service = new CliService();
			String outputName = result.getOutputName();

			if (result.hasHelp()) {

				result.printHelpOn(System.out);

			} else if (result.hasSRTOption()) {

				service.toSRT(result.getSrt(), outputName);

			} else if (result.hasASSOption()) {

				File file = result.getAss();
				service.toASS(file, outputName);

			} else if (result.hasUTF8Option()) {

				File file = result.getUtf8();
				service.toUTF8(file, outputName);

			} else if (result.hasMergeOption()) {

				File fileTop = result.getMerge().get(0);
				File fileBot = result.getMerge().get(1);
				service.mergeToASS(fileTop, fileBot, outputName);

			} else if (result.hasFramerateOption()) {

				double source = Double.parseDouble(result.getSource());
				double destination = Double.parseDouble(result.getDestination());
				File file = result.getFramerate();

				service.convertFramerate(file, source, destination, outputName);

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
