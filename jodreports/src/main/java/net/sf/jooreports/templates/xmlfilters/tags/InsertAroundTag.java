package net.sf.jooreports.templates.xmlfilters.tags;

import net.sf.jooreports.templates.xmlfilters.AbstractInsertTag;
import nu.xom.Element;
import nu.xom.Text;

public class InsertAroundTag extends AbstractInsertTag implements JooScriptTag {

	public void process(Element scriptElement, Element tagElement) {
		Text beforeText = (Text) tagElement.getChild(0);
		Text afterText = (Text) tagElement.getChild(2);
		insertBefore(scriptElement, tagElement, (Text) beforeText.copy());
		insertAfter(scriptElement, tagElement, (Text) afterText.copy());
	}
}
