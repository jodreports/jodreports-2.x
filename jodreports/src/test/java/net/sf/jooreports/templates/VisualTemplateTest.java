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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisualTemplateTest extends AbstractTemplateTest {

	public void testVisualFields() throws IOException, DocumentTemplateException {
        File templateFile = getTestFile("visual-fields-template.odt");
        Map model = new HashMap();
        model.put("field1", "First Value");
        model.put("field2", "Second Value");
        String actual = processZippedTemplate(templateFile, model);
        String expected =
        	"1: First Value\n"+
        	"2: Second Value";
        assertEquals(expected, actual);
    }

    public void testScriptWithSetting() throws IOException, DocumentTemplateException {
    	// template contains [#setting number_format="00.00"]
        File templateFile = getTestFile("visual-script-setting-template.odt");
        Map model = new HashMap();
        model.put("value", new Double("7.5"));
        String actual = processZippedTemplate(templateFile, model);
        assertEquals("Number: 07.50", actual);
    }

    public void testScriptForRepeatingTableRow() throws IOException, DocumentTemplateException {
        File templateFile = getTestFile("visual-repeat-table-row-template.odt");
        Map model = new HashMap();
        List items = new ArrayList();
        model.put("items", items);
        Map one = new HashMap();
        one.put("value", new Integer(1));
        one.put("description", "one");
        items.add(one);
        Map two = new HashMap();
        two.put("value", new Integer(2));
        two.put("description", "two");
        items.add(two);
        Map three = new HashMap();
        three.put("value", new Integer(3));
        three.put("description", "three");
        items.add(three);
        String actual = processZippedTemplate(templateFile, model);
        String expected =
        	"one\n" + "1\n"+
        	"two\n" + "2\n"+
        	"three\n" + "3\n"+
        	"Total\n" + "6";
        assertEquals(expected, actual);
    }
}
