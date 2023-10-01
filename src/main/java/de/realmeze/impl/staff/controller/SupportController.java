package de.realmeze.impl.staff.controller;

import de.realmeze.impl.staff.model.SupportRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
public class SupportController {

    private HashMap<String, SupportRequest> supportRequestMap;
    /**
     * key = player who is in support, value supporter
     */
    private HashMap<Player, Player> supportMap;

    public void requestSupport(Player player) {
        SupportRequest supportRequest = new SupportRequest(player);
        getSupportRequestMap().put(player.getName(), supportRequest);
    }

    public boolean acceptSupportRequest(String requesterName, Player supporter) {
        if (!getSupportRequestMap().containsKey(requesterName)) {
            return false;
        } else {
            getSupportRequestMap().remove(requesterName);
            Player requester = Bukkit.getPlayer(requesterName);
            getSupportMap().put(requester, supporter);
            getSupportMap().put(supporter, requester);
            return true;
        }
    }

    public Player closeSupport(Player whoClosed) {
        Player supportPartner = getSupportMap().get(whoClosed);
        getSupportMap().remove(whoClosed);
        getSupportMap().remove(supportPartner);
        return supportPartner;
    }

    public boolean isInSupportChat(Player player) {
        return getSupportMap().containsKey(player);
    }

    public boolean needsSupport(String playerName) {
        return getSupportRequestMap().containsKey(playerName);
    }


}
