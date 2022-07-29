package com.github.pedrosodev.atazpvp;

import com.github.pedrosodev.atazpvp.config.HibernateConfiguration;
import com.github.pedrosodev.atazpvp.dependencyinjection.AtazPvpModule;
import com.github.pedrosodev.atazpvp.initializers.CommandInitializer;
import com.github.pedrosodev.atazpvp.initializers.HibernateInitializer;
import com.github.pedrosodev.atazpvp.initializers.ListenerInitializer;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class AtazPvp extends JavaPlugin {

    @Inject
    private ListenerInitializer listenerInitializer;
    @Inject
    private CommandInitializer commandInitializer;
    @Inject
    private HibernateInitializer hibernateInitializer;

    public AtazPvp() {
        super();
    }

    @Override
    public void onEnable() {
        final Injector injector = Guice.createInjector(new AtazPvpModule(this, hibernateConfiguration()));
        injector.injectMembers(this);

        hibernateInitializer.init(injector);
        listenerInitializer.init(injector);
        commandInitializer.init(injector);
    }

    private static HibernateConfiguration hibernateConfiguration() {

        final String host = "database";
        final int port = 3306;
        final String database = "minecraft_server";
        final String user = "root";
        final String password = "123456";

        Map<String, Object> configurationMap = new HashMap<String, Object>() {
            {
                put("hibernate.current_session_context_class",  "thread");
                put("hibernate.show_sql", "true");
                put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
                put("hibernate.connection.provider_class", "com.zaxxer.hikari.hibernate.HikariConnectionProvider");
                put("hibernate.hikari.minimumIdle", "5");
                put("hibernate.hikari.maximumPoolSize", "10");
                put("hibernate.hikari.idleTimeout", "30000");
                put("hibernate.hikari.dataSourceClassName", "com.mysql.cj.jdbc.MysqlDataSource");
                put("hibernate.hikari.dataSource.url", "jdbc:mysql://"+host+":"+port+"/"+database);
                put("hibernate.hikari.dataSource.user", user);
                put("hibernate.hikari.dataSource.password", password);
                put("hibernate.hbm2ddl.auto", "update");
            }
        };

        HibernateConfiguration configuration = new HibernateConfiguration();
        configuration.setHibernateSettings(configurationMap);
        return configuration;
    }

    @Override
    public void onDisable() {
        hibernateInitializer.dispose();
    }
}
