package net.sf.jooreports.opendocument;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Nodes;
import nu.xom.ParsingException;
import nu.xom.Serializer;
import nu.xom.Text;

/**
 * Represents the settings.xml subdocument
 */
public class SettingsSubDocument {

	private Document document;

	public SettingsSubDocument(InputStream inputStream) throws IOException {
		Builder builder = new Builder();
		try {
			document = builder.build(inputStream);
		} catch (ParsingException parsingException) {
			throw new IllegalArgumentException("inputStream contains invalid XML: " + parsingException.getMessage());
		}
	}

	public void changeSettings(Map openDocumentSettings) {
		Iterator settings = openDocumentSettings.entrySet().iterator();
	    while(settings.hasNext()){
	    	Map.Entry setting = (Map.Entry) settings.next();
			Nodes configNodes = document.query("//config:config-item[@config:name='" + setting.getKey() + "']", 
					OpenDocumentNamespaces.XPATH_CONTEXT);
			for (int nodeIndex = 0; nodeIndex < configNodes.size(); nodeIndex++) {
				Element configElement = (Element) configNodes.get(nodeIndex);
				configElement.removeChildren();
				if (setting.getValue()!=null) {
					configElement.appendChild(new Text((String)setting.getValue()));
				}
			}
	    }
	}

	public void save(OutputStream outputStream) throws IOException {
		Serializer serializer = new Serializer(outputStream, OpenDocumentIO.UTF_8.name());
		serializer.setIndent(1);
		serializer.write(document);
	}
}
