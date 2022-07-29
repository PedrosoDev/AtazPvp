package com.github.pedrosodev.atazpvp.entities;

import com.github.pedrosodev.atazpvp.enums.Groups;
import com.github.pedrosodev.atazpvp.enums.Ranks;
import lombok.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "account")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {
    @Id
    @Type(type = "uuid-char")
    private UUID uuid;

    @Column(unique = true)
    private String username;

    @Temporal(TemporalType.TIMESTAMP)
    private Date firstJoinDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastJoinDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "group_id")
    private Groups group;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tag_id")
    private Groups tag;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "rank_id")
    private Ranks rank;

    private int coins;

    private int xp;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @MapsId
    private KitPvpStatusEntity kitPvpStatus;

    public Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccountEntity that = (AccountEntity) o;
        return uuid != null && Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
