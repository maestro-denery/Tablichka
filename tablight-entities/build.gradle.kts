import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

dependencies {
    compileOnly(project(":misc-lib"))
    compileOnly(project(":tablight-entity-registry-api"))
    compileOnly("com.ticxo.modelengine:api:R2.2.0")
    compileOnly("tf.tofu:tofu-core_2.13:0.10.3")
}

bukkit {
    name = "Entities"
    main = "dev.tablight.entities.EntitiesPlugin"
    version = "a1.0"
    apiVersion = "1.17"
    description = "Plugin adding Entities for TabLight"
    authors = listOf("Denery")
    depend = listOf("ModelEngine", "EntityRegistry-API")
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
