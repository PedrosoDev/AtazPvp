package com.github.pedrosodev.atazpvp.listeners;

import com.github.pedrosodev.atazpvp.entities.AccountEntity;
import com.github.pedrosodev.atazpvp.entities.KitPvpStatusEntity;
import com.github.pedrosodev.atazpvp.enums.Groups;
import com.github.pedrosodev.atazpvp.enums.Ranks;
import com.github.pedrosodev.atazpvp.enums.ScoreBoards;
import com.github.pedrosodev.atazpvp.managers.ScoreBoardManager;
import com.github.pedrosodev.atazpvp.repositories.AccountRepository;
import com.github.pedrosodev.atazpvp.repositories.KitPvpStatusRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;

@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class PlayerListener implements Listener {

    private final AccountRepository accountRepository;
    private final KitPvpStatusRepository kitPvpStatusRepository;

    private final ScoreBoardManager scoreBoardManager;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();

        AccountEntity account = accountRepository.getAccountByUuid(player.getUniqueId());

        if (account == null) {
            account = createNewAccounts(player);
        }

        account.setLastJoinDate(new Date());
        accountRepository.saveAccount(account);


        welcomeMessage(player);

        scoreBoardManager.register(player);
        scoreBoardManager.updateScoreBoard(player, ScoreBoards.Default);

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        scoreBoardManager.unregister(event.getPlayer());
    }

    private AccountEntity createNewAccounts(Player player) {

        AccountEntity account = new AccountEntity(
                player.getUniqueId(),
                player.getName(),
                new Date(),
                new Date(),
                Groups.MEMBER,
                Groups.MEMBER,
                Ranks.UNRANKED,
                0,
                0,
                null
        );

        KitPvpStatusEntity kitPvpStatus = new KitPvpStatusEntity(
                player.getUniqueId(),
                0,
                0,
                0.0
        );

        account.setKitPvpStatus(kitPvpStatus);

        final boolean isKitPvpSaved = kitPvpStatusRepository.saveStatus(kitPvpStatus);
        final boolean isAccountSaved = accountRepository.saveAccount(account);

        if (!isAccountSaved && !isKitPvpSaved) {
            player.kickPlayer(
                    "§cOuve um problema ao criar sua conta\n" +
                            "§cPor favor tente entrar novamente mais tarde"
            );
        }

        return account;
    }

    private void welcomeMessage(Player player) {
        for (int i = 0; i < 100; i++) {
            player.sendMessage("");
        }

        player.sendMessage("§aBem-vindo ao AtazPvP!");
    }

}
