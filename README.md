[![](https://jitpack.io/v/deividasstr/docx-word-replacer.svg)](https://jitpack.io/#deividasstr/docx-word-replacer)
# Docx word replacer 
Wrapper for [Apache poi-ooxml java lib][1] explicitly dealing with docx files - replaces words/bookmarks/markers.
## Motivaton
This is quite a simple wrapper around medium level Apache poi-ooxml lib. When I needed this functionality it took me unreasonably long time to achieve it. Also this lib is my first shot at open source.
## How to use
Simply add path to jitpack repo and dependency:

Gradle

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.deividasstr:docx-word-replacer:0.1'
	}

Maven 

Step 1. Add the JitPack repository to your build file

	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
Step 2. Add the dependency

	<dependency>
	    <groupId>com.github.deividasstr</groupId>
	    <artifactId>docx-word-replacer</artifactId>
	    <version>0.1</version>
	</dependency>



API class is WordReplacer. Init it with docx file or native apache XWPFDocument, then call ```
replaceWordsInText(String bookmark, String replacement)```
 or ```
replaceWordsInTables(String bookmark, String replacement)```
to replace as many times as needed.
Then retrieve the XWPFDocument (if needed) by ```getModdedXWPFDoc()``` or get file by ```saveAndGetModdedFile()``` providing the filepath or file itself as arguments.
## Tips
All appropriate runs of the document are checked for bookmark string. Sometimes bookmark string is scattered across few runs, so the lib looks at the currently checked run, previous and next run for bookmark. 
* If bookmark is scattered in more than three runs or in different paragraphs, it will not be found and replaced.
* How text is divided into runs is rather mysterous. Usually text written in identical style is in the same run until line break.
Every style change (font, text size, bold etc.) means that it will go to a separate run. Text is also sometimes divided into runs on special characters (_, $ etc.) or it is pasted from elsewhere.
* Moreover docx creating and editing is most concise with microsoft word editor (I have tried libreoffice and sometimes).
* The same goes for converted doc files to docx. Sometimes converted files take few seconds to be opened (as XWPFDocument in the lib).

So basically if the bookmark in the document was not replaced, text is scattered across more than 3 runs or paragraphs.
## License
This project is licensed under the Apache 2.0 because of the Apache POI dependency under same license. See the LICENSE.md file for details.

[1]: https://poi.apache.org/document/index.html
