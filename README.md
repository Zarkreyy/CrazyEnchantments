# Crazy Enchantments
The legacy source for CrazyEnchantments ( 1.8 -> 1.16.5 )

## Download:
All versions labeled "Alpha" are legacy versions.
https://modrinth.com/plugin/crazycrates/versions

## Repository:
https://repo.crazycrew.us/#/releases

# Developer API

## Groovy
<details>
 <summary>
   Gradle (Groovy)
 </summary>

```gradle
repositories {
    maven {
        url = "https://repo.crazycrew.us/releases"
    }
}
```

```gradle
dependencies {
    compileOnly "me.badbones69.crazyenchantments:crazyenchantments:1.8.1.0"
}
```
</details>

## Kotlin
<details>
 <summary>
   Gradle (Kotlin)
 </summary>

```gradle
repositories {
    maven("https://repo.crazycrew.us/releases")
}
```

```gradle
dependencies {
    compileOnly("me.badbones69.crazyenchantments", "crazyenchantments", "1.8.1.0")
}
```
</details>

## Maven
<details>
 <summary>
   Maven
 </summary>

```xml
<repository>
  <id>crazycrew</id>
  <url>https://repo.crazycrew.us/releases</url>
</repository>
```

```xml
<dependency>
  <groupId>me.badbones69.crazycrates</groupId>
  <artifactId>crazyenchantments</artifactId>
  <version>1.8.1.0</version>
  <scope>provided</scope>  
 </dependency>
```
</details>