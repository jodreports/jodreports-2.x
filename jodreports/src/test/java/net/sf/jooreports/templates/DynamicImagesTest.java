package net.sf.jooreports.templates;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import net.sf.jooreports.templates.image.ClasspathImageSource;
import net.sf.jooreports.templates.image.FileImageSource;
import net.sf.jooreports.templates.image.ImageSource;
import net.sf.jooreports.templates.image.RenderedImageSource;

public class DynamicImagesTest extends AbstractTemplateTest {

	public void testOrderForm() throws Exception {
		File templateFile = getTestFile("order-with-images-template.odt");
		
		ImageSource red = new RenderedImageSource(ImageIO.read(new File("src/test/resources/red.png")));
		ImageSource blue = new FileImageSource("src/test/resources/blue.png");
		ImageSource blue2 = new ClasspathImageSource("blue.png");
		
		Map model = new HashMap();
		List items = new ArrayList();
		Map item1 = new HashMap();
		item1.put("description", "First Item");
		item1.put("quantity", "20");
		item1.put("picture", red);
		items.add(item1);
		Map item2 = new HashMap();
		item2.put("description", "Second Item");
		item2.put("quantity", "15");
		item2.put("picture", blue);
		items.add(item2);
		Map item3 = new HashMap();
		item3.put("description", "Third Item");
		item3.put("quantity", "50");
		item3.put("picture", red);
		items.add(item3);
		Map item4 = new HashMap();
		item4.put("description", "Fourth Item");
		item4.put("quantity", "20");
		item4.put("picture", blue2);
		items.add(item4);
		model.put("items", items);
		
		String content = processTemplate(templateFile, model);
		
		String expected =
			"Order Form\n" +
			"\n" +
			"Picture\n" +
			"Description\n" + 
			"Quantity\n" +
			"[img:Pictures/dynamic-image-1.png]\n" +
			"First Item\n" +
			"20\n" +
			"[img:Pictures/dynamic-image-2.png]\n" +
			"Second Item\n" +
			"15\n" +
			"[img:Pictures/dynamic-image-1.png]\n" +
			"Third Item\n" +
			"50\n" +
			"[img:Pictures/dynamic-image-3.png]\n" +
			"Fourth Item\n" +
			"20";			
		assertEquals("incorrect output", expected, content);		
	}

	public void testOldScriptOrderForm() throws Exception {
		File templateFile = getTestFile("order-with-images-old-script-template.odt");
		
		ImageSource red = new RenderedImageSource(ImageIO.read(new File("src/test/resources/red.png")));
		ImageSource blue = new FileImageSource("src/test/resources/blue.png");
		ImageSource blue2 = new ClasspathImageSource("blue.png");
		
		Map model = new HashMap();
		List items = new ArrayList();
		Map item1 = new HashMap();
		item1.put("description", "First Item");
		item1.put("quantity", "20");
		item1.put("picture", red);
		items.add(item1);
		Map item2 = new HashMap();
		item2.put("description", "Second Item");
		item2.put("quantity", "15");
		item2.put("picture", blue);
		items.add(item2);
		Map item3 = new HashMap();
		item3.put("description", "Third Item");
		item3.put("quantity", "50");
		item3.put("picture", red);
		items.add(item3);
		Map item4 = new HashMap();
		item4.put("description", "Fourth Item");
		item4.put("quantity", "20");
		item4.put("picture", blue2);
		items.add(item4);
		model.put("items", items);
		
		String content = processTemplate(templateFile, model);
		
		String expected =
			"Order Form\n" +
			"\n" +
			"Picture\n" +
			"Description\n" + 
			"Quantity\n" +
			"[img:Pictures/dynamic-image-1.png]\n" +
			"First Item\n" +
			"20\n" +
			"[img:Pictures/dynamic-image-2.png]\n" +
			"Second Item\n" +
			"15\n" +
			"[img:Pictures/dynamic-image-1.png]\n" +
			"Third Item\n" +
			"50\n" +
			"[img:Pictures/dynamic-image-3.png]\n" +
			"Fourth Item\n" +
			"20";			
		assertEquals("incorrect output", expected, content);		
	}
	
	public void testOrderFormWithImageUrl() throws Exception {
		File templateFile = getTestFile("order-with-images-template.odt");
		
		ImageSource blue = new FileImageSource("src/test/resources/blue.png");

		Map model = new HashMap();
		List items = new ArrayList();
		Map item1 = new HashMap();
		item1.put("description", "First Item");
		item1.put("quantity", "20");
		item1.put("picture", blue);
		items.add(item1);
		Map item2 = new HashMap();
		item2.put("description", "Second Item");
		item2.put("quantity", "15");
		item2.put("picture", "src/test/resources/red.png");
		items.add(item2);
		Map item3 = new HashMap();
		item3.put("description", "Third Item");
		item3.put("quantity", "50");
		item3.put("picture", "");
		items.add(item3);
		Map item4 = new HashMap();
		item4.put("description", "Fourth Item");
		item4.put("quantity", "20");
		item4.put("picture", null);
		items.add(item4);
		model.put("items", items);
		
		String content = processTemplate(templateFile, model);
		
		String expected =
			"Order Form\n" +
			"\n" +
			"Picture\n" +
			"Description\n" + 
			"Quantity\n" +
			"[img:Pictures/dynamic-image-1.png]\n" +
			"First Item\n" +
			"20\n" +
			"[img:Pictures/dynamic-image-2.png]\n" +
			"Second Item\n" +
			"15\n" +
			"[img:Pictures/1000000000000028000000145B20E0B1.png]\n" +
			"Third Item\n" +
			"50\n" +
			"[img:Pictures/1000000000000028000000145B20E0B1.png]\n" +
			"Fourth Item\n" +
			"20";			
		assertEquals("incorrect output", expected, content);		
	}

	public void testOrderFormWithImageResize() throws Exception {
		File templateFile = getTestFile("order-with-images-resize-template.odt");
		
		ImageSource red = new RenderedImageSource(ImageIO.read(new File("src/test/resources/red.png")));
		ImageSource blue = new FileImageSource("src/test/resources/blue.png");
		ImageSource blue2 = new ClasspathImageSource("blue.png");

		Map model = new HashMap();
		List items = new ArrayList();
		Map item1 = new HashMap();
		item1.put("description", "First Item");
		item1.put("quantity", "20");
		item1.put("picture", red);
		items.add(item1);
		Map item2 = new HashMap();
		item2.put("description", "Second Item");
		item2.put("quantity", "15");
		item2.put("picture", blue);
		items.add(item2);
		Map item3 = new HashMap();
		item3.put("description", "Third Item");
		item3.put("quantity", "50");
		item3.put("picture", red);
		items.add(item3);
		Map item4 = new HashMap();
		item4.put("description", "Fourth Item");
		item4.put("quantity", "20");
		item4.put("picture", blue2);
		items.add(item4);
		model.put("items", items);
		
		String content = processTemplate(templateFile, model);
		
		String expected =
			"Order Form\n" +
			"\n" +
			"Picture\n" +
			"Description\n" + 
			"Quantity\n" +
			"[img:Pictures/dynamic-image-1.png]\n" +
			"First Item\n" +
			"20\n" +
			"[img:Pictures/dynamic-image-2.png]\n" +
			"Second Item\n" +
			"15\n" +
			"[img:Pictures/dynamic-image-1.png]\n" +
			"Third Item\n" +
			"50\n" +
			"[img:Pictures/dynamic-image-3.png]\n" +
			"Fourth Item\n" +
			"20";			
		assertEquals("incorrect output", expected, content);		
	}
}
