package de.realmeze.impl.staff.controller;

import de.realmeze.impl.base.TexController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

@AllArgsConstructor
@Getter
@Setter
public class StaffController extends TexController {

    private ArrayList<Player> onlineStaff;

    public void staffJoined(Player player) {
        getOnlineStaff().add(player);
    }

    public boolean isStaff(Player player) {
        return player.hasPermission("texfight.staff");
    }

}
