package com.github.pedrosodev.atazpvp.repositories;

import com.github.pedrosodev.atazpvp.entities.AccountEntity;
import com.github.pedrosodev.atazpvp.managers.HibernateManager;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class AccountRepositoryImpl implements AccountRepository {

    private final HibernateManager manager;

    @Override
    public boolean saveAccount(@NotNull AccountEntity account) {
        return manager.withEntityManager(session -> {
            try {
                session.getTransaction().begin();
                session.saveOrUpdate(account);
                return true;
            } catch (Exception e) {
                return false;
            } finally {
                session.getTransaction().commit();
            }
        });
    }

    @Override
    public AccountEntity getAccountByUuid(@NotNull UUID uuid) {
        return manager.withEntityManager(session -> {
            session.getTransaction().begin();
            AccountEntity account = session.get(AccountEntity.class, uuid);
            session.getTransaction().commit();
            return account;
        });
    }
}
