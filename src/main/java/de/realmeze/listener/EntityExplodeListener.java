package de.realmeze.listener;

import de.realmeze.impl.base.TexListener;
import de.realmeze.impl.savechest.controller.SaveChestController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.ArrayList;
import java.util.Objects;

@AllArgsConstructor
@Getter
public class EntityExplodeListener implements TexListener {

    private final SaveChestController saveChestController;

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event){
        ArrayList<Block> blocksClone = new ArrayList<>(event.blockList());
        blocksClone.stream().filter(Objects::nonNull).filter(getSaveChestController()::isSaveChest).forEach(event.blockList()::remove);
    }


}
