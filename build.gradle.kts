plugins {
    `java-library`
}

allprojects {
    apply(plugin = "java-library")

    repositories {
        /**
         * Spigot Team
         */
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")

        /**
         * Paper Team
         */
        maven("https://repo.papermc.io/repository/maven-public/")

        /**
         * CrazyCrew Repository
         * All 3rd party dependencies
         */
        maven("https://repo.crazycrew.us/plugins/")

        /**
         * Everything else we need.
         */
        maven("https://jitpack.io")

        mavenCentral()
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}