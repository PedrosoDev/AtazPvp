package com.github.pedrosodev.atazpvp.enums;

import com.github.pedrosodev.atazpvp.dependencyinjection.AtazPvpModule;
import com.github.pedrosodev.atazpvp.interfaces.Score;
import com.github.pedrosodev.atazpvp.scoreboards.DefaultScoreBoard;
import com.google.inject.Guice;
import com.google.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ScoreBoards {
    Default(DefaultScoreBoard.class);

    private final Class<? extends Score> scoreboardClass;

}
