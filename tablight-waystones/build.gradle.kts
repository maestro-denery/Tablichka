apply(plugin = "scala")

dependencies {
    compileOnly(project(":paper-plugin:misc-lib"))
    compileOnly("com.github.LoneDev6:api-itemsadder:3.0.0")
    compileOnly("com.comphenix.protocol:ProtocolLib:4.8.0")
    implementation("net.wesjd:anvilgui:1.5.3-SNAPSHOT")
}

bukkit {
    name = "waystones"
    main = "com.danikvitek.waystones.WayStonesPlugin"
    apiVersion = "1.18"
    depend = listOf("ItemsAdder", "ProtocolLib", "scala-support-lib", "misc-lib")
    authors = listOf("Danik_Vitek")
    description = """This plugin lets players to create waystones. 
        After discovering a few waystones, player will be able to teleport from one to another.
        Teleportation is visualized in several ways:
        - far presence (foreign players will see the position of those who is going to teleport to their waystone),
        - destination preview (phantom blocks ae shown to the player, displaying the scene, that is located
        at the destination point)."""
}
