package de.realmeze.impl.item.model;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private final ItemStack itemStack = new ItemStack(Material.AIR);

    public ItemBuilder(){

    }
    
    public ItemStack getItem() {
        return itemStack;
    }
    
    public Material getMaterial() {
        return this.itemStack.getType();
    }
    
    public ItemMeta getItemMeta() {
        return this.itemStack.getItemMeta();
    }
    
    public ItemBuilder setItemMeta(ItemMeta itemMeta) {
        getItem().setItemMeta(itemMeta);
        return this;
    }
    
    public ItemBuilder setData(int i){
        short s = (short) i;
        this.getItem().setDurability(s);
        return this;
    }

    public ItemBuilder setMaterial(Material material) {
        this.itemStack.setType(material);
        return this;
    }

    /**
     *
     * @param lore
     * @return
     */
    public ItemBuilder addLore(String lore) {
        ItemMeta im = this.getItemMeta();
        List<String> loreList = im.hasLore() ?  im.getLore() : new ArrayList<String>();
        loreList.add(lore);
        im.setLore(loreList);
        this.setItemMeta(im);
        return this;
    }
    
    public ItemBuilder setName(String name) {
        ItemMeta im = getItemMeta();
        im.setDisplayName(name);
        this.itemStack.setItemMeta(im);
        return this;
    }
    
    public ItemBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }
    
    public ItemBuilder setDurability(short durability) {
        this.itemStack.setDurability(durability);
        return this;
    }

}
