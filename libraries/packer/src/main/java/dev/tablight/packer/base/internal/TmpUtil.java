package dev.tablight.packer.base.internal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import org.apache.commons.io.FileUtils;

public final class TmpUtil {
	public static Optional<File> generateTemp(Path path) {
		final File tmp = new File(path.toString() + "/.tmp");
		if (tmp.exists()) {
			try {
				FileUtils.deleteDirectory(tmp);
			} catch (IOException e) {
				throw new RuntimeException("Couldn't delete temp directory!", e);
			}
		}
		if (tmp.mkdir()) {
			return Optional.of(tmp);
		} else {
			return Optional.empty();
		}
	}
}
