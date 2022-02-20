plugins {
    //TODO: Implement checkstyle plugin to automate checking APIs structure.
    id("java")
    id("scala")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.papermc.paperweight.userdev") version "1.3.3" apply false
    id("xyz.jpenilla.run-paper") version "1.0.6"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1" apply false
    id("org.sonarqube") version "3.3"
}

group = "dev.tablight.common"
version = "dev-0"
description = "Main TabLight gameplay plugins, APIs and libraries for them."

tasks {
    register("buildAll") {
        subprojects
            .map { sub -> sub.tasks.shadowJar }
            .forEach { sub -> dependsOn(sub) }
    }

    runPaper {
        disablePluginJarDetection()
    }

    runServer {
        runDirectory(file("$rootDir/run"))
        subprojects.forEach { sub ->
            pluginJars.from(sub.tasks.shadowJar.get().archiveFile)
        }
        minecraftVersion("1.17.1")
    }

    sonarqube {
        properties {
            property("sonar.host.url", System.getenv("host.url"))
            property("sonar.login", System.getenv("login"))
            property("sonar.projectKey", System.getenv("projectKey"))
        }
    }
}

subprojects {
    apply(plugin = "java")
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

    dependencies {
        paperDevBundle("1.17.1-R0.1-SNAPSHOT")
        compileOnly("com.lmax:disruptor:3.4.4")
        compileOnly("org.scala-lang:scala3-library_3:3.1.0")
        compileOnly("org.jetbrains:annotations:22.0.0")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
        testImplementation("org.junit.vintage:junit-vintage-engine:5.8.1")
        testImplementation("org.scala-lang:scala3-library_3:3.1.0")
        testImplementation("tf.tofu:tofu-core_2.13:0.10.3")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
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
        getByName<Test>("test") {
            useJUnitPlatform()
        }
        shadowJar {
          destinationDirectory.set(file("$rootDir/out-plugins"))
        }
    }
}
