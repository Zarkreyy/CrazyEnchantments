package com.ryderbelserion.crazyenchantments.loader;

import com.ryderbelserion.crazyenchantments.CrazyEnchantments;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

public class CrazyLoader implements PluginBootstrap, PluginLoader {

    @Override
    public void bootstrap(@NotNull BootstrapContext context) {

    }

    @Override
    public @NotNull JavaPlugin createPlugin(@NotNull PluginProviderContext context) {
        return new CrazyEnchantments();
    }

    @Override
    public void classloader(@NotNull PluginClasspathBuilder classpathBuilder) {
        MavenLibraryResolver resolver = new MavenLibraryResolver();

        resolver.addDependency(new Dependency(new DefaultArtifact("com.ryderbelserion.vital:paper:1.0.6"), null));

        //resolver.addRepository(new RemoteRepository.Builder("paper", "default", "https://repo.papermc.io/repository/maven-public").build());

        resolver.addRepository(new RemoteRepository.Builder("crazycrew", "default", "https://repo.crazycrew.us/libraries").build());

        classpathBuilder.addLibrary(resolver);
    }
}