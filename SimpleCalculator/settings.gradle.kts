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
    }
}

rootProject.name = "SimpleCalculator"
include(":app")

//include ':app', ':commons-lang3-3.13.0.jar'
//project(':commons-lang3-3.13.0.jar').projectDir = new File('libs/commons-lang3-3.13.0.jar')
