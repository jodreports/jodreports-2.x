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
import java.util.Arrays;
import java.util.Iterator;

import net.sf.jooreports.opendocument.OpenDocumentArchive;
import net.sf.jooreports.templates.DocumentTemplate.ContentWrapper;
import net.sf.jooreports.templates.xmlfilters.DynamicImageFilter;
import net.sf.jooreports.templates.xmlfilters.ScriptTagFilter;
import net.sf.jooreports.templates.xmlfilters.TextInputTagFilter;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.ParsingException;
import nu.xom.Serializer;

import org.apache.commons.io.output.ByteArrayOutputStream;

class TemplatePreProcessor {

	private static final String UTF_8 = "UTF-8";

	private String[] xmlEntries;
	private ContentWrapper contentWrapper;

	private final TextInputTagFilter textInputTagFilter = new TextInputTagFilter();
	private final ScriptTagFilter scriptTagFilter = new ScriptTagFilter();
	private final DynamicImageFilter dynamicImageFilter = new DynamicImageFilter();
	
	public TemplatePreProcessor(String[] xmlEntries, ContentWrapper contentWrapper) {
		this.xmlEntries = xmlEntries;
		this.contentWrapper = contentWrapper;
	}

	public void process(OpenDocumentArchive archive) throws DocumentTemplateException, IOException {
		for (Iterator it = archive.getEntryNames().iterator(); it.hasNext();) {
			String entryName = (String) it.next();
			if (Arrays.binarySearch(xmlEntries, entryName) >= 0) {
				InputStream inputStream = archive.getEntryInputStream(entryName);
				OutputStream outputStream = archive.getEntryOutputStream(entryName);
				applyXmlFilters(inputStream, outputStream);
				inputStream.close();
				outputStream.close();
			}
		}
	}

//	public void handleEntry(String entryName, InputStream input, Object model, OutputStream output) throws IOException, DocumentTemplateException {
//        if (Arrays.binarySearch(xmlEntries, entryName) >= 0) {
//    		ByteArrayOutputStream filteredOutput = new ByteArrayOutputStream();
//    		applyXmlFilters(input, filteredOutput);
//    		String filteredContent = new String(filteredOutput.toByteArray(), UTF_8);
//    		String wrappedContent = contentWrapper.wrapContent(filteredContent);
//    		output.write(wrappedContent.getBytes(UTF_8));
//        } else {
//        	IOUtils.copy(input, output);
//        }
//	}

	private void applyXmlFilters(InputStream input, OutputStream output) throws DocumentTemplateException, IOException {
		Builder builder = new Builder();
		Document document = null;
		try {
			document = builder.build(input);
		} catch (ParsingException parsingException) {
			throw new DocumentTemplateException(parsingException);
		}

		textInputTagFilter.doFilter(document);
		scriptTagFilter.doFilter(document);
		dynamicImageFilter.doFilter(document);
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		Serializer serializer = new Serializer(byteArrayOutputStream, UTF_8);
		serializer.write(document);

		output.write(contentWrapper.wrapContent(byteArrayOutputStream.toString(UTF_8)).getBytes(UTF_8));
	}
}
