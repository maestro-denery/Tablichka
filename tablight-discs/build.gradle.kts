dependencies {
    compileOnly(project(":paper-plugin:misc-lib"))
    compileOnly(project(":paper-plugin:tablight-disc-registry-api"))
}

bukkit {
    name = "discs"
    main = "org.foton.discs.DiscsLauncher"
    description = "Plugin adding custom music discs."
    apiVersion = "1.18"
    authors = listOf("Danik_Vitek")
    depend = listOf("tablight-dependency-manager", "tablight-disc-registry-api","misc-lib")
}
