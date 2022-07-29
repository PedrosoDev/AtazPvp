package com.github.pedrosodev.atazpvp.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 30.12.2017.
 */
public class HibernateConfiguration {
    private boolean autoDiscovery = true;
    private Map<String, Object> hibernateSettings = new HashMap<>();

    public Map<String, Object> getHibernateSettings() {
        return hibernateSettings;
    }

    public void setHibernateSettings(Map<String, Object> hibernateSettings) {
        this.hibernateSettings = hibernateSettings;
    }

    @Override
    public String toString() {
        return "HibernateConfiguration{" +
                "hibernateSettings=" + hibernateSettings +
                '}';
    }

    public boolean isAutoDiscovery() {
        return autoDiscovery;
    }

    public void setAutoDiscovery(boolean autoDiscovery) {
        this.autoDiscovery = autoDiscovery;
    }
}
