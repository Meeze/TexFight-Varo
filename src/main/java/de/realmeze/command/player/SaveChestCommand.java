package de.realmeze.command.player;

import co.aikar.commands.annotation.*;
import de.realmeze.impl.base.TexCommand;
import de.realmeze.impl.savechest.controller.SaveChestController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

@CommandAlias("savechest")
@Getter
@AllArgsConstructor
public class SaveChestCommand extends TexCommand {

    private final SaveChestController saveChestController;

    @Default
    @CatchUnknown
    public void onSaveChestHelp(Player player){
        sendTo(player, "/savechest lock|unlock double|single", true);
        sendTo(player, "doublechest=6dias singlechest=3dias", true);
    }

    @Subcommand("lock")
    @CommandCompletion("@chest-types")
    public void onSaveChestLock(Player player, String chestType){
        Boolean isDouble = parseIsDoubleChest(chestType);
        if(isDouble == null){
            onSaveChestHelp(player);
            return;
        }
        Block block = player.getTargetBlock(getToIgnore(), 5);
        if(block.getType() != Material.CHEST){
            sendTo(player, "Du musst eine Kiste anschauen!", true);
            return;
        }
        Chest chest = (Chest) block.getState();
        Inventory chestInventory = chest.getInventory();
        if(getSaveChestController().isLocked(chestInventory)){
            sendTo(player, "Diese Kiste ist bereits gelocked!", true);
            return;
        }
        if(getSaveChestController().isDoubleChest(chestInventory)){
            sendTo(player, "Um Doppelkisten zu sichern, schaue bitte auf eine einzelne Kiste und benute den /savechest lock double befehl!", true);
            return;
        }
        if(isDouble){
            if(!player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 6)){
                sendTo(player, "Du brauchst 6 Diamanten um eine dc zu sichern", true);
                return;
            }
        } else {
            if(!player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 3)){
                sendTo(player, "Du brauchst 3 Diamanten um eine Kiste zu sichern", true);
                return;
            }
        }

        sendTo(player, "Deine Kiste wurde gesichert!", true);
        getSaveChestController().lockChest(player, chestInventory, isDouble);
    }

    @Subcommand("unlock")
    public void onSaveChestUnlock(Player player){
        Block block = player.getTargetBlock(getToIgnore(), 5);
        if(block.getType() != Material.CHEST){
            sendTo(player, "Du musst eine Kiste anschauen!", true);
            return;
        }
        Chest chest = (Chest) block.getState();
        Inventory chestInventory = chest.getInventory();
        if(!getSaveChestController().isLocked(chestInventory)){
            sendTo(player, "Diese Kiste ist nicht gelocked!", true);
            return;
        }
        getSaveChestController().unlockChest(player, chestInventory);
    }

    private Boolean parseIsDoubleChest(String chestType){
        if(chestType.equalsIgnoreCase("double")){
            return true;
        } else if (chestType.equalsIgnoreCase("single")){
            return false;
        }
        return null;
    }

    private Set<Material> getToIgnore(){
        Set<Material> toIgnore = new HashSet<>();
        toIgnore.add(Material.AIR);
        toIgnore.add(Material.SNOW);
        toIgnore.add(Material.GRASS);
        toIgnore.add(Material.LONG_GRASS);
        return toIgnore;
    }

}
