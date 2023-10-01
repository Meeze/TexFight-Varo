package de.realmeze.impl.shop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
@AllArgsConstructor
public class ShopItem {
    private final ItemStack item;
    private final long cost;

    public Material getMaterial(){
        return item.getType();
    }
}
