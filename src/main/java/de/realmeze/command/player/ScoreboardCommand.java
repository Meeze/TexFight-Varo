package de.realmeze.command.player;

import co.aikar.commands.annotation.*;
import de.realmeze.impl.base.TexCommand;
import de.realmeze.impl.player.controller.TexPlayerController;
import de.realmeze.impl.scoreboard.controller.ScoreboardController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("scoreboard")
@Getter
@AllArgsConstructor
public class ScoreboardCommand extends TexCommand {

    private final ScoreboardController scoreboardController;
    private final TexPlayerController texPlayerController;

    @Default
    @CatchUnknown
    public void onScoreboard(Player player) {
        sendTo(player, "/scoreboard <on/off>", true);
        return;
    }

    @Subcommand("off")
    public void onScoreboardOff(Player player) {
        getScoreboardController().disableScoreboard(getTexPlayerController().getPlayer(player));
        sendTo(player, "scoreboard off", true);
    }

    @Subcommand("on")
    public void onScoreboardOn(Player player) {
        getScoreboardController().enableScoreboard(getTexPlayerController().getPlayer(player));
        sendTo(player, "scoreboard on", true);
    }

}
