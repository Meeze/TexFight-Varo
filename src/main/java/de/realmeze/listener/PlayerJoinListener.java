package de.realmeze.listener;

import de.realmeze.impl.base.TexListener;
import de.realmeze.impl.player.controller.TexPlayerController;
import de.realmeze.impl.player.model.TexPlayer;
import de.realmeze.impl.scoreboard.controller.ScoreboardController;
import de.realmeze.impl.staff.controller.StaffController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

@Getter
@AllArgsConstructor
public class PlayerJoinListener implements TexListener {

    private final TexPlayerController texPlayerController;
    private final StaffController staffController;
    private final ScoreboardController scoreboardController;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player player = event.getPlayer();
        TexPlayer texPlayer = texPlayerController.login(player);
        if(getStaffController().isStaff(player)) {
            Bukkit.broadcastMessage(ChatColor.GREEN + "+ STAFF> " + texPlayer.getPlayer().getDisplayName());
            getStaffController().staffJoined(player);
        } else {
            Bukkit.broadcastMessage(ChatColor.GREEN + "+" + texPlayer.getPlayer().getDisplayName());
        }
        getScoreboardController().registerBoard(texPlayer);
        getScoreboardController().enableScoreboard(texPlayer);
    }
}
