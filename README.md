# submerge

<p>
Submerge is a tool that aims to merge two subtitles into one ASS displaying both: one at the top of the screen, the other at the bottom. It can also be used to convert SRT to ASS or ASS to SRT, or to change the encoding of a file.
</p>

The project is made up of three sub-project :
* submerge-api : a library to manage SRT and ASS subtitles
* submerge-cli : a command line application with the exact same features as the website, see how to use it <a href=http://www.submerge.ovh/pages/subcli.xhtml>here</a>
* submerge-boot : the website available at www.submerge.ovh.
* [deprecated]submerge-web : old version of the website available at www.submerge.ovh. Java 8, legacy tomcat and postgresql database have been moved to java 17, spring boot and dynamodb with submerge-boot.

# Basic use of submerge-api


``` xml
<dependency>
  <groupId>com.github.dnbn.submerge</groupId>
  <artifactId>submerge-api</artifactId>
  <version>1.9.3</version>
</dependency>
```


<p>
Parsing ASS subtitles:
</p>

``` java
File file = new File("subtitle.ass");

ASSParser parser = new ASSParser();
ASSSub subtitle = parser.parse(file);

System.out.println(subtitle.toString());
```

Parsing SRT subtitles:

``` java
File file = new File("subtitle.srt");
SRTParser parser = new SRTParser();

SRTSub subtitle = parser.parse(file);

System.out.println(subtitle.toString());
```

Using interfaces:

``` java
File file = new File("subtitle.srt");
String extension = FilenameUtils.getExtension(file.getName());

SubtitleParser parser = ParserFactory.getParser(extension);
TimedTextFile subtitle = parser.parse(file);

System.out.println(subtitle.toString());
```

ASS to SRT:

``` java
File file = new File("subtitle.ass");

ASSParser parser = new ASSParser();
ASSSub ass = parser.parse(file);

SubmergeAPI api= new SubmergeAPI();
SRTSub srt = convert.toSRT(ass);

System.out.println(srt.toString());
```

SRT (or ASS) to ASS:

``` java
File file = new File("subtitle.srt");
String extension = FilenameUtils.getExtension(file.getName());

SubtitleParser parser = ParserFactory.getParser(extension);
TimedTextFile subtitle = parser.parse(file);

SimpleSubConfig config = new SimpleSubConfig();
config.setSub(subtitle);

ASSSub ass = new SubmergeAPI().toASS(config);

System.out.println(ass.toString());
```
