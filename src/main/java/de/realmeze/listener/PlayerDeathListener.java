package de.realmeze.listener;

import de.realmeze.impl.base.TexListener;
import de.realmeze.impl.player.controller.TexPlayerController;
import de.realmeze.impl.player.model.TexPlayer;
import de.realmeze.impl.scoreboard.controller.ScoreboardController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

@Getter
@AllArgsConstructor
public class PlayerDeathListener implements TexListener {

    private final TexPlayerController texPlayerController;
    private final ScoreboardController scoreboardController;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = event.getEntity().getKiller();
        if(killer == null) {
            return;
        }
        TexPlayer texPlayer = getTexPlayerController().getPlayer(player);
        TexPlayer texKiller = getTexPlayerController().getPlayer(killer);
        getTexPlayerController().kill(texPlayer, texKiller);
        Bukkit.broadcastMessage(player.getName() + "wurde von " + killer.getName() + " getötet!");
        //update board
        getScoreboardController().setStats(texPlayer);
        getScoreboardController().setLives(texPlayer);
        getScoreboardController().setStats(texKiller);
    }

}
