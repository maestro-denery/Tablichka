package dev.tablight.packer.base.internal;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class GenerationUtil {
	Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)
			.create();

	public static void generateRPRoot() {
		//bdddef1a6db6e1dbab239641ca60a7419e555fec
	}
}
