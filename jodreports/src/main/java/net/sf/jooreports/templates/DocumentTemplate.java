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
import java.io.OutputStream;
import java.util.Map;

import net.sf.jooreports.templates.xmlfilters.XmlEntryFilter;

public interface DocumentTemplate {

	public interface ContentWrapper {

		public String wrapContent(String content);

	}

	/**
	 * Set which XML entries in the ODT template can contain templating instructions.
	 * <p>
	 * By default they are <i>content.xml</i> and <i>styles.xml</i>.
	 * <p>
	 * To add all possible XML entries use
	 * <pre>
	 * template.setXmlEntries(new String[] {
	 *     "content.xml",
	 *     "meta.xml",
	 *     "settings.xml",
	 *     "styles.xml"
	 * });
	 * </pre>
	 * 
	 * @param xmlEntries
	 */
	public void setXmlEntries(String[] xmlEntries);

	/**
	 * Set a custom list of filters that will be used to pre-process the template.
	 * <p>
	 * The default filters are (in that order) <i>TextInputTagFilter</i>,
	 * <i>ScriptTagFilter</i>,<i>DynamicImageFilter</i>.
	 * 
	 * @param xmlEntryFilters
	 */
	public void setXmlEntryFilters(XmlEntryFilter[] xmlEntryFilters);
	
	/**
	 * Hook to set custom FreeMarker directives on each XML entry.
	 * <p>
	 * The default implementation escapes XML entities and converts
	 * newline characters into <i>line-break</i> tags. 
	 * 
	 * @param contentWrapper
	 */
    public void setContentWrapper(ContentWrapper contentWrapper);

    public void setOpenDocumentSettings(Map openDocumentSettings);
    
    public Map getConfigurations();
    
    /**
     * Merge the data model into this template and create the output document.
     * 
     * @param model
     * @param output
     * @throws IOException
     * @throws DocumentTemplateException
     */
    public void createDocument(Object model, OutputStream output) throws IOException, DocumentTemplateException;

}
