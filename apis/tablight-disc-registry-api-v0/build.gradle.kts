dependencies {
    compileOnly(project(":pl-misc-lib"))
}

bukkit {
    name = "DiscRegistry"
    main = "com.danikvitek.discregistry.DiscRegistryPlugin"
    apiVersion = "1.17"
    depend = listOf("Misc")
    authors = listOf("Danik_Vitek")
}
