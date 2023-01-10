repositories {
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    implementation(project(":api"))

    compileOnly("org.spigotmc", "spigot-api", "1.12.2-R0.1-SNAPSHOT")

    compileOnly("com.sk89q.worldguard", "worldguard-legacy", "6.2")

    compileOnly("com.sk89q", "worldedit", "6.0.0-SNAPSHOT")
}