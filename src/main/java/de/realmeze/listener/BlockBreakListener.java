package de.realmeze.listener;

import de.realmeze.impl.base.TexListener;
import de.realmeze.impl.savechest.controller.SaveChestController;
import de.realmeze.impl.spawner.controller.SpawnerController;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

@Getter
@RequiredArgsConstructor
public class BlockBreakListener implements TexListener {

    private final SaveChestController saveChestController;
    private final SpawnerController spawnerController;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        if (getSaveChestController().isSaveChest(block)) {
            sendTo(player, "Du kannst das nicht zerstören", true);
            event.setCancelled(true);
        } else {
            if(block.getType() == Material.MOB_SPAWNER){
                BlockState state = block.getState();
                CreatureSpawner creatureSpawner = (CreatureSpawner) state;
            } else if(getSpawnerController().getDropsByMining().contains(block.getType())){
                getSpawnerController().doDrop(player);
            }
        }
    }
}
