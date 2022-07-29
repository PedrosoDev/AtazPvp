package com.github.pedrosodev.atazpvp.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Groups {
    DEVELOPER("§6", "Dev", "§6§lDEV §6"),
    MEMBER("§7","Membro", "§7");

    private final String color;
    private final String name;
    private final String prefix;

    public static Groups getGroup(String name) {
        for (Groups group : Groups.values()) {
            if (group.getName().equalsIgnoreCase(name)) {
                return group;
            }
        }
        return null;
    }

    public String getBoldColor() {
        return this.color+"§l";
    }
}
