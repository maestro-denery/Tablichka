package dev.tablight.common.api.llapi;

public final class VersionedClasses {

	private static VersionedClasses instance;
	public static VersionedClasses getInstance() {
		if (instance == null) instance = new VersionedClasses();
		return instance;
	}

	private VersionedClasses() {}
	private final String version = "v1_18_R2";
	private final Class<?> craftWorld;

	{
		try {
			craftWorld = Class.forName("org.bukkit.craftbukkit." + version + ".CraftWorld");
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError("An Error occurred while initializing NMS / CraftBukkit Classes. Check Your Minecraft Version!");
		}
	}

	public String getVersion() {
		return version;
	}

	public Class<?> getCraftWorld() {
		return craftWorld;
	}

}
