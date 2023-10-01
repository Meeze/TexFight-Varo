package de.realmeze.impl.player.model;

import org.bukkit.inventory.ItemStack;

public class TexBackpack {
    private int backpackSize;
    private String backpackTitle;
    private ItemStack[] backpackContent;

    public TexBackpack(int backpackSize, String backpackTitle , ItemStack[] backpackContent) {
        setBackpackSize(backpackSize);
        setBackpackTitle(backpackTitle);
        setBackpackContent(backpackContent);
    }

    public ItemStack[] getBackpackContent() {
        return backpackContent;
    }

    public void setBackpackContent(ItemStack[] backpackContent) {
        this.backpackContent = backpackContent;
    }

    public String getBackpackTitle() {
        return backpackTitle;
    }

    public void setBackpackTitle(String backpackTitle) {
        this.backpackTitle = backpackTitle;
    }

    public int getBackpackSize() {
        return backpackSize;
    }

    public void setBackpackSize(int backpackSize) {
        this.backpackSize = backpackSize;
    }
}
