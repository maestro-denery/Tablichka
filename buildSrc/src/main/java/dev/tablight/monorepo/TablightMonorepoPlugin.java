/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package dev.tablight.monorepo;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

import java.util.Set;

/**
 * Highly WIP, will be developed later.
 */
public class TablightMonorepoPlugin implements Plugin<Project> {
	@Override
	public void apply(Project project) {
		final TaskContainer tasks = project.getTasks();
		final TablightMonorepoExtension extension = project.getExtensions()
				.create(TablightMonorepoExtension.class, TablightMonorepoExtension.EXTENSION_NAME, TablightMonorepoExtension.class, project);
		final Set<DevEnv> devEnvs = extension.getEnvironments().get();
		//project.getDependencies().add("paperweightDevBundle", "dev.tablight.tablightpaper:1.18.2-R0.1-SNAPSHOT");
		tasks.register("setupTablightPaperDev", SetupDevelopmentTask.class, setupDevelopmentTask -> {
			for (DevEnv devEnv : devEnvs) {
				switch (devEnv) {
					case PLUGIN -> {

					}
					case FABRIC -> {

					}
					case TABLIGHT_PAPER -> {

					}
				}
			}
		});
	}
}
