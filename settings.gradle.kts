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
include(":pl-scala-support-lib")
include(":pl-entity-registry-lib")
include(":pl-disc-registry-lib")
include(":pl-misc-lib")

project(":pl-scala-support-lib").projectDir = file("libraries/scala-support")
project(":pl-entity-registry-lib").projectDir = file("libraries/entity-registry")
project(":pl-disc-registry-lib").projectDir = file("libraries/disc-registry")
project(":pl-misc-lib").projectDir = file("libraries/misc")