pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
    }
}

val modId: String by extra
val minecraftVersion: String by extra
rootProject.name = "$modId-$minecraftVersion"
