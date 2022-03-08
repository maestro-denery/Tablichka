package dev.tablight.test;

import dev.tablight.common.api.llapi.MojangMappingsHelper;

import org.junit.jupiter.api.Test;

class MojangMappingsUtilTest {
	MojangMappingsHelper mapUtil = MojangMappingsHelper.getInstance();

	@Test
	void checkField() {
		System.out.println(mapUtil.getSpigotField("net/minecraft/server/level/ServerLevel", "customSpawners", "Ljava/lang/List;"));
	}
}
