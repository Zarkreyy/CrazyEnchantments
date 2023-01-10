repositories {
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    implementation(project(":api"))

    compileOnly("org.spigotmc", "spigot-api", "${project.extra["minecraft_version"]}-R0.1-SNAPSHOT")

    compileOnly("com.sk89q.worldedit", "worldedit-bukkit", "7.2.9")

    compileOnly("com.sk89q.worldguard", "worldguard-bukkit", "7.1.0-SNAPSHOT")

    compileOnly("com.plotsquared", "PlotSquared-Core", "6.10.1")
}