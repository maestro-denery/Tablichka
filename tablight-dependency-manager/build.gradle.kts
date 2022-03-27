dependencies {
    project(":library").dependencyProject.subprojects.forEach { pr ->
        implementation(project(pr.path))
    }
}

bukkit {
    name = "tablight-dependency-manager"
    main = "dev.tablight.dependencies.DependencyManager"
    description = "Plugin managing tablight dependencies for other plugins."
    apiVersion = "1.18"
    authors = listOf("Denery")
}
