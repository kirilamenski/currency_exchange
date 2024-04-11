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

rootProject.name = "Currency Exchange"
include(":app")
include(":feature:currency_list")
include(":feature:select_currency")
include(":core:base")
include(":core:design")
include(":core:data")
include(":core:network")
include(":core:model")
include(":core:domain")
include(":core:database")
