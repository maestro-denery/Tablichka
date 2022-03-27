dependencies {
    compileOnly(project(":library:admixtures"))
}

bukkit {
    name = "tablight-llapi"
    main = "dev.tablight.common.impl.llapi.LLAPIPlugin"
    description = "An API for low-level interaction with Minecraft."
    depend = listOf("tablight-dependency-manager")
    apiVersion = "1.18"
    authors = listOf("Denery")
}
