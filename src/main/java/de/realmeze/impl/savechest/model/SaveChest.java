package de.realmeze.impl.savechest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
@AllArgsConstructor
@Setter
public class SaveChest {

    private boolean isDoubleChest;
    private String chestTitle;
    private ItemStack[] chestContent;
    private Player owner;

    public int getSize(){
        if(isDoubleChest()){
            return 54;
        } else
            return 27;
    }

}
