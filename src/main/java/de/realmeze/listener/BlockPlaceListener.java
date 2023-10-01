package de.realmeze.listener;

import co.aikar.commands.annotation.CatchUnknown;
import de.realmeze.impl.base.TexListener;
import de.realmeze.impl.savechest.controller.SaveChestController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.DoubleChestInventory;

@Getter
@AllArgsConstructor
public class BlockPlaceListener implements TexListener {

    private final SaveChestController saveChestController;

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Location location = block.getLocation();
        Bukkit.broadcastMessage("works");
        if (!isChest(block)) {
            return;
        }
        Block b1 = location.getBlock().getRelative(BlockFace.NORTH);
        Block b2 = location.getBlock().getRelative(BlockFace.EAST);
        Block b3 = location.getBlock().getRelative(BlockFace.SOUTH);
        Block b4 = location.getBlock().getRelative(BlockFace.WEST);
        if (doCheck(b1, event.getPlayer())) {
            Bukkit.broadcastMessage("is true");
            event.setCancelled(true);
        } else if (doCheck(b2, event.getPlayer())) {
            Bukkit.broadcastMessage("works lul");
            event.setCancelled(true);
        } else if (doCheck(b3, event.getPlayer())) {
            Bukkit.broadcastMessage("works lul");
            event.setCancelled(true);
        } else if (doCheck(b4, event.getPlayer())) {
            Bukkit.broadcastMessage("works lul");
            event.setCancelled(true);
        }

    }

    private boolean doCheck(Block potentialChest, Player player) {
        if (isChest(potentialChest)) {
            Chest chest = (Chest) potentialChest.getState();
            // jesus double chest api is aids af pls recode mr microsoft
            DoubleChestInventory doubleChestInventory = (DoubleChestInventory) chest.getInventory();
            if (getSaveChestController().isLocked(doubleChestInventory.getLeftSide()) || getSaveChestController().isLocked(doubleChestInventory.getRightSide())) {
                sendTo(player, "Du kannst keine Kiste neben einer Savechest platzieren!", true);
                return true;
            }
        }
        return false;

    }

    private boolean isChest(Block block) {
        return block.getType() == Material.CHEST;
    }

}
