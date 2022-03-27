dependencies {
    minecraft(group = "com.mojang", name = "minecraft", version = project.properties["minecraft_version"].toString())
    mappings(loom.officialMojangMappings())
    modImplementation(group = "net.fabricmc", name = "fabric-loader", version = project.properties["loader_version"].toString())
    modImplementation(group = "net.fabricmc.fabric-api", name = "fabric-api", version = project.properties["fabric_version"].toString())
}

loom {
    runs {
        // Run client with this option!
        create("tablightFabric") {
            environment = "client"
            defaultMainClass = ""
            runDir = "fabricRun"
            client()
        }
    }
}
