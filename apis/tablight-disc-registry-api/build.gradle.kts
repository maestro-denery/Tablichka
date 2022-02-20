dependencies {
    compileOnly(project(":misc-lib"))
}

bukkit {
    name = "DiscRegistry"
    main = "com.danikvitek.discregistry.DiscRegistryPlugin"
    description = "API for registering Discs"
    apiVersion = "1.17"
    depend = listOf("Misc")
    authors = listOf("Danik_Vitek")
}
