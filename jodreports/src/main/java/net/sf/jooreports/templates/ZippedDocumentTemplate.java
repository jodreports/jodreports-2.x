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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import freemarker.template.Configuration;

import net.sf.jooreports.opendocument.OpenDocumentArchive;
import net.sf.jooreports.opendocument.OpenDocumentIO;

/**
 * Class for generating OpenDocument documents from a template and a data model.
 * <p>
 * The template is an "almost regular" OpenDocument file, except that the <i>content.xml</i>
 * and <i>styles.xml</i> entries may contain FreeMarker variables and directives.
 */
public class ZippedDocumentTemplate extends AbstractDocumentTemplate {

    private final OpenDocumentArchive archive;

    public ZippedDocumentTemplate(InputStream inputStream, Configuration freemarkerConfiguration) throws IOException {
    	super(freemarkerConfiguration);
    	archive = OpenDocumentIO.readZip(inputStream);
    }

    /**
     * @deprecated use {@link DocumentTemplateFactory#getTemplate(File)}
     */
    public ZippedDocumentTemplate(File file) throws IOException {
        this(new FileInputStream(file));
    }

    /**
     * @deprecated use {@link DocumentTemplateFactory#getTemplate(InputStream)}
     */
    public ZippedDocumentTemplate(InputStream inputStream) throws IOException {
    	archive = OpenDocumentIO.readZip(inputStream);
    }

    protected OpenDocumentArchive getOpenDocumentArchive() {
    	return archive;
    }
}
