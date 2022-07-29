package com.github.pedrosodev.atazpvp.commands;

import com.github.pedrosodev.atazpvp.entities.AccountEntity;
import com.github.pedrosodev.atazpvp.entities.KitPvpStatusEntity;
import com.github.pedrosodev.atazpvp.managers.ScoreBoardManager;
import com.github.pedrosodev.atazpvp.repositories.AccountRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import redempt.redlib.commandmanager.CommandHook;

import java.text.SimpleDateFormat;


@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class ScoreBoardCommand {

    private final ScoreBoardManager scoreBoardManager;

    @CommandHook("scoreboard")
    public void scoreboardCommand(Player playerSender) {

        if (scoreBoardManager.isRegistered(playerSender)) {
            scoreBoardManager.unregister(playerSender);
            playerSender.sendMessage("§bScoreboard desativado!");
        }else {
            scoreBoardManager.register(playerSender);
            playerSender.sendMessage("§bScoreboard ativado!");
        }

    }

}
