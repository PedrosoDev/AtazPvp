package com.github.pedrosodev.atazpvp.initializers;

import com.github.pedrosodev.atazpvp.commands.AccountCommand;
import com.github.pedrosodev.atazpvp.commands.ScoreBoardCommand;
import com.github.pedrosodev.atazpvp.interfaces.Initializer;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import redempt.redlib.commandmanager.CommandParser;

public class CommandInitializer implements Initializer {

    @Inject
    private JavaPlugin javaPlugin;

    @Override
    public void init(final @NotNull Injector injector) {
        new CommandParser(javaPlugin.getResource("command.rdcml")).parse().register("kitpvp",
            injector.getInstance(AccountCommand.class),
            injector.getInstance(ScoreBoardCommand.class)
        );
    }
}
