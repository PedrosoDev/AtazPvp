package com.github.pedrosodev.atazpvp.scoreboards;

import com.github.pedrosodev.atazpvp.entities.AccountEntity;
import com.github.pedrosodev.atazpvp.entities.KitPvpStatusEntity;
import com.github.pedrosodev.atazpvp.interfaces.Score;
import com.github.pedrosodev.atazpvp.repositories.AccountRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import fr.mrmicky.fastboard.FastBoard;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class DefaultScoreBoard implements Score {

    private final AccountRepository accountRepository;

    @Override
    public void update(FastBoard board) {
        Player player = board.getPlayer();
        AccountEntity account = accountRepository.getAccountByUuid(player.getUniqueId());
        KitPvpStatusEntity kitPvpStatus = account.getKitPvpStatus();

        // TODO: 28/07/2022 - Implement a title for the custom scoreboard via config.yml
        board.updateTitle("§b§lAtaz§fPvP");

        board.updateLines(
                "",
                " §fgrupo: §b" + account.getGroup().getBoldColor() + account.getGroup().getName(),
                " §frank: §b" + account.getRank().getColor() + account.getRank().getName(),
                "",
                " §fkills: §b" + kitPvpStatus.getKills(),
                " §fdeaths: §b" + kitPvpStatus.getDeaths(),
                " §fkdr: §b" + kitPvpStatus.getKdr(),
                "",
                " §fmoedas: §b" + account.getCoins(),
                " §fxp: §b" + account.getXp(),
                "",
                // TODO: 28/07/2022 - Implement website link via config.yml
                " 4§bwww.atazpvp.com"
        );
    }
    
}
