package net.sf.jooreports.templates.image;

import java.io.IOException;
import java.io.InputStream;

public class ClasspathImageSource extends AbstractInputStreamImageSource {

	private String resourceName;

	public ClasspathImageSource(String resourceName) {
		this.resourceName = resourceName;
	}

	protected InputStream getInputStream() throws IOException {
		return getClass().getClassLoader().getResourceAsStream(resourceName);
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((resourceName == null) ? 0 : resourceName.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClasspathImageSource other = (ClasspathImageSource) obj;
		if (resourceName == null) {
			if (other.resourceName != null)
				return false;
		} else if (!resourceName.equals(other.resourceName))
			return false;
		return true;
	}
}
