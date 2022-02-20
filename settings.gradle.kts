pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

rootProject.name = "TabLight"

include(":entities")
include(":discs")
include(":waystones")

include(":tablight-api-base")
include(":tablight-entity-registry-api")
include(":tablight-disc-registry-api")

include(":scala-support-lib")
include(":misc-lib")

project(":entities").projectDir = file("tablight-entities")
project(":discs").projectDir = file("tablight-discs")
project(":waystones").projectDir = file("tablight-waystones")

project(":tablight-api-base").projectDir = file("apis/tablight-api-base")
project(":tablight-entity-registry-api").projectDir = file("apis/tablight-entity-registry-api")
project(":tablight-disc-registry-api").projectDir = file("apis/tablight-disc-registry-api")

project(":scala-support-lib").projectDir = file("libraries/scala-support")
project(":misc-lib").projectDir = file("libraries/misc")
