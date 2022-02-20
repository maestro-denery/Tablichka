dependencies {
    compileOnly(project(":misc-lib"))
    compileOnly(project(":tablight-disc-registry-api"))
}

bukkit {
    name = "Discs"
    main = "org.foton.discs.DiscsLauncher"
    description = "Plugin adding Disc in TabLight server."
    apiVersion = "1.17"
    authors = listOf("Danik_Vitek")
    depend = listOf("DiscRegistry", "Misc")
    description = "Register custom music discs"
}
