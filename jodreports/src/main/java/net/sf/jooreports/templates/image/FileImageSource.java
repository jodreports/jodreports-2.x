package net.sf.jooreports.templates.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileImageSource extends AbstractInputStreamImageSource {

	private File file;

	public FileImageSource(String fileName) {
		this(new File(fileName));
	}

	public FileImageSource(File file) {
		this.file = file;
	}

	protected InputStream getInputStream() throws IOException {
		return new FileInputStream(file);
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileImageSource other = (FileImageSource) obj;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		return true;
	}
}
