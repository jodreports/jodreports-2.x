package net.sf.jooreports.templates;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import net.sf.jooreports.templates.image.FileImageSource;
import net.sf.jooreports.templates.image.ImageSource;

/**
 * Contains utility methods that can be invoked in the template from FreeMarker. 
 * <p>
 * An instance of this class is automatically made available to the template
 * as a predefined FreeMarker variable named "JOOScript". 
 */
public class TemplateFreemarkerNamespace {

	public static final String NAME = "jooscript";
	private static final Logger log = LoggerFactory.getLogger(TemplateFreemarkerNamespace.class);
	private static final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
	private boolean checkImageExistRequired = true;

	private int imageIndex = 0;
	
	private Map/*<ImageWriter,String>*/ images = new HashMap();

	public TemplateFreemarkerNamespace() {
		decimalFormat.applyPattern("#.##");
	}

	public String getDoubleHyphen() {
		return "--";
	}

	public void applyConfigurations(Map configurations) {
		this.checkImageExistRequired = Configuration.getConfiguration(
				Configuration.SETTING_CHECK_IMAGE_EXIST, configurations);
	}
	
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

	public String image(String defaultImageName, String fileName){
		if (imageExists(fileName)) {
			defaultImageName = image(new FileImageSource(fileName));
		}
		return defaultImageName;
	}

	public String image(String defaultImageName, Object object){
		if (object instanceof String) {
			defaultImageName = image(defaultImageName, (String)object);
		} else if (object instanceof ImageSource) {
			defaultImageName = image((ImageSource)object);
		} else if (object instanceof Element) {
			defaultImageName = image(defaultImageName, ((Element)object).getTextContent());
		}
		return defaultImageName;
	}

	public String imageWidth(ImageSource imageSource, String maxWidthStr, String maxHeightStr, String format){
		return getRatioDimension(imageSource.getWidth(), imageSource.getHeight(), maxWidthStr, maxHeightStr, true, format);
	}
	
	public String imageWidth(String defaultImageName,ImageSource imageSource, String maxWidth, String maxHeight, String format){
		return imageWidth(imageSource, maxWidth, maxHeight, format);
	}

	public String imageWidth(String defaultImageName, String fileName, String maxWidth, String maxHeight, String format){
		String result = maxWidth;
		if (imageExists(fileName)) {
			result = imageWidth(new FileImageSource(fileName), maxWidth, maxHeight, format);
		}
		return result;
	}

	public String imageWidth(String defaultImageName, Object object, String maxWidth, String maxHeight, String format){
		String result = maxWidth;
		if (object instanceof String) {
			result = imageWidth(new FileImageSource((String)object), maxWidth, maxHeight, format);
		} else if (object instanceof ImageSource) {
			result = imageWidth((ImageSource)object, maxWidth, maxHeight, format);
		} else if (object instanceof Element) {
			result = imageWidth(new FileImageSource(((Element)object).getTextContent()), maxWidth, maxHeight, format);
		}
		return result;
	}

	public String imageHeight(ImageSource imageSource, String maxWidthStr, String maxHeightStr, String format){
		return getRatioDimension(imageSource.getHeight(), imageSource.getWidth(), maxHeightStr, maxWidthStr, false, format);
	}

	public String imageHeight(String defaultImageName,ImageSource imageSource, String maxWidth, String maxHeight, String format){
		return imageHeight(imageSource, maxWidth, maxHeight, format);
	}

	public String imageHeight(String defaultImageName, String fileName, String maxWidth, String maxHeight, String format){
		String result = maxHeight;
		if (imageExists(fileName)) {
			result = imageHeight(new FileImageSource(fileName), maxWidth, maxHeight, format);
		}
		return result;
	}

	public String imageHeight(String defaultImageName, Object object, String maxWidth, String maxHeight, String format){
		String result = maxHeight;
		if (object instanceof String) {
			result = imageHeight(new FileImageSource((String)object), maxWidth, maxHeight, format);
		} else if (object instanceof ImageSource) {
			result = imageHeight((ImageSource)object, maxWidth, maxHeight, format);
		} else if (object instanceof Element) {
			result = imageHeight(new FileImageSource(((Element)object).getTextContent()), maxWidth, maxHeight, format);
		}
		return result;
	}

	private String getRatioDimension(int dimX, int dimY, String maxDimXStr, String maxDimYStr, boolean isWidth, String format){
		String result = maxDimXStr;
		if (dimX>0 && dimY>0 && maxDimXStr.length()>2 && maxDimYStr.length()>2) {
			String unit = maxDimXStr.substring(maxDimXStr.length()-2);
			try {
				double maxDimX = Double.parseDouble(maxDimXStr.substring(0, maxDimXStr.length()-2));
				double maxDimY = Double.parseDouble(maxDimYStr.substring(0, maxDimYStr.length()-2));
				double maxRatio = (double) maxDimX / maxDimY;
				double ratio = (double) dimX / dimY;
				if (ratio!=0 && (format.equalsIgnoreCase("Max" + (isWidth?"Height":"Width"))) 
						|| (format.equalsIgnoreCase("fit") && ratio<maxRatio)) {
					result = decimalFormat.format(maxDimY * ratio) + unit;
				}
			} catch (NumberFormatException nfException) {
				log.error("Cannot get image dimension", nfException);
			} catch (ArithmeticException aException) {
				log.error("Cannot divide by 0", aException);
			}
		}
		return result;
	}
	
	private boolean imageExists(String fileName) {
		boolean isFileExist = true;
		if(checkImageExistRequired){
			isFileExist = new File(fileName).exists();
		}
		return isFileExist;
	}

}
