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

public class TextInputTagTest extends AbstractTemplateTest {

    public void testVariable() throws IOException, DocumentTemplateException {
        File templateFile = getTestFile("textinput-variable-template.odt");
        Map model = new HashMap();
        model.put("name", "Mirko");
        String actual = processTemplate(templateFile, model);
        assertEquals("output content", "Hello Mirko!", actual);
    }
    
    public void testVariableWithSpecialChars() throws IOException, DocumentTemplateException {
        File templateFile = getTestFile("textinput-variable-special-template.odt");
        Map model = new HashMap();
        model.put("name", "You&Me");
        String actual = processTemplate(templateFile, model);
        assertEquals("output content", "Hello You&Me! He&She!", actual);
    }

    public void testDirective() throws IOException, DocumentTemplateException {
        File templateFile = getTestFile("textinput-directive-template.odt");
        Map model = new HashMap();
        model.put("name", "Mirko");
        String actual = processTemplate(templateFile, model);
        assertEquals("output content", "Hello Mr. Mirko!", actual);
    }

    public void testDirectivesInOneLine() throws IOException, DocumentTemplateException {
        File templateFile = getTestFile("textinput-directives-oneline-template.odt");
        Map model = new HashMap();
        model.put("name", "Mirko");
        model.put("showname", "Yes");
        String actual = processTemplate(templateFile, model);
        assertEquals("output content", "Hello Mirko!", actual);
    }

    public void testDirectivesInSplitLine() throws IOException, DocumentTemplateException {
        File templateFile = getTestFile("textinput-directives-splitline-template.odt");
        Map model = new HashMap();
        model.put("name", "Mirko");
        model.put("showname", "Yes");
        String actual = processTemplate(templateFile, model);
        assertEquals("output content", "Hello Mirko!", actual);
    }

    public void testDirectiveWithSpecialChars() throws IOException, DocumentTemplateException {
        File templateFile = getTestFile("textinput-directives-special-chars-template.odt");
        Map model = new HashMap();
        model.put("cond1", "pass");
        model.put("cond2", "pass");
        String actual = processTemplate(templateFile, model);
        assertEquals("output content", "All passed!", actual);
    }

    public void testDirectivesSwitch() throws IOException, DocumentTemplateException {
        File templateFile = getTestFile("textinput-directives-switch-template.odt");
        Map model = new HashMap();
        model.put("option", "2");
        String actual = processTemplate(templateFile, model);
        assertEquals("output content", "Two", actual);
    }

}
