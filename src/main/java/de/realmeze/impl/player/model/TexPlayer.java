package de.realmeze.impl.player.model;

import de.realmeze.impl.teams.model.Team;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class TexPlayer {

    private Player player;
    private TexMoney money;
    private TexBackpack backpack;
    private TexVipChest texVipChest;
    private TexStats texStats;
    private Team team;

    public TexPlayer(Player player) {
        setPlayer(player);
    }

    public void saveContentToBackpack(ItemStack[] content){
        getBackpack().setBackpackContent(content);
    }
    public void saveContentToVipChest(ItemStack[] content){
        getTexVipChest().setChestContent(content);
    }

}
