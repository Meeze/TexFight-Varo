package de.realmeze.listener;

import de.realmeze.impl.base.TexListener;
import de.realmeze.impl.player.controller.TexPlayerController;
import de.realmeze.impl.player.model.TexPlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

@Getter
@AllArgsConstructor
public class PlayerQuitListener implements TexListener {

    private final TexPlayerController texPlayerController;

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        event.setQuitMessage(null);
        Player player = event.getPlayer();
        TexPlayer texPlayer = getTexPlayerController().getPlayer(player);
        getTexPlayerController().logout(texPlayer);
    }

}
