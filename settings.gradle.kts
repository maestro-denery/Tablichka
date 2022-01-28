pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

rootProject.name = "TabLight"

include(":pl-entities")
include(":pl-discs")
include(":pl-waystones")

// библиотечки, могут использовать nms и разные извращения, главное чтобы было понятно и удобно использовать.
include(":pllib-scala-support")
include(":pllib-entity-registry")
include(":pllib-disc-registry")
include(":pllib-misc")

project(":pllib-scala-support").projectDir = file("libraries/pllib-scala-support")
project(":pllib-entity-registry").projectDir = file("libraries/pllib-entity-registry")
project(":pllib-disc-registry").projectDir = file("libraries/pllib-disc-registry")
project(":pllib-misc").projectDir = file("libraries/pllib-misc")