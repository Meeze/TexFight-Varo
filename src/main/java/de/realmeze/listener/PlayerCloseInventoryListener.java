package de.realmeze.listener;

import de.realmeze.impl.player.controller.TexPlayerController;
import de.realmeze.impl.player.model.TexPlayer;
import de.realmeze.impl.savechest.controller.SaveChestController;
import de.realmeze.impl.savechest.model.SaveChest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

@AllArgsConstructor
@Getter
public class PlayerCloseInventoryListener implements Listener {

    private TexPlayerController texPlayerController;
    private final SaveChestController saveChestController;

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if(!(event.getPlayer() instanceof Player)){
           return;
        }
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();
        if(inventory.getName().equalsIgnoreCase("Backpack : " + player.getUniqueId())){
            TexPlayer texPlayer = texPlayerController.getPlayer(player);
            texPlayer.saveContentToBackpack(event.getInventory().getContents());
        } else if(inventory.getName().equalsIgnoreCase("VipChest : " + player.getUniqueId())) {
            TexPlayer texPlayer = texPlayerController.getPlayer(player);
            texPlayer.saveContentToVipChest(event.getInventory().getContents());
        } else if(inventory.getName().equalsIgnoreCase("SaveChest : " + player.getUniqueId())){
            Bukkit.broadcastMessage("savechest detected");
            Inventory invKey = getSaveChestController().getOpenChest(player);
            getSaveChestController().updateLockedChestContent(invKey, inventory);
            getSaveChestController().closeChest(player);
        }
    }

}
