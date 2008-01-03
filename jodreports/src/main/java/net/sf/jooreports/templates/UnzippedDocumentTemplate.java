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
import java.io.IOException;

import freemarker.template.Configuration;

import net.sf.jooreports.opendocument.OpenDocumentArchive;
import net.sf.jooreports.opendocument.OpenDocumentIO;

/**
 * Same as {@link ZippedDocumentTemplate} but in unzipped form.
 */
public class UnzippedDocumentTemplate extends AbstractDocumentTemplate {

	private final OpenDocumentArchive archive;

	public UnzippedDocumentTemplate(File directory, Configuration freemarkerConfiguration) throws IOException {
		super(freemarkerConfiguration);
        if (!(directory.isDirectory() && directory.canRead())) {
            throw new IllegalArgumentException("not a readable directory: "+ directory);
        }        
        archive = OpenDocumentIO.readDirectory(directory);		
	}

	/**
	 * @deprecated since 2.1 use {@link DocumentTemplateFactory#getUnzippedTemplate(File)}
	 */
    public UnzippedDocumentTemplate(File directory) throws IOException {
        if (!(directory.isDirectory() && directory.canRead())) {
            throw new IllegalArgumentException("not a readable directory: "+ directory);
        }        
        archive = OpenDocumentIO.readDirectory(directory);
    }

    protected OpenDocumentArchive getOpenDocumentArchive() {
    	return archive;
    }
}
