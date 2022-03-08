dependencies {
    compileOnly(project(":misc-lib"))
}

bukkit {
    name = "tablight-disc-registry-api"
    main = "com.danikvitek.discregistry.DiscRegistryPlugin"
    description = "An API for registering Discs"
    apiVersion = "1.18"
    depend = listOf("misc-lib")
    authors = listOf("Danik_Vitek")
}
