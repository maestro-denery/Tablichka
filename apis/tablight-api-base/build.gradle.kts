dependencies {
    compileOnly(project(":pl-misc-lib"))
}

bukkit {
    name = "API-Base-V0"
    main = "dev.tablight.common.registry.RegistryPlugin"
    apiVersion = "1.17"
    authors = listOf("Denery")
}
