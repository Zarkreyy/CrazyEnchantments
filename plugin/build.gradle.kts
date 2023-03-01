plugins {
    `maven-publish`

    id("com.modrinth.minotaur") version "2.6.0"

    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    /**
     * Towny Team
     */
    maven("https://repo.glaremasters.me/repository/towny/")

    /**
     * SilkSpawners Team
     */
    maven("https://repo.dustplanet.de/artifactory/libs-release-local/")

    /**
     * NCP Team
     */
    maven("https://repo.md-5.net/content/repositories/snapshots/")

    /**
     * FactionsUUID API
     */
    maven("https://ci.ender.zone/plugin/repository/everything/")

    /**
     * NBT API
     */
    maven("https://repo.codemc.io/repository/maven-public/")

    /**
     * BG Software Team
     */
    maven("https://repo.bg-software.com/repository/api/")

    /**
     * AAC API
     */
    maven("https://repo.janmm14.de/repository/public/")

    /**
     * PreciousStones
     */
    maven("https://maven.elmakers.com/repository/")

    /**
     * Unknown
     */
    maven("https://repo.codemc.io/repository/nms/")
}

dependencies {
    implementation(project(":api"))

    implementation(project(":v1_12_2_down"))
    implementation(project(":v1_13_up"))

    implementation("de.tr7zw", "nbt-data-api", "2.11.1")

    implementation("org.bstats", "bstats-bukkit", "3.0.0")
    implementation("org.jetbrains", "annotations", "23.0.0")

    compileOnly("org.spigotmc", "spigot-api", "${project.extra["minecraft_version"]}-R0.1-SNAPSHOT")

    compileOnly("com.bgsoftware", "SuperiorSkyblockAPI", "2022.8.1")
    compileOnly("com.bgsoftware", "WildStackerAPI", "2022.5")

    compileOnly("net.sacredlabyrinth.Phaed", "PreciousStones", "1.16.1.9")

    compileOnly("de.dustplanet", "silkspawners", "7.2.0") {
        exclude("org.bukkit", "bukkit")
        exclude("org.spigotmc", "spigot")
        exclude("com.destroystokyo.paper", "paper")
        exclude("com.sk89q", "worldguard")
        exclude("com.sk89q", "worldedit")
        exclude("com.massivecraft.massivecore", "MassiveCore")
        exclude("com.massivecraft.factions", "Factions")
        exclude("net.gravitydevelopment.updater", "updater")
        exclude("com.intellectualsites", "Pipeline")
    }

    compileOnly("com.github.TechFortress", "GriefPrevention", "16.18")

    compileOnly("com.massivecraft", "Factions", "1.6.9.5-U0.6.9")

    compileOnly("com.palmergames.bukkit.towny", "towny", "0.98.3.10")

    compileOnly("fr.neatmonster", "nocheatplus", "3.16.1-SNAPSHOT")
    compileOnly("me.vagdedes.spartan", "SpartanAPI", "9.1")

    compileOnly("com.github.MilkBowl", "VaultAPI", "1.7")
}

tasks {
    shadowJar {
        archiveFileName.set("${rootProject.name}-${project.version}.jar")

        listOf(
            "de.tr7zw",
            "org.bstats",
            "org.jetbrains"
        ).forEach {
            relocate(it, "${project.group}.plugin.lib.$it")
        }
    }

    modrinth {
        token.set(System.getenv("MODRINTH_TOKEN"))
        projectId.set(rootProject.name.toLowerCase())

        versionName.set("${rootProject.name} ${project.version}")
        versionNumber.set("${project.version}")

        versionType.set("alpha")

        uploadFile.set(shadowJar.get())

        autoAddDependsOn.set(true)

        gameVersions.addAll(listOf("1.8.8", "1.12.2", "1.16.5"))
        loaders.addAll(listOf("spigot", "paper"))

        //<h3>The first release for CrazyCrates on Modrinth! 🎉🎉🎉🎉🎉<h3><br> If we want a header.
        changelog.set("""
                <h4>Notice:</h4>
                 <p>This is only for Legacy ( 1.8 - 1.16.5 ) Support, No new features will be added.</p>
                <h4>Bug Fixes:</h4>
                 <p>N/a</p>
            """.trimIndent())
    }

    processResources {
        filesMatching("plugin.yml") {
            expand(
                "name" to rootProject.name,
                "group" to project.group,
                "version" to project.version,
                "description" to project.description,
                "website" to "https://modrinth.com/plugin/${rootProject.name.toLowerCase()}"
            )
        }
    }
}

publishing {
    repositories {
        maven("https://repo.crazycrew.us/releases") {
            name = "crazycrew"
            credentials(PasswordCredentials::class)
            credentials {
                username = System.getenv("REPOSITORY_USERNAME")
                password = System.getenv("REPOSITORY_PASSWORD")
            }
        }
    }

    publications {
        create<MavenPublication>("maven") {
            groupId = "${project.group}"
            artifactId = rootProject.name.toLowerCase()
            version = "${project.version}"
            from(components["java"])
        }
    }
}