package com.github.pedrosodev.atazpvp.initializers;

import com.github.pedrosodev.atazpvp.interfaces.Initializer;
import com.github.pedrosodev.atazpvp.listeners.PlayerListener;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class ListenerInitializer implements Initializer {

    private final JavaPlugin plugin;
    private final PluginManager pluginManager;

    @Inject
    public ListenerInitializer(final JavaPlugin plugin, final PluginManager pluginManager) {
        this.plugin = plugin;
        this.pluginManager = pluginManager;
    }

    @Override
    public void init(final @NotNull Injector injector) {
        // TODO: 23/07/2022 - Implement listener initialization
        pluginManager.registerEvents(injector.getInstance(PlayerListener.class), plugin);
    }
}
