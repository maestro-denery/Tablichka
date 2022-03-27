dependencies {
    compileOnly(project(":paper-plugin:misc-lib"))
    compileOnly(project(":paper-plugin:tablight-llapi"))
    compileOnly(project(":library:data-addons"))
}

bukkit {
    name = "tablight-entity-registry-api"
    main = "dev.tablight.common.impl.entityregistry.EntityRegistryPlugin"
    description = "An API for registering and manipulating entites."
    apiVersion = "1.18"
    depend = listOf("misc-lib", "tablight-dependency-manager", "tablight-llapi")
    authors = listOf("Denery")
}
