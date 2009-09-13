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
import java.util.HashMap;
import java.util.Map;

/**
 * test input field tags <tt>&lt;text:text-input text-description="JOOScript"&gt;...&lt;/text:text-input&gt;</tt>
 */
public class TextInputTagTest extends AbstractTemplateTest {

	/**
	 * input field with content <tt>$name</tt>
	 */
    public void testVariable() throws IOException, DocumentTemplateException {
        File templateFile = getTestFile("textinput-variable-template.odt");
        Map model = new HashMap();
        model.put("name", "Mirko");
        String actual = processTemplate(templateFile, model);
        assertEquals("output content", "Hello Mirko!", actual);
    }
    
	/**
	 * input field with content <tt>[#assign title='Mr.']</tt>
	 */
    public void testDirective() throws IOException, DocumentTemplateException {
        File templateFile = getTestFile("textinput-directive-template.odt");
        Map model = new HashMap();
        model.put("name", "Mirko");
        String actual = processTemplate(templateFile, model);
        assertEquals("output content", "Hello Mr. Mirko!", actual);
    }

	/**
	 * input fields for directive in one line<p> 
	 * <tt>[#if showname=='Yes']...[/#if]</tt>
	 */
    public void testDirectivesInOneLine() throws IOException, DocumentTemplateException {
        File templateFile = getTestFile("textinput-directives-oneline-template.odt");
        Map model = new HashMap();
        model.put("name", "Mirko");
        model.put("showname", "Yes");
        String actual = processTemplate(templateFile, model);
        assertEquals("output content", "Hello Mirko!", actual);
    }

	/**
	 * input fields for directive in split lines<p>
	 * <tt>[#if showname='Yes']</tt><br>
	 * ...<br>
	 * <tt>[#else]</tt><br>
	 * ...<br>
	 * <tt>[/#if]</tt><br>
	 */
    public void testDirectivesInSplitLine() throws IOException, DocumentTemplateException {
        File templateFile = getTestFile("textinput-directives-splitline-template.odt");
        Map model = new HashMap();
        model.put("name", "Mirko");
        model.put("showname", "Yes");
        String actual = processTemplate(templateFile, model);
        assertEquals("output content", "Hello Mirko!", actual);
    }

	/**
	 * input field for directive contains "&amp;"<p> 
	 * <tt>[#if (cond1=='pass' && cond2=='pass')]</tt>
	 */
    public void testDirectiveWithSpecialChars() throws IOException, DocumentTemplateException {
        File templateFile = getTestFile("textinput-directives-special-chars-template.odt");
        Map model = new HashMap();
        model.put("cond1", "pass");
        model.put("cond2", "pass");
        String actual = processTemplate(templateFile, model);
        assertEquals("output content", "All passed!", actual);
    }

	/**
	 * input fields for directive "switch"<p>
	 * <tt>[#switch option][#case '1']</tt><br>
	 * ...<br>
	 * <tt>[#break]</tt><br>
	 * ...<br>
	 * <tt>[/#switch]</tt><br>
	 */
    public void testDirectivesSwitch() throws IOException, DocumentTemplateException {
        File templateFile = getTestFile("textinput-directives-switch-template.odt");
        Map model = new HashMap();
        model.put("option", "2");
        String actual = processTemplate(templateFile, model);
        assertEquals("output content", "Two", actual);
    }

}
