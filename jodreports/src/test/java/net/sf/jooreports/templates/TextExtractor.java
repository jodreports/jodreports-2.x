package net.sf.jooreports.templates;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.sf.jooreports.opendocument.OpenDocumentArchive;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Simple tool to extract text from an OpenDocument archive
 * <p>
 * Images are replaced with a placeholder text in the form <code>[img:Pictures/image-path.png]</code>. 
 */
public class TextExtractor extends DefaultHandler {

	private static final char LF = '\n';
	private static final char LS = '\u2028';
	private static final String SPACE = " ";

	private final Writer writer;
	private StringBuffer paragraphText;

	private TextExtractor(Writer writer) {
		this.writer = writer;
	}

	public static String extractTextAsString(File openDocumentFile) throws IOException, SAXException {
		ZipFile zipFile = new ZipFile(openDocumentFile);
		ZipEntry contentEntry = zipFile.getEntry(OpenDocumentArchive.ENTRY_CONTENT);
		InputStream inputStream = zipFile.getInputStream(contentEntry);
		StringWriter stringWriter = new StringWriter();
		extractText(inputStream, stringWriter);
		inputStream.close();
		return stringWriter.toString();
	}

	private static void extractText(InputStream inputStream, Writer writer) throws IOException, SAXException {
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		SAXParser parser;
		try {
			parser = parserFactory.newSAXParser();
		} catch (ParserConfigurationException parserConfigurationException) {
			throw new SAXException(parserConfigurationException);
		}
		DefaultHandler handler = new TextExtractor(writer);
		parser.parse(inputStream, handler);
		inputStream.close();
		writer.flush();
	}

	private void write(String text) throws SAXException {
		try {
			writer.write(text);				
		} catch (IOException ioException) {
			throw new SAXException(ioException);
		}		
	}

	private String collapseWhiteSpace(String text) {
		if (text == null) {
			return "";
		}
		return text.replaceAll("\\s+", SPACE).trim().replace(LS, LF);
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if ("text:p".equals(qName)) {
			paragraphText = new StringBuffer();
		} else if ("draw:frame".equals(qName)) {
			String width = attributes.getValue("svg:width");
			String height = attributes.getValue("svg:height");
			if (width != null && height != null) {
				write("[frame:" + width + "," + height + "]");
			}
		} else if ("draw:image".equals(qName)) {
			String href = attributes.getValue("xlink:href");
			if (href != null) {
				write("[img:" + href + "]");
			}
		}
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		if ("text:p".equals(qName)) {
			if (paragraphText == null) {
				System.err.println("paragraphText is null!");
				//FIXME this happens when we have nested text:p tags
			} else {
				write(collapseWhiteSpace(paragraphText.toString()) + LF);				
				paragraphText = null;
			}
		} else if ("text:line-break".equals(qName)) {
			// HACK: line-breaks are first inserted as LS
			// and then replaced with LF *after* collapsing whitespace
			paragraphText.append(LS);
		}
	}

	public void characters(char[] characters, int start, int length) throws SAXException {
		if (paragraphText != null) {
			paragraphText.append(characters, start, length);
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println(extractTextAsString(new File("/home/mirko/tmp/line-breaks.odt")));
	}
}
