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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jooreports.converter.DocumentConverter;
import net.sf.jooreports.converter.DocumentFormat;
import net.sf.jooreports.converter.DocumentFormatRegistry;
import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.DocumentTemplateException;
import net.sf.jooreports.templates.UnzippedDocumentTemplate;
import net.sf.jooreports.templates.ZippedDocumentTemplate;
import net.sf.jooreports.templates.images.ImageProvider;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Base class for predefined document generators.
 * 
 * Predefined generators load a template with the same name as the request URI,
 * build a model from the request and generate the response document.
 */
public abstract class AbstractDocumentGenerator extends AbstractController {

    protected abstract Object getModel(HttpServletRequest request) throws Exception;
    
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
        renderDocument(getModel(request), request, response);
        return null;
	}

	/**
	 * Hook for specifying an {@link ImageProvider}.
	 * 
	 * @param model
	 * @return
	 */
    protected ImageProvider getImageProvider(Object model) {
    	return null;
    }

    private Resource getTemplateDirectory(String documentName) throws IOException {
        String directoryName = "WEB-INF/templates/"+ documentName +"-template";
        return getApplicationContext().getResource(directoryName);
    }

    private Resource getTemplateFile(String documentName) throws IOException {
        String templateName = "WEB-INF/templates/"+ documentName +"-template.odt";
        return getApplicationContext().getResource(templateName);
    }

    private void renderDocument(Object model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DocumentConverter converter = (DocumentConverter) getApplicationContext().getBean("documentConverter");
        DocumentFormatRegistry formatRegistry = (DocumentFormatRegistry) getApplicationContext().getBean("documentFormatRegistry");
        String outputExtension = FilenameUtils.getExtension(request.getRequestURI());
		DocumentFormat outputFormat = formatRegistry.getFormatByFileExtension(outputExtension);
        if (outputFormat == null) {
        	throw new ServletException("unsupported output format: "+ outputExtension);
        }
        File templateFile = null;
        String documentName = FilenameUtils.getBaseName(request.getRequestURI());
        Resource templateDirectory = getTemplateDirectory(documentName);
        if (templateDirectory.exists()) {
            templateFile = templateDirectory.getFile();
        } else {
            templateFile = getTemplateFile(documentName).getFile();
            if (!templateFile.exists()) {
                throw new ServletException("template not found: "+ documentName);
            }
        }
        
        DocumentTemplate template = null;
        if (templateFile.isDirectory()) {
            template = new UnzippedDocumentTemplate(templateFile);
        } else {
            template = new ZippedDocumentTemplate(new FileInputStream(templateFile));        
        }
        
        ByteArrayOutputStream odtOutputStream = new ByteArrayOutputStream();
        try {
		    ImageProvider imageProvider = getImageProvider(model);
			template.createDocument(model, odtOutputStream, imageProvider);
		} catch (DocumentTemplateException exception) {
		    throw new ServletException(exception);
		}
        response.setContentType(outputFormat.getMimeType());
        response.setHeader("Content-Disposition", "inline; filename="+ documentName +"."+ outputFormat.getFileExtension());
        
        if ("odt".equals(outputFormat.getFileExtension())) {
        	// no need to convert
	        response.getOutputStream().write(odtOutputStream.toByteArray());
        } else {
	    	ByteArrayInputStream odtInputStream = new ByteArrayInputStream(odtOutputStream.toByteArray());
	    	DocumentFormat inputFormat = formatRegistry.getFormatByFileExtension("odt");
	        converter.convert(odtInputStream, inputFormat, response.getOutputStream(), outputFormat);
        }
    }

}
