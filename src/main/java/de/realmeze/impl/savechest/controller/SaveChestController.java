package de.realmeze.impl.savechest.controller;

import de.realmeze.impl.base.TexController;
import de.realmeze.impl.savechest.model.SaveChest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class SaveChestController extends TexController {

    private HashMap<Inventory, SaveChest> savedChestMap;
    private HashMap<UUID, Inventory> currentOpenChest;

    public void lockChest(Player player, Inventory chestInventory, boolean isDouble){
        SaveChest saveChest = new SaveChest(isDouble, "SaveChest : " + player.getUniqueId(), chestInventory.getContents(), player);
        getSavedChestMap().put(chestInventory, saveChest);
        if(isDouble){
            player.getInventory().removeItem(new ItemStack(Material.DIAMOND, 6));
        } else {
            player.getInventory().removeItem(new ItemStack(Material.DIAMOND, 3));
        }
        chestInventory.clear();
    }
    public void unlockChest(Player player, Inventory chestInventory){
        ItemStack[] chestContent = getLockedChest(chestInventory).getChestContent();
        for (int i = 0; i < chestContent.length ; i++) {
            if(i < 27){
                chestInventory.setItem(i, chestContent[i]);
            } else {
                player.getWorld().dropItem(player.getLocation(), chestContent[i]);
            }
        }
        getSavedChestMap().remove(chestInventory);
    }

    public Inventory getOpenChest(Player player){
        return getCurrentOpenChest().get(player.getUniqueId());
    }

    public void openChest(Player player, Inventory inventory){
        getCurrentOpenChest().put(player.getUniqueId(), inventory);
        SaveChest saveChest = getSavedChestMap().get(inventory);
        Inventory saveChestInventory = Bukkit.createInventory(player, saveChest.getSize(), saveChest.getChestTitle());
        saveChestInventory.setContents(saveChest.getChestContent());
        player.openInventory(saveChestInventory);
    }

    public void closeChest(Player player){
        getCurrentOpenChest().remove(player.getUniqueId());
    }

    public boolean isDoubleChest(Inventory inventory){
        return inventory.getSize() == 54;
    }

    public boolean isLocked(Inventory chestInventory){
        return getSavedChestMap().containsKey(chestInventory);
    }

    public SaveChest getLockedChest(Inventory chestInventory){
        return getSavedChestMap().get(chestInventory);
    }

    public void updateLockedChestContent(Inventory inventoryKey, Inventory updateWith){
        getLockedChest(inventoryKey).setChestContent(updateWith.getContents());
    }

    public boolean isSaveChest(Block block){
        if(block.getType() != Material.CHEST){
            return false;
        }
        Chest chest = (Chest) block.getState();
        Inventory inventory = chest.getInventory();
        if(isLocked(inventory)){
            return true;
        }
        return false;
    }

}
