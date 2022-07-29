package com.github.pedrosodev.atazpvp.dependencyinjection;

import com.github.pedrosodev.atazpvp.config.HibernateConfiguration;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@RequiredArgsConstructor
public class AtazPvpModule extends AbstractModule implements TypeListener {

    private final JavaPlugin javaPlugin;
    private final HibernateConfiguration hibernateConfiguration;

    @Override
    protected void configure() {
        super.bindListener(Matchers.any(), this);

        super.bind(JavaPlugin.class).toInstance(javaPlugin);
        super.bind(PluginManager.class).toInstance(javaPlugin.getServer().getPluginManager());
        super.bind(HibernateConfiguration.class).toInstance(hibernateConfiguration);
    }

    @Override
    public <I> void hear(final @NotNull TypeLiteral<I> typeLiteral, final @NotNull TypeEncounter<I> typeEncounter) {
        typeEncounter.register((InjectionListener<I>) i ->
                Arrays.stream(i.getClass().getMethods()).filter(method -> method.isAnnotationPresent(PostConstruct.class))
                        .forEach(method -> invokeMethod(method, i)));
    }

    private void invokeMethod(final @NotNull Method method, final @NotNull Object object) {
        try {
            method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
