dependencies {
    compileOnly(project(":pl-misc-lib"))
    compileOnly(project(":pl-tablight-disc-registry-api-v0"))
}

bukkit {
    name = "Discs"
    main = "org.foton.discs.DiscsLauncher"
    apiVersion = "1.17"
    authors = listOf("Danik_Vitek")
    depend = listOf("DiscRegistry", "Misc")
    description = "Register custom music discs"
}
