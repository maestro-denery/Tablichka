import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    id("java")
    id("scala")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.papermc.paperweight.userdev") version "1.3.3" apply false
    id("xyz.jpenilla.run-paper") version "1.0.6"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
}

group = "org.tablight"
version = "dev-0"
description = "Main TabLight gameplay mods and plugins."

tasks {
    register("buildAll") {
        subprojects.filter { sub -> sub.name.startsWith("pl-") }
            .map { sub -> sub.tasks.shadowJar }
            .forEach { sub -> dependsOn(sub) }
    }

    runPaper {
        disablePluginJarDetection()
    }

    runServer {
        runDirectory(file("$rootDir/run"))
        subprojects.filter { sub -> sub.name.startsWith("pl-") }.forEach {sub ->
            pluginJars.from(sub.tasks.shadowJar.get().archiveFile)
        }
        minecraftVersion("1.17.1")
    }
}

configure(subprojects.filter { sub -> sub.name.startsWith("pl-") }) {
    apply(plugin = "java")
    apply(plugin = "scala")
    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "io.papermc.paperweight.userdev")
    apply(plugin = "xyz.jpenilla.run-paper")
    apply(plugin = "net.minecrell.plugin-yml.bukkit")

    repositories {
        mavenCentral()
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://oss.sonatype.org/content/groups/public/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://repo.codemc.org/repository/maven-public/")
        maven("https://mvn.lumine.io/repository/maven-public/")
        maven("https://jitpack.io")
        maven("https://repo.dmulloy2.net/repository/public/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/public/")
        maven("https://papermc.io/repo/repository/maven-public/")
    }

    sourceSets {
        main {
            scala.srcDirs("$projectDir/main/mixed")
            resources.srcDir("$projectDir/main/resources")
        }

        test {
            java.srcDirs("$projectDir/test/java")
            scala.srcDirs("$projectDir/test/scala")
            resources.srcDirs("$projectDir/test/resources")
        }
    }

    dependencies {
        paperDevBundle("1.17.1-R0.1-SNAPSHOT")
        compileOnly("com.lmax:disruptor:3.4.4")
        compileOnly("org.scala-lang:scala3-library_3:3.1.0")
        compileOnly("org.jetbrains:annotations:22.0.0")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
        testImplementation("org.junit.vintage:junit-vintage-engine:5.8.1")
    }

    tasks {
        assemble {
            dependsOn(":reobfJar")
        }
        compileJava {
            options.encoding = Charsets.UTF_8.name()
            options.release.set(17)
        }
        javadoc {
            options.encoding = Charsets.UTF_8.name()
        }
        processResources {
            filteringCharset = Charsets.UTF_8.name()
        }
        withType(JavaCompile::class.java) {
            options.encoding = "UTF-8"
        }
        getByName<Test>("test"){
            useJUnitPlatform()
        }
    }
}

project(":pl-entities") {
    dependencies {
        compileOnly(project(":pl-misc-lib"))
        compileOnly(project(":pl-entity-registry-lib"))
        compileOnly(project(":pl-scala-support-lib"))
        compileOnly("com.ticxo.modelengine:api:R2.2.0")
        compileOnly("tf.tofu:tofu-core_2.13:0.10.3")
    }

    bukkit {
        name = "Entities"
        main = "org.foton.entities.FotonEntities"
        version = "a1.0"
        apiVersion = "1.17"
        authors = listOf("Denery")
        depend = listOf("ModelEngine", "EntityRegistry", "ScalaSupport")
        commands {
            register("erspawn") {
                description = "Spawn entity if its ID is registered in EntityRegistryLib"
                usage = "/<command>"
            }
        }

        permissions {
            register("foton.commands.erspawn") {
                description = "Player can use the erspawn command"
                default = BukkitPluginDescription.Permission.Default.OP
            }
        }
    }
}

project(":pl-discs") {
    dependencies {
        compileOnly(project(":pl-misc-lib"))
        compileOnly(project(":pl-disc-registry-lib"))
    }

    bukkit {
        name = "Discs"
        main = "org.foton.discs.DiscsLauncher"
        apiVersion = "1.17"
        authors = listOf("Danik_Vitek")
        depend = listOf("DiscRegistry", "Misc")
        description = "Register custom music discs"
    }
}

project(":pl-waystones") {
    dependencies {
        compileOnly(project(":pl-misc-lib"))
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
}

project(":pl-entity-registry-lib") {
    dependencies {
        compileOnly(project(":pl-scala-support-lib"))
        compileOnly(project(":pl-misc-lib"))

        compileOnly("com.ticxo.modelengine:api:R2.2.0")
        compileOnly("de.tr7zw:item-nbt-api-plugin:2.8.0")
        compileOnly("tf.tofu:tofu-core_2.13:0.10.3")
    }

    bukkit {
        name = "EntityRegistry"
        main = "io.denery.entityregistry.EntityRegistryPlugin"
        apiVersion = "1.17"
        depend = listOf("Misc", "ScalaSupport")
        authors = listOf("Denery")
    }
}

project(":pl-disc-registry-lib") {
    dependencies {
        compileOnly(project(":pl-misc-lib"))
    }

    bukkit {
        name = "DiscRegistry"
        main = "com.danikvitek.discregistry.DiscRegistryPlugin"
        apiVersion = "1.17"
        depend = listOf("Misc")
        authors = listOf("Danik_Vitek")
    }
}

project(":pl-misc-lib") {
    bukkit {
        name = "Misc"
        main = "org.foton.MiscPlugin"
        apiVersion = "1.17"
        authors = listOf("Denery", "Danik_Vitek")
    }
}

project(":pl-scala-support-lib") {
    dependencies {
        implementation("tf.tofu:tofu-core_2.13:0.10.3")
        implementation("org.scala-lang:scala3-library_3:3.1.0")
    }

    bukkit {
        name = "ScalaSupport"
        version = "a1.0"
        main = "org.foton.scalasupport.ScalaLauncher"
        apiVersion = "1.17"
        authors = listOf("Denery")
    }
}