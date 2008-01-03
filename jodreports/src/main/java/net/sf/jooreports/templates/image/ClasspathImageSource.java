package net.sf.jooreports.templates.image;

import java.io.IOException;
import java.io.InputStream;

public class ClasspathImageSource extends AbstractInputStreamImageSource implements ImageSource {

	private String resourceName;

	public ClasspathImageSource(String resourceName) {
		this.resourceName = resourceName;
	}

	protected InputStream getInputStream() throws IOException {
		return getClass().getClassLoader().getResourceAsStream(resourceName);
	}
}
