dependencies {
    compileOnly(project(":pl-tablight-api-base"))
    compileOnly(project(":pl-scala-support-lib"))
    compileOnly(project(":pl-misc-lib"))

    compileOnly("com.ticxo.modelengine:api:R2.2.0")
    compileOnly("de.tr7zw:item-nbt-api-plugin:2.8.0")
    compileOnly("tf.tofu:tofu-core_2.13:0.10.3")
}

bukkit {
    name = "EntityRegistry-API-V0"
    main = "io.denery.entityregistry.EntityRegistryPlugin"
    apiVersion = "1.17"
    depend = listOf("Misc", "ScalaSupport")
    authors = listOf("Denery")
}
