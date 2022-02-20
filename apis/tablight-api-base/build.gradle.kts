dependencies {
    compileOnly(project(":misc-lib"))
}

bukkit {
    name = "API-Base"
    main = "dev.tablight.common.BasePlugin"
    description = "A base API for Minecraft all tablight plugins."
    apiVersion = "1.17"
    authors = listOf("Denery")
}
