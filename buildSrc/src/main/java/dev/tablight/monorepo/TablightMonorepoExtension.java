/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package dev.tablight.monorepo;

import org.gradle.api.Project;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.SetProperty;

public abstract class TablightMonorepoExtension {
	public static final String EXTENSION_NAME = "tablight";

	public TablightMonorepoExtension(final Project project) {

	}

	abstract SetProperty<DevEnv> getEnvironments();
	abstract Property<Project> getPluginProject();
	abstract Property<Project> getTablightPaperProject();
	abstract Property<Project> getFabricProject();
}
