pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://maven.fabricmc.net/")
    }
}

rootProject.name = "TabLight"

// Paper plugins
include(":paper-plugin")

include(":paper-plugin:entities")
include(":paper-plugin:discs")
include(":paper-plugin:waystones")

include(":paper-plugin:tablight-entity-registry-api")
include(":paper-plugin:tablight-disc-registry-api")
include(":paper-plugin:tablight-llapi")

include(":paper-plugin:scala-support-lib")
include(":paper-plugin:misc-lib")

include(":paper-plugin:dependency-manager")

// Libraries, generally cross-platform.
include(":library")

include(":library:packer")

include(":library:data-addons")
include(":library:data-addons:sqlite-io")

include(":library:admixtures")

// Our Paper fork for some specific things.
include(":tablight-paper")
include(":tablight-paper:tablight-server")
include(":tablight-paper:tablight-api")


project(":paper-plugin:entities").projectDir = file("tablight-entities")
project(":paper-plugin:discs").projectDir = file("tablight-discs")
project(":paper-plugin:waystones").projectDir = file("tablight-waystones")
project(":paper-plugin:dependency-manager").projectDir = file("tablight-dependency-manager")

project(":paper-plugin:tablight-entity-registry-api").projectDir = file("apis/tablight-entity-registry-api")
project(":paper-plugin:tablight-disc-registry-api").projectDir = file("apis/tablight-disc-registry-api")
project(":paper-plugin:tablight-llapi").projectDir = file("apis/tablight-llapi")

project(":paper-plugin:scala-support-lib").projectDir = file("libraries/scala-support")
project(":paper-plugin:misc-lib").projectDir = file("libraries/misc")

project(":library:packer").projectDir = file("libraries/packer")

project(":library:data-addons").projectDir = file("libraries/data-addons")
project(":library:data-addons:sqlite-io").projectDir = file("libraries/data-addons/sqlite-io")

project(":library:admixtures").projectDir = file("libraries/admixtures")
