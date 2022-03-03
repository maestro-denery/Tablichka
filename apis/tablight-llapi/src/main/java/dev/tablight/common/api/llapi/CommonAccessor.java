package dev.tablight.common.api.llapi;

public final class CommonAccessor {
	private final String version;
	private final Class<?> craftWorldClass;

	private CommonAccessor(String version) {
		this.version = version;

		try {
			craftWorldClass = Class.forName("org.bukkit.craftbukkit." + version + ".CraftWorld");
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError("An Error occurred while initializing NMS / CraftBukkit Classes. Check Your Minecraft Version!");
		}
	}

	public String getVersion() {
		return version;
	}

	public Class<?> getCraftWorldClass() {
		return craftWorldClass;
	}

	public static CommonAccessor create(String version) {
		return new CommonAccessor(version);
	}
}
