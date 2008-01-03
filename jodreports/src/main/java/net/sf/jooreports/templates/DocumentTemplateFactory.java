package net.sf.jooreports.templates;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.jooreports.opendocument.OpenDocumentIO;

import freemarker.template.Configuration;

public class DocumentTemplateFactory {

	private final Configuration freemarkerConfiguration;

	public DocumentTemplateFactory() {
		freemarkerConfiguration = new Configuration();		
		freemarkerConfiguration.setTagSyntax(Configuration.SQUARE_BRACKET_TAG_SYNTAX);
		freemarkerConfiguration.setDefaultEncoding(OpenDocumentIO.UTF_8.name());
		freemarkerConfiguration.setOutputEncoding(OpenDocumentIO.UTF_8.name());
	}

	/**
	 * Retrieves the FreeMarker {@link Configuration} for this factory.
	 * <p>
	 * You can use this method to set custom FreeMarker options on the returned
	 * {@link Configuration}, and they will apply to all templates returned by
	 * this factory.
	 * <p>
	 * Any such customizations should be made right after initializaion, before
	 * using this factory to create any template.
	 * 
	 * @return the FreeMarker {@link Configuration} 
	 */
	public Configuration getFreemarkerConfiguration() {
		return freemarkerConfiguration;
	}

	public DocumentTemplate getTemplate(File file) throws IOException {
		return getTemplate(new FileInputStream(file));
	}

	public DocumentTemplate getTemplate(InputStream inputStream) throws IOException {
		return new ZippedDocumentTemplate(inputStream, freemarkerConfiguration);
	}

	public DocumentTemplate getUnzippedTemplate(File templateDir) throws IOException {
		return new UnzippedDocumentTemplate(templateDir, freemarkerConfiguration);
	}
}
