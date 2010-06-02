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

	/**
	 * template contains input fields:
	 * <tt>[description:field1,content:field one] [description:field2,content:field two]</tt>
	 * @deprecated use {@link TextInputTagTest#testVariable()}
	 */
	public void testVisualFields() throws IOException, DocumentTemplateException {
        File templateFile = getTestFile("visual-fields-template.odt");
        Map model = new HashMap();
        model.put("field1", "First Value");
        model.put("field2", "Second Value");
        String actual = processTemplate(templateFile, model);
        String expected =
        	"1: First Value\n"+
        	"2: Second Value";
        assertEquals(expected, actual);
    }

	/**
	 * template contains <tt>[#setting number_format="00.00"]</tt>
	 */
    public void testScriptWithSetting() throws IOException, DocumentTemplateException {
        File templateFile = getTestFile("visual-script-setting-template.odt");
        Map model = new HashMap();
        model.put("value", new Double("7.5"));
        String actual = processTemplate(templateFile, model);
        assertEquals("Number: 07.50", actual);
    }

	/**
	 * template contains <tt>[#setting number_format="00.00"]</tt> in new JOOScript style
	 */
    public void testNewScriptWithSetting() throws IOException, DocumentTemplateException {
        File templateFile = getTestFile("visual-script-setting-template-2.odt");
        Map model = new HashMap();
        model.put("value", new Double("7.5"));
        String actual = processTemplate(templateFile, model);
        assertEquals("Number: 07.50", actual);
    }

	/**
	 * template contains <tt>[#list items as item]</tt>
	 */
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
        String actual = processTemplate(templateFile, model);
        String expected =
        	"one\n" + "1\n"+
        	"two\n" + "2\n"+
        	"three\n" + "3\n"+
        	"Total\n" + "6";
        assertEquals(expected, actual);
    }

	/**
	 * template contains <tt>[#list items as item]</tt> in new JOOScript style
	 */
    public void testNewScriptForRepeatingTableRow() throws IOException, DocumentTemplateException {
        File templateFile = getTestFile("visual-repeat-table-row-template-2.odt");
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
        String actual = processTemplate(templateFile, model);
        String expected =
        	"one\n" + "1\n"+
        	"two\n" + "2\n"+
        	"three\n" + "3\n"+
        	"Total\n" + "6";
        assertEquals(expected, actual);
    }
    
	/**
	 * template contains <tt>[#if (item.cond1=='yes' && item.cond2=='--')]</tt> in JooScript for directive condition
	 */
    public void testScriptWithSpecialCharsInDirective() throws IOException, DocumentTemplateException {
        File templateFile = getTestFile("visual-script-special-chars-template.odt");
        Map model = new HashMap();
        List items = new ArrayList();
        model.put("items", items);
        Map one = new HashMap();
        one.put("value", new Integer(1));
        one.put("description", "one");
        one.put("cond1", "yes");
        one.put("cond2", "no");
        items.add(one);
        Map two = new HashMap();
        two.put("value", new Integer(2));
        two.put("description", "two");
        two.put("cond1", "yes");
        two.put("cond2", "--");
        items.add(two);
        Map three = new HashMap();
        three.put("value", new Integer(3));
        three.put("description", "three");
        three.put("cond1", "yes");
        three.put("cond2", "no");
        items.add(three);
        String actual = processTemplate(templateFile, model);
        String expected =
        	"one\n" + "1\n"+
        	"two\n" + "2\n"+
        	"three\n" + "3\n"+
        	"Total\n" + "2";
        assertEquals(expected, actual);
    }

    /**
	 * template contains special characters in JooScript for output
	 */
    public void testScriptWithSpecialCharsForOutput() throws IOException, DocumentTemplateException {
        File templateFile = getTestFile("visual-script-special-chars-output-template.odt");
        Map model = new HashMap();
        model.put("value", "<!@#$%^&*-->");
        model.put("JooScriptNullDevice", "fdsfdfs");
        String actual = processTemplate(templateFile, model);
        assertEquals("Value: <!@#$%^&*-->", actual);
    }

	/**
	 * template contains jooscript for changing tag attribute <tt>syntax: @#location \n attributeName=value</tt>
	 */
    public void testScriptForTableSpanRow() throws IOException, DocumentTemplateException {
        File templateFile = getTestFile("visual-table-span-row-template.odt");
        Map model = new HashMap();
        List items = new ArrayList();
        model.put("items", items);
        Map one = new HashMap();
        one.put("col1", "cell 1a");
        one.put("col2", "cell 1b");
        one.put("rowspan", Integer.valueOf(2));
        items.add(one);
        Map two = new HashMap();
        two.put("col1", "cell 2a");
        two.put("col2", "cell 2b");
        two.put("rowspan", Integer.valueOf(0));
        items.add(two);
        Map three = new HashMap();
        three.put("col1", "cell 3a");
        three.put("col2", "cell 3b");
        three.put("rowspan", Integer.valueOf(1));
        items.add(three);
        String actual = processTemplate(templateFile, model);
        String expected =
        	"cell 1a\n"+
			"cell 1b\n"+
			"cell 2b\n"+
			"cell 3a\n"+
			"cell 3b";
        assertEquals(expected, actual);
    }
}
