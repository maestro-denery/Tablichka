dependencies {
    compileOnly(project(":admixtures"))
}

bukkit {
    name = "tablight-llapi"
    main = "dev.tablight.common.impl.llapi.LLAPIPlugin"
    description = "An API for low-level interaction with Minecraft."
    depend = listOf("admixtures")
    apiVersion = "1.18"
    authors = listOf("Denery")
}
