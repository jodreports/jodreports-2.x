package net.sf.jooreports.templates;

import java.util.HashMap;
import java.util.Map;

import net.sf.jooreports.templates.image.ImageSource;

/**
 * Contains utility methods that can be invoked in the template from FreeMarker. 
 * <p>
 * An instance of this class is automatically made available to the template
 * as a predefined FreeMarker variable named "template". 
 */
public class TemplateFreemarkerNamespace {

	public static final String NAME = "template";

	private int imageIndex = 0;
	
	private Map/*<ImageWriter,String>*/ images = new HashMap();

	public String image(ImageSource imageWriter) {
		if (images.containsKey(imageWriter)) {
			return (String) images.get(imageWriter);
		} else {
			++imageIndex;
			String imageHref = "Pictures/dynamic-image-" + imageIndex + ".png";
			images.put(imageWriter, imageHref);
			return imageHref;
		}
	}

	public Map/*<ImageWriter,String>*/ getImages() {
		return images;
	}
}
