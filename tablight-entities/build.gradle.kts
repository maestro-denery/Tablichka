import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

dependencies {
    compileOnly(project(":paper-plugin:misc-lib"))
    compileOnly(project(":paper-plugin:tablight-entity-registry-api"))
    compileOnly(project(":paper-plugin:tablight-llapi"))
    compileOnly(project(":library:data-addons"))
}

bukkit {
    name = "entities"
    main = "dev.tablight.entities.EntitiesPlugin"
    version = "a1.0"
    apiVersion = "1.18"
    description = "Plugin adding Entities for TabLight"
    authors = listOf("Denery")
    depend = listOf("misc-lib", "tablight-entity-registry-api", "tablight-dependency-manager")
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
