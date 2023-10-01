package de.realmeze.impl.player.controller;

import de.realmeze.impl.base.TexController;
import de.realmeze.impl.player.model.*;
import de.realmeze.main.TexFightMain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class TexPlayerController extends TexController {

    private final TexFightMain pluginMain;
    private final HashMap<UUID, TexPlayer> texPlayerMap;
    private final HashMap<TexPlayer, Instant> loginTimeMap;

    private TexPlayer registerPlayer(Player player) {
        TexPlayer texPlayer = new TexPlayer(player);
        texPlayer.setMoney(new TexMoney(100000));
        texPlayer.setBackpack(new TexBackpack(9, "Backpack : " + player.getUniqueId(), new ItemStack[9]));
        texPlayer.setTexStats(new TexStats(11L, 2L, 5, 0));
        //check if player is vip :)
        if (true) {
            texPlayer.setTexVipChest(new TexVipChest(27, "VipChest : " + player.getUniqueId(), new ItemStack[27]));
        }
        texPlayer.setTeam(null);
        getTexPlayerMap().put(player.getUniqueId(), texPlayer);
        return texPlayer;
    }

    public TexPlayer getPlayer(Player player) {
        return getTexPlayerMap().get(player.getUniqueId());
    }

    public void kill(TexPlayer playerWhoDied, TexPlayer killer) {
        playerWhoDied.getTexStats().addDeath();
        playerWhoDied.getTexStats().removeLive();
        killer.getTexStats().addKill();
        killer.getMoney().add(5);
    }

    public TexPlayer login(Player player){
        TexPlayer texPlayer;
        if(getTexPlayerMap().containsKey(player.getUniqueId())){
            texPlayer = getPlayer(player);
        } else {
            texPlayer = registerPlayer(player);
        }
        getLoginTimeMap().put(texPlayer, Instant.now());
        return texPlayer;
    }

    public void logout(TexPlayer player){
        long seconds = getSecondsBetweenLogin(player);
        player.getTexStats().addOnlineTime(seconds);
        getLoginTimeMap().remove(player);
    }

    private long getSecondsBetweenLogin(TexPlayer player){
        Instant loginTime = getLoginTimeMap().get(player);
        Duration between = Duration.between(loginTime, Instant.now());
        long seconds = between.getSeconds();
        return seconds;
    }

}
