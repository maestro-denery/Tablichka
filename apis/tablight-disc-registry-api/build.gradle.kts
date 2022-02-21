dependencies {
    compileOnly(project(":misc-lib"))
}

bukkit {
    name = "tablight-disc-registry-api"
    main = "com.danikvitek.discregistry.DiscRegistryPlugin"
    description = "API for registering Discs"
    apiVersion = "1.17"
    depend = listOf("misc-lib")
    authors = listOf("Danik_Vitek")
}
