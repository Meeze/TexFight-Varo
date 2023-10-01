package de.realmeze.command.event;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import de.realmeze.impl.base.TexCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Random;

@CommandAlias("selectrandomplayer|randomplayer|random")
public class SelectRandomPlayerCommand extends TexCommand {

    @Default
    public void onSelectRandomPlayer() {
        Player selectedRandomPlayer = selectRandomPlayer();
        sendAll("Der Spieler " +  selectedRandomPlayer.getName() + " wurde zufaellig ausgewaehlt", true);
    }

    private Player selectRandomPlayer() {
        int playerCount = Bukkit.getOnlinePlayers().size();
        Player[] onlinePlayers = Bukkit.getOnlinePlayers().toArray(new Player[playerCount]);
        int randomInArrayRange = new Random().nextInt(playerCount);
        return onlinePlayers[randomInArrayRange];
    }

}
