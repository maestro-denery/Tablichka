dependencies {
    compileOnly(project(":data-addons"))
    compileOnly(project(":misc-lib"))
    compileOnly(project(":tablight-llapi"))
}

bukkit {
    name = "tablight-entity-registry-api"
    main = "dev.tablight.common.impl.entityregistry.EntityRegistryPlugin"
    description = "An API for registering and manipulating entites."
    apiVersion = "1.18"
    depend = listOf("misc-lib", "data-addons", "tablight-llapi")
    authors = listOf("Denery")
}
