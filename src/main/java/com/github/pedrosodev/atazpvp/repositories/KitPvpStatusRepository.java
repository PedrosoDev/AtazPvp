package com.github.pedrosodev.atazpvp.repositories;

import com.github.pedrosodev.atazpvp.entities.AccountEntity;
import com.github.pedrosodev.atazpvp.entities.KitPvpStatusEntity;
import com.google.inject.ImplementedBy;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@ImplementedBy(KitPvpStatusRepositoryImpl.class)
public interface KitPvpStatusRepository {

        boolean saveStatus(final @NotNull KitPvpStatusEntity account);

        KitPvpStatusEntity getStatusByUuid(final @NotNull UUID uuid);

}
