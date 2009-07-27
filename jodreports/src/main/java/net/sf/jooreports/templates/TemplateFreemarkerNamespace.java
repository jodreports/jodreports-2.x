package net.sf.jooreports.templates;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.sf.jooreports.templates.image.FileImageSource;
import net.sf.jooreports.templates.image.ImageSource;

/**
 * Contains utility methods that can be invoked in the template from FreeMarker. 
 * <p>
 * An instance of this class is automatically made available to the template
 * as a predefined FreeMarker variable named "template". 
 */
public class TemplateFreemarkerNamespace {

	public static final String NAME = "template";
	public static final double sizeScale = 37.8;

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

	public String image(String defaultImageName, ImageSource imageWriter) {
		return image(imageWriter);
	}

	public String image(String defaultImageName, String url){
		if (new File(url).exists()) {
			defaultImageName = image(new FileImageSource(url));
		}
		return defaultImageName;
	}

	public String image(String defaultImageName, Object object){
		if (object!=null) {
			if (object.getClass().isInstance(String.class)) {
				defaultImageName = image(new FileImageSource((String)object));
			} else if (object.getClass().isInstance(ImageSource.class)) {
				defaultImageName = image((ImageSource)object);
			}
		}
		return defaultImageName;
	}

	public String imageWidth(ImageSource imageSource, double maxWidth, double maxHeight, String format){
		double width = getWidth(imageSource);
		double height = getHeight(imageSource);
		
		if (width==0 || height==0) {
			return maxWidth + "cm";
		}
		
		double scaleWidth = (double) width/maxWidth;
		double scaleHeight = (double) height/maxHeight;
		double result = maxWidth;

		if (format.toLowerCase().contains("original")) {
			result = width;
		} else if (format.toLowerCase().contains("shrink")) {
			if (scaleWidth>1 && scaleWidth>scaleHeight) {
				result = maxWidth;
			} else if (scaleHeight>1 && scaleHeight>scaleWidth) {
				result = (double) width/scaleHeight;
			} else {
				result = width;
			}
		} else if (format.toLowerCase().contains("scale")) {
			if (scaleWidth<1 && scaleHeight<1 && scaleWidth<scaleHeight) {
				result = (double) width/scaleHeight;
			} else if (scaleWidth<1 && scaleHeight<1 && scaleHeight<scaleWidth) {
				result = maxWidth;
			} else {
				result = width;
			}
		} else if (format.toLowerCase().contains("fit")) {
			if (scaleWidth>1 && scaleWidth>scaleHeight) {
				result = maxWidth;
			} else if (scaleHeight>1 && scaleHeight>scaleWidth) {
				result = (double)width/scaleHeight;
			} else if (scaleWidth<1 && scaleHeight<1 && scaleWidth<scaleHeight) {
				result = (double)width/scaleHeight;
			} else if (scaleWidth<1 && scaleHeight<1 && scaleHeight<scaleWidth) {
				result = maxWidth;
			} else {
				result = width;
			}
		}

		return result+"cm";
	}

	public String imageWidth(String defaultImageName,ImageSource imageSource, double maxWidth, double maxHeight, String format){
		return imageWidth(imageSource, maxWidth, maxHeight, format);
	}

	public String imageWidth(String defaultImageName, String url, double maxWidth, double maxHeight, String format){
		String result = maxWidth + "cm";
		if (new File(url).exists()) {
			result = imageWidth(new FileImageSource(url), maxWidth, maxHeight, format);
		}
		return result;
	}

	public String imageWidth(String defaultImageName, Object object, double maxWidth, double maxHeight, String format){
		String result = maxWidth + "cm";
		if (object!=null) {
			if (object.getClass().isInstance(String.class)) {
				result = imageWidth(new FileImageSource((String)object), maxWidth, maxHeight, format);
			} else if (object.getClass().isInstance(ImageSource.class)) {
				result = imageWidth((ImageSource)object, maxWidth, maxHeight, format);
			}
		}
		return result;
	}

	public String imageHeight(ImageSource imageSource, double maxWidth, double maxHeight, String format){
		double width = getWidth(imageSource);
		double height = getHeight(imageSource);
		
		if (width==0 || height==0) {
			return maxHeight + "cm";
		}
		
		double scaleWidth = (double) width/maxWidth;
		double scaleHeight = (double) height/maxHeight;
		double result = maxHeight;

		if (format.toLowerCase().contains("original")) {
			result = height;
		} else if (format.toLowerCase().contains("shrink")) {
			if (scaleHeight>1 && scaleHeight>scaleWidth) {
				result = maxHeight;
			} else if (scaleWidth>1 && scaleWidth>scaleHeight) {
				result = (double) height/scaleWidth;
			} else {
				result = height;
			}
		} else if (format.toLowerCase().contains("scale")) {
			if (scaleHeight<1 && scaleWidth<1 && scaleHeight<scaleWidth) {
				result = (double) height/scaleWidth;
			} else if (scaleHeight<1 && scaleWidth<1 && scaleWidth<scaleHeight) {
				result = maxHeight;
			} else {
				result = height;
			}
		} else if (format.toLowerCase().contains("fit")) {
			if (scaleHeight>1 && scaleHeight>scaleWidth) {
				result = maxHeight;
			} else if (scaleWidth>1 && scaleWidth>scaleHeight) {
				result = (double) height/scaleWidth;
			} else if (scaleHeight<1 && scaleWidth<1 && scaleHeight<scaleWidth) {
				result = (double) height/scaleWidth;
			} else if (scaleHeight<1 && scaleWidth<1 && scaleWidth<scaleHeight) {
				result = maxHeight;
			} else {
				result = height;
			}
		}

		return result+"cm";
	}

	public String imageHeight(String defaultImageName,ImageSource imageSource, double maxWidth, double maxHeight, String format){
		return imageHeight(imageSource, maxWidth, maxHeight, format);
	}

	public String imageHeight(String defaultImageName, String url, double maxWidth, double maxHeight, String format){
		String result = maxHeight + "cm";
		if (new File(url).exists()) {
			result = imageHeight(new FileImageSource(url), maxWidth, maxHeight, format);
		}
		return result;
	}

	public String imageHeight(String defaultImageName, Object object, double maxWidth, double maxHeight, String format){
		String result = maxHeight + "cm";
		if (object!=null) {
			if (object.getClass().isInstance(String.class)) {
				result = imageHeight(new FileImageSource((String)object), maxWidth, maxHeight, format);
			} else if (object.getClass().isInstance(ImageSource.class)) {
				result = imageHeight((ImageSource)object, maxWidth, maxHeight, format);
			}
		}
		return result;
	}

	private double getWidth(ImageSource imageSource){
		return (double)imageSource.getWidth()/sizeScale;
	}
	
	private double getHeight(ImageSource imageSource){
		return (double)imageSource.getHeight()/sizeScale;
	}
	
}
