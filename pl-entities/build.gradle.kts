import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

dependencies {
    compileOnly(project(":pl-misc-lib"))
    compileOnly(project(":pl-tablight-entity-registry-api-v0"))
    compileOnly(project(":pl-scala-support-lib"))
    compileOnly("com.ticxo.modelengine:api:R2.2.0")
    compileOnly("tf.tofu:tofu-core_2.13:0.10.3")
}

bukkit {
    name = "Entities"
    main = "org.foton.entities.FotonEntities"
    version = "a1.0"
    apiVersion = "1.17"
    authors = listOf("Denery")
    depend = listOf("ModelEngine", "EntityRegistry", "ScalaSupport")
    commands {
        register("erspawn") {
            description = "Spawn entity if its ID is registered in EntityRegistryLib"
            usage = "/<command>"
        }
    }

    permissions {
        register("foton.commands.erspawn") {
            description = "Player can use the erspawn command"
            default = BukkitPluginDescription.Permission.Default.OP
        }
    }
}
