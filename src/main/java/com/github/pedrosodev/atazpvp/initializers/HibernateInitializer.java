package com.github.pedrosodev.atazpvp.initializers;

import com.github.pedrosodev.atazpvp.config.HibernateConfiguration;
import com.github.pedrosodev.atazpvp.entities.AccountEntity;
import com.github.pedrosodev.atazpvp.entities.KitPvpStatusEntity;
import com.github.pedrosodev.atazpvp.interfaces.Destroyer;
import com.github.pedrosodev.atazpvp.interfaces.Initializer;
import com.github.pedrosodev.atazpvp.managers.HibernateManager;
import com.google.inject.Inject;
import com.google.inject.Injector;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class HibernateInitializer implements Initializer, Destroyer {

    private final HibernateManager hibernateManager;
    private final HibernateConfiguration configuration;

    @Override
    public void init(final @NotNull Injector injector) {
        hibernateManager.init(configuration, injector.getInstance(JavaPlugin.class));
        // TODO: 23/07/2022 - Add Entity
        hibernateManager.addClasses(
                AccountEntity.class,
                KitPvpStatusEntity.class
        );
    }

    @Override
    public void dispose() {
        hibernateManager.dispose();
    }
}
