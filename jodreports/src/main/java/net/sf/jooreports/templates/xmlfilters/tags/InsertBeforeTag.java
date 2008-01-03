package net.sf.jooreports.templates.xmlfilters.tags;

import net.sf.jooreports.templates.xmlfilters.AbstractInsertTag;
import nu.xom.Element;
import nu.xom.Text;

public class InsertBeforeTag extends AbstractInsertTag implements JooScriptTag {

	public void process(Element scriptElement, Element tagElement) {
		Text text = new Text(tagElement.getValue());
		insertBefore(scriptElement, tagElement, text);
	}
}
