package dev.tablight.packer.base.component;

import java.io.File;

import dev.tablight.packer.base.component.language.Language;

public final class PackMCMeta {
	private File file;
	private int packFormat;
	private String description;
	private Language language;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public int getPackFormat() {
		return packFormat;
	}

	public void setPackFormat(int packFormat) {
		this.packFormat = packFormat;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
}
