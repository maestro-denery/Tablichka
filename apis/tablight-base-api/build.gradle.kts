dependencies {
    compileOnly(project(":misc-lib"))
}

bukkit {
    name = "tablight-base-api"
    main = "dev.tablight.common.impl.base.BasePlugin"
    description = "A base API for all tablight plugins."
    apiVersion = "1.17"
    authors = listOf("Denery")
}
