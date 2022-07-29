package com.github.pedrosodev.atazpvp.repositories;

import com.github.pedrosodev.atazpvp.entities.AccountEntity;
import com.github.pedrosodev.atazpvp.entities.KitPvpStatusEntity;
import com.github.pedrosodev.atazpvp.managers.HibernateManager;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class KitPvpStatusRepositoryImpl implements KitPvpStatusRepository {

    private final HibernateManager manager;

    @Override
    public boolean saveStatus(@NotNull KitPvpStatusEntity status) {
        return manager.withEntityManager(session -> {
            try {
                session.getTransaction().begin();
                session.saveOrUpdate(status);
                return true;
            } catch (Exception e) {
                return false;
            } finally {
                session.getTransaction().commit();
            }
        });
    }

    @Override
    public KitPvpStatusEntity getStatusByUuid(@NotNull UUID uuid) {
        return manager.withEntityManager(session -> {
            session.getTransaction().begin();
            KitPvpStatusEntity status = session.get(KitPvpStatusEntity.class, uuid);
            session.getTransaction().commit();
            return status;
        });
    }
}
