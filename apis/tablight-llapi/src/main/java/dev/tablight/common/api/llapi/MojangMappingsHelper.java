package dev.tablight.common.api.llapi;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import net.fabricmc.mappingio.MappingReader;
import net.fabricmc.mappingio.format.MappingFormat;
import net.fabricmc.mappingio.tree.MappingTree;
import net.fabricmc.mappingio.tree.MemoryMappingTree;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MojangMappingsHelper {
	public static final String SPIGOT_NAMESPACE = "spigot";
	public static final String MOJANG_PLUS_YARN_NAMESPACE = "mojang+yarn";
	
	private static MojangMappingsHelper instance;
	public static MojangMappingsHelper getInstance() {
		if (instance == null) {
			instance = new MojangMappingsHelper();
			try (final @Nullable InputStream mappingsInputStream = 
						 MojangMappingsHelper.class.getClassLoader().getResourceAsStream("mappings/mojang+yarn-spigot-reobf.tiny")) {
				if (mappingsInputStream == null) throw new IllegalStateException("Yarn + Mojang mappings not found in resources.");
				MappingReader.read(new InputStreamReader(mappingsInputStream, StandardCharsets.UTF_8), MappingFormat.TINY_2, instance.memoryMappingTreeCache);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	private MojangMappingsHelper() {}
	private final MemoryMappingTree memoryMappingTreeCache = new MemoryMappingTree();
	
	public MappingTree.FieldMapping getSpigotField(String srcOwnerName, String srcName, String srcDesc) {
		return memoryMappingTreeCache.getField(srcOwnerName, srcName, srcDesc);
	}

	public MemoryMappingTree getMemoryMappingTree() {
		return memoryMappingTreeCache;
	}
}
