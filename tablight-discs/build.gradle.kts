dependencies {
    compileOnly(project(":misc-lib"))
    compileOnly(project(":tablight-disc-registry-api"))
}

bukkit {
    name = "discs"
    main = "org.foton.discs.DiscsLauncher"
    description = "Plugin adding Disc in TabLight server."
    apiVersion = "1.18"
    authors = listOf("Danik_Vitek")
    depend = listOf("tablight-disc-registry-api", "misc-lib")
    description = "Register custom music discs"
}
