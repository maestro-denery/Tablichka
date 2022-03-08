package dev.tablight.packer.base;

import java.nio.file.Path;
import java.util.zip.ZipFile;

import dev.tablight.packer.base.component.ResourcePack;
import dev.tablight.packer.base.internal.TmpUtil;

public final class Packer {
	public static ZipFile generate(ResourcePack resourcePack, Path generatePath) {
		TmpUtil.generateTemp(generatePath);
		var packPng = resourcePack.getPackPNG();
		var assets = resourcePack.getAssets();
		var packMCMeta = resourcePack.getPackMCMeta();
		var language = packMCMeta.getLanguage();


		return null;
	}

	public static ResourcePack parse(ZipFile zipFile) {
		return null;
	}
}
