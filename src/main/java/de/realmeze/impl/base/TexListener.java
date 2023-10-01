package de.realmeze.impl.base;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public interface TexListener extends Listener {

    String prefix = ChatColor.RED + "TexFight> " + ChatColor.GRAY;

    default void sendTo(Player player, String message, boolean withPrefix) {
        if(withPrefix) {
            message = prefix + message;
        }
        sendToPlayer(player, message);
    }

    default void sendAll(String message, boolean withPrefix) {
        if(withPrefix) {
            message = prefix + message;
        }
        sendToAllPlayers(message);
    }

    default void sendToAllPlayers(String message) {
        Bukkit.broadcastMessage(message);
    }

    default void sendToPlayer(Player player, String message) {
        player.sendMessage(message);
    }

}
