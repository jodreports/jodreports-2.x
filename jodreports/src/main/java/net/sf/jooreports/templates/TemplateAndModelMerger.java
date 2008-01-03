//
// JOOReports - The Open Source Java/OpenOffice Report Engine
// Copyright (C) 2004-2006 - Mirko Nasato <mirko@artofsolving.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
// http://www.gnu.org/copyleft/lesser.html
//
package net.sf.jooreports.templates;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import net.sf.jooreports.opendocument.ManifestSubDocument;
import net.sf.jooreports.opendocument.OpenDocumentArchive;
import net.sf.jooreports.templates.image.ImageSource;

import org.apache.commons.io.IOUtils;

import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Merges a pre-processed template with the data model to
 * produce the output document.
 */
class TemplateAndModelMerger {

	private final Configuration freemarkerConfiguration;
	private final String[] xmlEntries;

	public TemplateAndModelMerger(Configuration freemarkerConfiguration, String[] xmlEntries) {
		this.freemarkerConfiguration = freemarkerConfiguration;
		this.xmlEntries = xmlEntries;
	}

	public void process(OpenDocumentArchive archive, Object model) throws IOException, DocumentTemplateException {
		TemplateFreemarkerNamespace predefinedNamespace = new TemplateFreemarkerNamespace();
		
		for (Iterator it = archive.getEntryNames().iterator(); it.hasNext();) {
			String entryName = (String) it.next();
			if (Arrays.binarySearch(xmlEntries, entryName) >= 0) {
				Reader reader = archive.getEntryReader(entryName);
				Writer writer = archive.getEntryWriter(entryName);

				Template template = new Template(entryName, reader, freemarkerConfiguration);
				
				BeansWrapper beansWrapper = new BeansWrapper();
	        	try {
					Environment environment = template.createProcessingEnvironment(model, writer);
					environment.setGlobalVariable(TemplateFreemarkerNamespace.NAME, beansWrapper.wrap(predefinedNamespace));
					environment.process();
				} catch (TemplateException templateException) {
					throw new DocumentTemplateException(templateException);
				} finally {
					IOUtils.closeQuietly(reader);
					IOUtils.closeQuietly(writer);
				}
			}
		}
		
		if (!predefinedNamespace.getImages().isEmpty()) { 
			addRequiredImages(archive, predefinedNamespace.getImages());
		}
	}

	private void addRequiredImages(OpenDocumentArchive archive, Map images) throws IOException {
		InputStream inputStream = archive.getEntryInputStream(OpenDocumentArchive.ENTRY_MANIFEST);
		ManifestSubDocument manifest = new ManifestSubDocument(inputStream);
		inputStream.close();
		
		for (Iterator it = images.keySet().iterator(); it.hasNext();) {
			ImageSource imageWriter = (ImageSource) it.next();
			String imageHref = (String) images.get(imageWriter);
			OutputStream imageOutputStream = archive.getEntryOutputStream(imageHref);
			imageWriter.write(imageOutputStream);
			IOUtils.closeQuietly(imageOutputStream);
			manifest.addFileEntry("image/png", imageHref);
		}

		OutputStream outputStream = archive.getEntryOutputStream(OpenDocumentArchive.ENTRY_MANIFEST);
		manifest.save(outputStream);
		outputStream.close();
	}
}
