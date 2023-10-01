package de.realmeze.listener;

import de.realmeze.impl.gui.controller.GuiController;
import de.realmeze.impl.gui.model.ClickableInventory;
import de.realmeze.impl.gui.model.ClickableSlot;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryClickListener implements Listener {

    private GuiController inventoryController;

    public InventoryClickListener(GuiController guiController) {
        setInventoryController(guiController);
    }

    public GuiController getInventoryController() {
        return inventoryController;
    }

    public void setInventoryController(GuiController inventoryController) {
        this.inventoryController = inventoryController;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String guiIdentifier = getInventoryController().getGuiIdentifier();
        if (!event.getInventory().getTitle().contains(guiIdentifier)) {
            return;
        }
        if(event.getWhoClicked().getOpenInventory().getTopInventory().getTitle().contains(guiIdentifier)){
            event.setCancelled(true);
        }
        Inventory clickedInventory = event.getClickedInventory();

        for (ClickableInventory clickableInventory : getInventoryController().getInventories().values()) {
            if (clickedInventory.getTitle().equalsIgnoreCase(clickableInventory.getTitle() + guiIdentifier)) {
                for (ClickableSlot actionSlot : clickableInventory.getClickableSlots()) {
                    if (event.getSlot() == actionSlot.getSlot()) {
                        Bukkit.dispatchCommand(event.getWhoClicked(), actionSlot.getCommandToExecute());
                    }
                }
            }
        }
    }
}
