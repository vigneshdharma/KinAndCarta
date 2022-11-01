/*pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "KinCarta"
include ':app'
include ':data'
include ':domain'*//*


include(":app", ":data", ":domain")

*/

rootDir
    .walk()
    .maxDepth(1)
    .filter {
        it.name != "buildSrc" && it.isDirectory &&
                file("${it.absolutePath}/build.gradle.kts").exists()
    }
    .forEach {
        include(":${it.name}")
    }
include(":app")
include(":domain")
include(":data")
