pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://maven.fabricmc.net/")
    }
}

rootProject.name = "TabLight"

include(":entities")
include(":discs")
include(":waystones")

include(":tablight-entity-registry-api")
include(":tablight-disc-registry-api")
include(":tablight-llapi")

include(":scala-support-lib")
include(":misc-lib")
include(":packer")
include(":data-addons")
include(":admixtures")

project(":entities").projectDir = file("tablight-entities")
project(":discs").projectDir = file("tablight-discs")
project(":waystones").projectDir = file("tablight-waystones")

project(":tablight-entity-registry-api").projectDir = file("apis/tablight-entity-registry-api")
project(":tablight-disc-registry-api").projectDir = file("apis/tablight-disc-registry-api")
project(":tablight-llapi").projectDir = file("apis/tablight-llapi")

project(":scala-support-lib").projectDir = file("libraries/scala-support")
project(":misc-lib").projectDir = file("libraries/misc")
project(":packer").projectDir = file("libraries/packer")
project(":data-addons").projectDir = file("libraries/data-addons")
project(":admixtures").projectDir = file("libraries/admixtures")
