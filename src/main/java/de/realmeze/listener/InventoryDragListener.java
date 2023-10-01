package de.realmeze.listener;

import de.realmeze.impl.gui.controller.GuiController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryDragListener implements Listener {

    private GuiController guiController;

    public InventoryDragListener(GuiController guiController) {
        setGuiController(guiController);
    }

    public GuiController getGuiController() {
        return guiController;
    }

    public void setGuiController(GuiController guiController) {
        this.guiController = guiController;
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        String guiIdentifier = getGuiController().getGuiIdentifier();
        // just to be safe cause mc is retarded sometimes lul
        if (event.getInventory().getTitle().contains(guiIdentifier) || event.getWhoClicked().getOpenInventory().getTopInventory().getTitle().contains(guiIdentifier)) {
            event.setCancelled(true);
        }

    }
}
