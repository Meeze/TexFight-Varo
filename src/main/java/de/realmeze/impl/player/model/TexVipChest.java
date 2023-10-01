package de.realmeze.impl.player.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Getter
@AllArgsConstructor
@Setter
public class TexVipChest {

    private int chestSize;
    private String chestTitle;
    private ItemStack[] chestContent;
}
