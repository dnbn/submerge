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

				File fileTop = result.getTopSub();
				File fileBot = result.getBottomSub();
				service.mergeToASS(fileTop, fileBot, outputName);

			} else {

				result.printHelpOn(System.out);
			}

		} catch (ParsingOptionException e) {
			System.err.println(e.getMessage());
			System.out.println("Try --help for more information");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

}
