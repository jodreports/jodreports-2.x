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
package net.sf.jooreports.web.spring.controller;

import java.io.StringReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.xml.sax.InputSource;

import freemarker.ext.dom.NodeModel;

/**
 * A predefined document generator that builds the model from XML data passed
 * in the HTTP parameter named "model".
 */
public class XmlDocumentGenerator extends AbstractDocumentGenerator {
    public static final String HTTP_PARAMETER_NAME = "model";

    protected Object getModel(HttpServletRequest request) throws Exception {
        String xmlData = request.getParameter(HTTP_PARAMETER_NAME);
        if (xmlData == null) {
            throw new ServletException("missing required parameter: "+ HTTP_PARAMETER_NAME);
        }
        return NodeModel.parse(new InputSource(new StringReader(xmlData)));
    }
}
