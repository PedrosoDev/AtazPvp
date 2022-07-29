package com.github.pedrosodev.atazpvp.commands;

import com.github.pedrosodev.atazpvp.entities.AccountEntity;
import com.github.pedrosodev.atazpvp.entities.KitPvpStatusEntity;
import com.github.pedrosodev.atazpvp.repositories.AccountRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import redempt.redlib.commandmanager.CommandHook;

import java.text.SimpleDateFormat;


@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class AccountCommand {

    private final AccountRepository repository;

    @CommandHook("account")
    public void accountCommand(Player playerSender, Player playerTarget) {

        if (playerTarget == null) {
            playerTarget = playerSender;
        }

        AccountEntity account = repository.getAccountByUuid(playerTarget.getUniqueId());
        KitPvpStatusEntity kitPvpStatus = account.getKitPvpStatus();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        playerSender.sendMessage("§b-=-=-=-=-=-=-=(§f*§b)=-=-=-=-=-=-=-");
        playerSender.sendMessage("§fCriado em: §b" + dateFormat.format(account.getFirstJoinDate()));
        playerSender.sendMessage("§fGrupo: §b" + account.getGroup().getBoldColor() + account.getGroup().getName());
        playerSender.sendMessage("§fCoins §b" + account.getCoins());
        playerSender.sendMessage("§fKills: §b" + kitPvpStatus.getKills());
        playerSender.sendMessage("§fDeaths: §b" + kitPvpStatus.getDeaths());
        playerSender.sendMessage("§fKDR: §b" + kitPvpStatus.getKdr());
        playerSender.sendMessage("§b-=-=-=-=-=-=-=(§f*§b)=-=-=-=-=-=-=-");

    }

}
