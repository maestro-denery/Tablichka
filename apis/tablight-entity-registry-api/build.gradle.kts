dependencies {
    compileOnly(project(":tablight-api-base"))
    compileOnly(project(":misc-lib"))

    compileOnly("com.ticxo.modelengine:api:R2.2.0")
    compileOnly("de.tr7zw:item-nbt-api-plugin:2.8.0")
    compileOnly("tf.tofu:tofu-core_2.13:0.10.3")
}

bukkit {
    name = "EntityRegistry-API"
    main = "dev.tablight.common.EntityRegistryPlugin"
    description = "An API for registering and manipulating entites."
    apiVersion = "1.17"
    depend = listOf("Misc")
    authors = listOf("Denery")
}
