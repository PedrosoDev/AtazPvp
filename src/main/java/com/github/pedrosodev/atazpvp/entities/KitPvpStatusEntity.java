package com.github.pedrosodev.atazpvp.entities;

import com.github.pedrosodev.atazpvp.enums.Groups;
import lombok.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "kitpvp_status")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class KitPvpStatusEntity {
    @Id
    @Type(type = "uuid-char")
    private UUID uuid;

    private int kills;

    private int deaths;

    @Transient
    private double kdr;


    @PostLoad
    public void onLoad() {
        kdr = ((double) kills) / ((double) (deaths == 0 ? 1 : deaths));
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        KitPvpStatusEntity that = (KitPvpStatusEntity) o;
        return uuid != null && Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
