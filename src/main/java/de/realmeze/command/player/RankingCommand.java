package de.realmeze.command.player;

import co.aikar.commands.annotation.CatchUnknown;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import de.realmeze.impl.base.TexCommand;
import de.realmeze.impl.player.controller.RankingController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
@CommandAlias("ranking|top|leaderboard")
public class RankingCommand extends TexCommand {

    private final RankingController rankingController;

    @Default
    @CatchUnknown
    public void onRanking(Player player) {
        getRankingController().getKillRanking().forEach(texPlayer -> {
            sendTo(player, texPlayer.getPlayer().getName() + "Kills: " + texPlayer.getTexStats().getKills(), true);
        });
    }


}
