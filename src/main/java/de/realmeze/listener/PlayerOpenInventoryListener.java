package de.realmeze.listener;

import de.realmeze.impl.base.TexListener;
import de.realmeze.impl.savechest.controller.SaveChestController;
import de.realmeze.impl.savechest.model.SaveChest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;

@Getter
@AllArgsConstructor
public class PlayerOpenInventoryListener implements TexListener {

    private SaveChestController saveChestController;

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory.getType() != InventoryType.CHEST) {
            return;
        }
        Player player = (Player) event.getPlayer();
        if (getSaveChestController().isLocked(inventory)) {
            SaveChest saveChest = getSaveChestController().getLockedChest(inventory);
            if(saveChest.getOwner() != player){
                sendTo(player, "Du kannst diese Kisten nicht oeffnen!", true);
                event.setCancelled(true);
            } else {
                sendTo(player, saveChest.toString(), true);
                getSaveChestController().openChest(player, inventory);
                event.setCancelled(true);
            }
        }
    }
}
