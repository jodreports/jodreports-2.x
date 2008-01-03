package net.sf.jooreports.opendocument;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.ParsingException;
import nu.xom.Serializer;

/**
 * Represents the META-INF/manifest.xml subdocument
 */
public class ManifestSubDocument {

	private Document document;

	public ManifestSubDocument(InputStream inputStream) throws IOException {
		Builder builder = new Builder();
		try {
			document = builder.build(inputStream);
		} catch (ParsingException parsingException) {
			throw new IllegalArgumentException("inputStream contains invalid XML: " + parsingException.getMessage());
		}
	}

	public void addFileEntry(String mediaType, String fullPath) {
		Element manifest = document.getRootElement();
		Element fileEntry = new Element("manifest:file-entry", OpenDocumentNamespaces.URI_MANIFEST);
		fileEntry.addAttribute(new Attribute("manifest:media-type", OpenDocumentNamespaces.URI_MANIFEST, "image/png"));
		fileEntry.addAttribute(new Attribute("manifest:full-path", OpenDocumentNamespaces.URI_MANIFEST, fullPath));
		manifest.appendChild(fileEntry);
	}

	public void save(OutputStream outputStream) throws IOException {
		Serializer serializer = new Serializer(outputStream, OpenDocumentIO.UTF_8.name());
		serializer.setIndent(1);
		serializer.write(document);
	}
}
