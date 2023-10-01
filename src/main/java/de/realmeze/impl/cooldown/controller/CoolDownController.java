package de.realmeze.impl.cooldown.controller;

import de.realmeze.impl.base.TexController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;

@Getter
@AllArgsConstructor
public class CoolDownController extends TexController {

    private final HashMap<Player, Instant> feedCoolDownMap;

    public void setFeedOnCooldown(Player player) {
        getFeedCoolDownMap().put(player, Instant.now().plus(5, ChronoUnit.MINUTES));
    }

    /**
     *
     * @param player
     * @return true if player can use command
     */
    public boolean checkFeedCoolDown(Player player) {
      return checkCoolDownFor(getFeedCoolDownMap(), player);
    }

    private boolean checkCoolDownFor(HashMap<Player, Instant> coolDownMap, Player player) {
        if(!coolDownMap.containsKey(player)){
            return true;
        }
        boolean isOffCooldown = coolDownMap.get(player).isBefore(Instant.now());
        if(isOffCooldown) {
            coolDownMap.remove(player);
        }
        return isOffCooldown;
    }

}
