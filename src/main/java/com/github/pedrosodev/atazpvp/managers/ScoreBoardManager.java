package com.github.pedrosodev.atazpvp.managers;

import com.github.pedrosodev.atazpvp.enums.ScoreBoards;
import com.github.pedrosodev.atazpvp.interfaces.Registrable;
import com.github.pedrosodev.atazpvp.interfaces.Score;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import fr.mrmicky.fastboard.FastBoard;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class ScoreBoardManager implements Registrable {

    private final Injector injector;
    static Map<UUID, FastBoard> boards;
    static Map<UUID, ScoreBoards> boardTypes;

    @PostConstruct
    public void init() {
        boards = new HashMap<>();
        boardTypes = new HashMap<>();
    }

    public void updateScoreBoard(Player player, ScoreBoards scoreBoard) {
        FastBoard board = boards.get(player.getUniqueId());
        boardTypes.put(player.getUniqueId(), scoreBoard);
        Score score = injector.getInstance(scoreBoard.getScoreboardClass());
        score.update(board);
    }

    @Override
    public void register(@NotNull Player player) {
        boards.putIfAbsent(player.getUniqueId(), new FastBoard(player));

        if (boardTypes.containsKey(player.getUniqueId())) {
            updateScoreBoard(player, boardTypes.get(player.getUniqueId()));
        }
    }

    @Override
    public void unregister(@NotNull Player player) {
        FastBoard board = boards.remove(player.getUniqueId());
        if (board != null) {
            board.delete();
        }
    }

    @Override
    public boolean isRegistered(@NotNull Player player) {
        return boards.containsKey(player.getUniqueId());
    }

}
