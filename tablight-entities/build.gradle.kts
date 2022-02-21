import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

dependencies {
    compileOnly(project(":misc-lib"))
    compileOnly(project(":tablight-entity-registry-api"))
}

bukkit {
    name = "entities"
    main = "dev.tablight.entities.EntitiesPlugin"
    version = "a1.0"
    apiVersion = "1.17"
    description = "Plugin adding Entities for TabLight"
    authors = listOf("Denery")
    depend = listOf("misc-lib", "tablight-entity-registry-api")
    commands {
        register("tl-entities") {
            description = "Spawn entity if its ID is registered in EntityRegistryLib"
            usage = "\"/tl-entities spawn <id>\" for spawn"
        }
    }

    permissions {
        register("tablight.commands.tl-entities") {
            description = "Player have a permission to use TabLight's entities"
            default = BukkitPluginDescription.Permission.Default.OP
        }
    }
}
