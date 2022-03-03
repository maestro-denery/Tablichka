dependencies {
    compileOnly(project(":tablight-base-api"))
    compileOnly(project(":misc-lib"))
    compileOnly(project(":tablight-llapi"))
}

bukkit {
    name = "tablight-entity-registry-api"
    main = "dev.tablight.common.impl.entityregistry.EntityRegistryPlugin"
    description = "An API for registering and manipulating entites."
    apiVersion = "1.17"
    depend = listOf("misc-lib", "tablight-base-api")
    authors = listOf("Denery")
}
