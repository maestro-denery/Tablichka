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

include(":pl-tablight-api-base")
include(":pl-tablight-entity-registry-api-v0")
include(":pl-tablight-disc-registry-api-v0")

include(":pl-scala-support-lib")
include(":pl-misc-lib")

project(":pl-tablight-api-base").projectDir = file("apis/tablight-api-base")
project(":pl-tablight-entity-registry-api-v0").projectDir = file("apis/tablight-entity-registry-api-v0")
project(":pl-tablight-disc-registry-api-v0").projectDir = file("apis/tablight-disc-registry-api-v0")

project(":pl-scala-support-lib").projectDir = file("libraries/scala-support")
project(":pl-misc-lib").projectDir = file("libraries/misc")
