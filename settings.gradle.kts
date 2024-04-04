pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://esri.jfrog.io/artifactory/arcgis") }
        maven { url = uri("https://olympus.esri.com/artifactory/arcgisruntime-repo/") }
    }
}

rootProject.name = "OgcTestApp"
include(":app")
 