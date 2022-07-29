package com.github.pedrosodev.atazpvp.managers;

import com.github.pedrosodev.atazpvp.config.HibernateConfiguration;
import com.google.inject.Singleton;
import org.bukkit.plugin.java.JavaPlugin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Singleton
public class HibernateManager {
    private StandardServiceRegistry standardRegistry;
    private final Set<String> classNames = new HashSet<>();
    private HibernateConfiguration hibernateConfiguration;
    private SessionFactory sessionFactory;

    public void init(HibernateConfiguration hibernateConfiguration, JavaPlugin plugin) {
        this.hibernateConfiguration = hibernateConfiguration;
        ClassLoader originalContextClassLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(plugin.getClass().getClassLoader());

        constructSessionFactory(hibernateConfiguration);

        Thread.currentThread().setContextClassLoader(originalContextClassLoader);
    }


    public void dispose() {
        sessionFactory.close();
    }

    public void addClassesByName(String... classNames) {
        addClassesByName(Arrays.asList(classNames));
    }

    public void addClassesByName(Collection<String> classNames) {
        this.classNames.addAll(classNames);
        constructSessionFactory(hibernateConfiguration);
    }

    public void addClasses(Class<?>... classes) {
        addClasses(Arrays.asList(classes));
    }

    public void addClasses(Collection<Class<?>> classes) {
        addClassesByName(classes.stream().map(Class::getName).collect(Collectors.toList()));
    }

    private void constructSessionFactory(HibernateConfiguration hibernateConfiguration) {
        System.out.println("Reconstructing session factory.");
        StandardServiceRegistryBuilder standardRegistryBuilder = new StandardServiceRegistryBuilder();
        standardRegistryBuilder.applySettings(hibernateConfiguration.getHibernateSettings());

        StandardServiceRegistry standardRegistry = standardRegistryBuilder.build();
        MetadataSources sources = new MetadataSources(standardRegistry);
        classNames.forEach(sources::addAnnotatedClassName);

        MetadataBuilder metadataBuilder = sources.getMetadataBuilder();
        metadataBuilder.applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE);
        //metadataBuilder.applyImplicitSchemaName("rake");
        Metadata metadata = metadataBuilder.build();;
        sessionFactory = metadata.buildSessionFactory();

    }

    public Session getEntityManager() {
        return sessionFactory.openSession();
    }

    public void withEntityManager(Consumer<Session> consumer) {
        try (Session en = getEntityManager()) {
            consumer.accept(en);
        }
    }

    public <T> T withEntityManager(Function<Session, T> consumer) {
        Session en = getEntityManager();
        T returnValue;
        try {
            returnValue = consumer.apply(en);
        } finally {
            en.close();
        }

        return returnValue;
    }
}
