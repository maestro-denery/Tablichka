apply(plugin = "scala")

dependencies {
    compileOnly(project(":misc-lib"))
    compileOnly("com.github.LoneDev6:api-itemsadder:2.4.21")
    compileOnly("com.comphenix.protocol:ProtocolLib:4.7.0")
    implementation("net.wesjd:anvilgui:1.5.3-SNAPSHOT")
}

bukkit {
    name = "WayStones"
    main = "com.danikvitek.waystones.WayStonesPlugin"
    apiVersion = "1.17"
    depend = listOf("ItemsAdder", "ProtocolLib", "ScalaSupport", "Misc")
    authors = listOf("Danik_Vitek")
    description = """This plugin lets players to create waystones. 
        After discovering a few waystones, player will be able to teleport from one to another.
        Teleportation is visualized in several ways:
        - far presence (foreign players will see the position of those who is going to teleport to their waystone),
        - destination preview (phantom blocks ae shown to the player, displaying the scene, that is located
        at the destination point)."""
}