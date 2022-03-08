package dev.tablight.packer.base.component;

import java.io.File;

public class ResourcePack {
	private PackMCMeta packMCMeta;
	private File packPNG;
	private Assets assets;

	public PackMCMeta getPackMCMeta() {
		return packMCMeta;
	}

	public void setPackMCMeta(PackMCMeta packMCMeta) {
		this.packMCMeta = packMCMeta;
	}

	public File getPackPNG() {
		return packPNG;
	}

	public void setPackPNG(File packPNG) {
		this.packPNG = packPNG;
	}

	public Assets getAssets() {
		return assets;
	}

	public void setAssets(Assets assets) {
		this.assets = assets;
	}
}
