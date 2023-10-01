package de.realmeze.command.stats;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import de.realmeze.impl.base.TexCommand;
import de.realmeze.impl.player.controller.TexPlayerController;
import de.realmeze.impl.player.model.TexPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("stats|statistic")
public class StatsCommand extends TexCommand {

    private TexPlayerController texPlayerController;

    public StatsCommand(TexPlayerController texPlayerController) {
        setTexPlayerController(texPlayerController);
    }

    public TexPlayerController getTexPlayerController() {
        return texPlayerController;
    }

    public void setTexPlayerController(TexPlayerController texPlayerController) {
        this.texPlayerController = texPlayerController;
    }

    @Default
    @CommandCompletion("@players")
    public void onStatsCommand(Player player, String[] args) {
        if (args.length == 0) {
            TexPlayer texPlayer = texPlayerController.getTexPlayerMap().get(player.getUniqueId());
            if (texPlayer == null) {
                sendTexPlayerNotRegistered(player);
                return;
            }
            statsCommand(player, texPlayer);
        } else if (args.length == 1) {
            if (isValidTarget(player, args[0])) {
                TexPlayer texPlayer = texPlayerController.getTexPlayerMap().get(Bukkit.getPlayer(args[0]).getUniqueId());
                if (texPlayer == null) {
                    sendTexPlayerNotRegistered(player);
                    return;
                }
                statsCommand(player, texPlayer);
            }
        }
    }

    private void statsCommand(Player sender, TexPlayer texPlayerTarget) {
        sendTo(sender, "Stats von " + texPlayerTarget.getPlayer().getName(), true);
        sendTo(sender, "Kills: " + texPlayerTarget.getTexStats().getKills(), false);
        sendTo(sender, "Deaths: " + texPlayerTarget.getTexStats().getDeaths(), false);
        sendTo(sender, "Lives: " + texPlayerTarget.getTexStats().getLives(), false);
        sendTo(sender, "Ontime: " + texPlayerTarget.getTexStats().getOnlineTime(), false);
    }

}
