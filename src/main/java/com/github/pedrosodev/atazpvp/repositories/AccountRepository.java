package com.github.pedrosodev.atazpvp.repositories;

import com.github.pedrosodev.atazpvp.entities.AccountEntity;
import com.google.inject.ImplementedBy;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

@ImplementedBy(AccountRepositoryImpl.class)
public interface AccountRepository {

        boolean saveAccount(final @NotNull AccountEntity account);

        AccountEntity getAccountByUuid(final @NotNull UUID uuid);

}
