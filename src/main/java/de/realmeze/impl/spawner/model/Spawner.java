package de.realmeze.impl.spawner.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

@RequiredArgsConstructor
@Getter
public class Spawner {

    private final SpawnerType spawnerType;
    private final String name;


}
