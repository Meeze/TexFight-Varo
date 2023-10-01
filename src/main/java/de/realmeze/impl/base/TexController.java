package de.realmeze.impl.base;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class TexController {

    String prefix = ChatColor.RED + "TexFight> " + ChatColor.GRAY;

    protected void sendTo(Player player, String message, boolean withPrefix) {
        if(withPrefix) {
            message = prefix + message;
        }
        sendToPlayer(player, message);
    }

    protected void sendAll(String message, boolean withPrefix) {
        if(withPrefix) {
            message = prefix + message;
        }
        sendToAllPlayers(message);
    }

    protected void sendToAllPlayers(String message) {
        Bukkit.broadcastMessage(message);
    }

    protected void sendToPlayer(Player player, String message) {
        player.sendMessage(message);
    }

}
