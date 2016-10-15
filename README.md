# submerge

<p>
Submerge is a tool that aims to merge two subtitles into one ASS displaying both: one at the top of the screen, the other at the bottom. It can also be used to convert SRT to ASS or ASS to SRT, or to change the encoding of a file.
</p>

The project is made up of three sub-project :
* submerge-api : a library to manage SRT and ASS subtitles
* submerge-web : the website available at www.submerge.ovh
* submerge-cli : a command line application with the exact same features as the website, see how to use it <a href=http://www.submerge.ovh/pages/subcli.xhtml>here</a>

# Basic use of sub-api

<p>
Parsing ASS subtitles :
</p>
```
File file = new File("subtitle.ass");

ASSParser parser = new ASSParser();
ASSSub subtitle = parser.parse(file);

System.out.println(subtitle.toString());
```
Parsing SRT subtitles :
```
File file = new File("subtitle.srt");
SRTParser parser = new SRTParser();

SRTSub subtitle = parser.parse(file);

System.out.println(subtitle.toString());
```
Using interfaces :
```
File file = new File("subtitle.srt");		
String extension = FilenameUtils.getExtension(file.getName());

SubtitleParser parser = ParserFactory.getParser(extension);		
TimedTextFile subtitle = parser.parse(file);

System.out.println(subtitle.toString());
```
ASS to SRT :
```
File file = new File("subtitle.ass");

ASSParser parser = new ASSParser();
ASSSub ass = parser.parse(file);

SubtitleConverter convert = new SubtitleConverter();
SRTSub srt = convert.toSRT(ass);

System.out.println(srt.toString());
```
SRT (or ASS) to ASS :
```
File file = new File("subtitle.srt");		
String extension = FilenameUtils.getExtension(file.getName());

SubtitleParser parser = ParserFactory.getParser(extension);		
TimedTextFile subtitle = parser.parse(file);

SubInput config = new SubInput();
config.setSub(subtitle);

ASSSub ass = new SubtitleConverter().toASS(config);

System.out.println(ass.toString());
```
