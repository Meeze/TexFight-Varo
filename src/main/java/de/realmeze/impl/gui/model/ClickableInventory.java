package de.realmeze.impl.gui.model;

import org.bukkit.inventory.ItemStack;

public class ClickableInventory {

    private ItemStack[] inventoryContent;
    private int size;
    private String title;
    private ClickableSlot[] clickableSlots;

    public ClickableInventory(ItemStack[] inventoryContent, int size, String title, ClickableSlot[] clickableSlots) {
        this.inventoryContent = inventoryContent;
        this.size = size;
        this.title = title;
        this.clickableSlots = clickableSlots;
    }

    public ItemStack[] getInventoryContent() {
        return inventoryContent;
    }

    public void setInventoryContent(ItemStack[] inventoryContent) {
        this.inventoryContent = inventoryContent;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ClickableSlot[] getClickableSlots() {
        return clickableSlots;
    }

    public void setClickableSlots(ClickableSlot[] clickableSlots) {
        this.clickableSlots = clickableSlots;
    }
}
