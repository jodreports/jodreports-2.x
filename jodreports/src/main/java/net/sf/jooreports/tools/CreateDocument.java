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
package net.sf.jooreports.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.DocumentTemplateFactory;

import org.apache.commons.io.FilenameUtils;

import freemarker.ext.dom.NodeModel;

/**
 * Command line tool to create an ODT document from a template and a data file.
 * <p>
 * The data file can be in XML format or a simple <i>.properties</i> file.
 */
public class CreateDocument {

    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.err.println("USAGE: "+ CreateDocument.class.getName() +" <template-document> <data-file> <output-document>");
            System.exit(0);
        }        
        File templateFile = new File(args[0]);
        File dataFile = new File(args[1]);
        File outputFile = new File(args[2]);

        DocumentTemplateFactory documentTemplateFactory = new DocumentTemplateFactory();
        DocumentTemplate template = documentTemplateFactory.getTemplate(templateFile);
        
        Object model = null;
        String dataFileExtension = FilenameUtils.getExtension(dataFile.getName());
		if (dataFileExtension.equals("xml")) {
			model = NodeModel.parse(dataFile);
        } else if (dataFileExtension.equals("properties")) {
        	Properties properties = new Properties();
        	properties.load(new FileInputStream(dataFile));
        	model = properties;
        } else {
        	throw new IllegalArgumentException("data file must be 'xml' or 'properties'; unsupported type: " + dataFileExtension);
        }
		
		template.createDocument(model, new FileOutputStream(outputFile));
    }
}
