package com.github.pedrosodev.atazpvp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Ranks {
    UNRANKED("§7","-", "Unranked");

    private final String color;
    private final String symbol;
    private final String name;

    public static Ranks getRank(String name) {
        for (Ranks group : Ranks.values()) {
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
