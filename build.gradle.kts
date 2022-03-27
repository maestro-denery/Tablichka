plugins {
    // General
    id("idea")
    id("java")
    id("scala")
    id("maven-publish")

    // Paper
    id("io.papermc.paperweight.userdev") version "1.3.5" apply false
    id("io.papermc.paperweight.patcher") version "1.3.5" apply false
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1" apply false
    id("xyz.jpenilla.run-paper") version "1.0.6"

    // Other management
    id("org.sonarqube") version "3.3"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("com.github.ben-manes.versions") version "0.42.0"
    //TODO: Implement checkstyle plugin to automate checking APIs structure.
}

group = "dev.tablight.common"
version = "dev-1"
description = "Main TabLight gameplay plugins, APIs and libraries for them."

tasks {
    register("setupTablightDevelopment") {
        dependsOn(project(":tablight-paper").tasks.getByName("applyPatches"))
        dependsOn(project(":tablight-paper").tasks.getByName("publishDevBundlePublicationToMavenLocal"))
    }

    register("buildAll") {
        project(":paper-plugin").subprojects
            .map { sub -> sub.tasks.getByName<io.papermc.paperweight.tasks.RemapJar>("reobfJar") }
            .forEach { reobf -> dependsOn(reobf) }
    }

    runPaper {
        disablePluginJarDetection()
    }

    runServer {
        runDirectory(file("$rootDir/run"))
        project(":paper-plugin").subprojects.filter { sub ->
               sub.name != "waystones"
        }.forEach { sub ->
            pluginJars.from(sub.tasks.getByName<io.papermc.paperweight.tasks.RemapJar>("reobfJar").outputJar)
        }
        serverJar(project(":tablight-paper").tasks.getByName<io.papermc.paperweight.tasks.CreatePaperclipJar>("createReobfPaperclipJar").outputZip)
        minecraftVersion("1.18.2")
    }

    sonarqube {
        properties {
            property("sonar.host.url", System.getenv("host.url"))
            property("sonar.login", System.getenv("login"))
            property("sonar.password", System.getenv("password"))
            property("sonar.projectKey", System.getenv("projectKey"))
        }
    }
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "com.github.ben-manes.versions")
    apply(plugin = "com.github.johnrengelman.shadow")

    repositories {
        mavenCentral()
    }

    dependencies {
        compileOnly("org.jetbrains:annotations:23.0.0")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    }

    tasks {
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
        getByName<Test>("test") {
            useJUnitPlatform()
        }
        jar {
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
    }
}

project(":paper-plugin").subprojects {
    apply(plugin = "io.papermc.paperweight.userdev")
    apply(plugin = "net.minecrell.plugin-yml.bukkit")

    repositories {
        mavenLocal()
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://oss.sonatype.org/content/groups/public/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://repo.codemc.org/repository/maven-public/")
        maven("https://jitpack.io")
        maven("https://repo.dmulloy2.net/repository/public/")
    }

    dependencies {
        // If you have some problems there, then look at tablight-paper/build.gradle.kts, and run ./gradlew publishToMavenLocal
        paperweightDevBundle("dev.tablight.tablightpaper", "1.18.2-R0.1-SNAPSHOT")

        compileOnly("com.lmax:disruptor:3.4.4")
        compileOnly("org.scala-lang:scala3-library_3:3.1.0")

        testImplementation("com.lmax:disruptor:3.4.4")
    }

    tasks {
        assemble {
            dependsOn(":reobfJar")
        }
        shadowJar {
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            destinationDirectory.set(file("$rootDir/out-plugins"))
        }
    }
}

project(":tablight-paper") {
    apply(plugin = "io.papermc.paperweight.patcher")
}
