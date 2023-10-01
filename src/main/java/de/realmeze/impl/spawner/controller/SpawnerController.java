package de.realmeze.impl.spawner.controller;

import de.realmeze.impl.base.TexController;
import de.realmeze.impl.item.model.ItemBuilder;
import de.realmeze.impl.spawner.model.Spawner;
import de.realmeze.impl.spawner.model.SpawnerType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

@RequiredArgsConstructor
@Getter
@Setter
public class SpawnerController extends TexController {

    private final ArrayList<Spawner> spawnersToDrop;
    private final ArrayList<Material> dropsByMining;
    // geteilt durch 10000 ergibt chance 100= (0.01)
    private int chance = 5000;
    private Random random = new Random();


    public void init(){
        initSpawners();
        initDrops();
    }

    private void initSpawners() {
        this.spawnersToDrop.add(new Spawner(SpawnerType.SKELETON, "Skelett"));
        this.spawnersToDrop.add(new Spawner(SpawnerType.SPIDER, "Spinne"));
        this.spawnersToDrop.add(new Spawner(SpawnerType.CREEPER, "Creeper"));
        this.spawnersToDrop.add(new Spawner(SpawnerType.ENDERMAN, "Enderman"));
        this.spawnersToDrop.add(new Spawner(SpawnerType.BLAZE, "Lohe"));
        this.spawnersToDrop.add(new Spawner(SpawnerType.IRON, "Eisen"));
        this.spawnersToDrop.add(new Spawner(SpawnerType.GOLD, "Gold"));
        this.spawnersToDrop.add(new Spawner(SpawnerType.DIAMOND, "Diamant"));
    }
    private void initDrops() {
       this.dropsByMining.add(Material.STONE);
       this.dropsByMining.add(Material.COAL_ORE);
    }

    public boolean doDrop(Player player) {
        Bukkit.broadcastMessage("dropchance");
        if (drops()) {
            Spawner spawner = getRandomDrop();
            givePlayer(player, spawner);
            return true;
        } else {
            return false;
        }
    }

    private void givePlayer(Player player, Spawner spawner) {
        ItemStack spawnerItem = new ItemBuilder().setMaterial(Material.MOB_SPAWNER).setAmount(1)
                .setName(spawner.getName() + " Spawner").addLore(spawner.getSpawnerType().toString()).getItem();
        player.getInventory().addItem(spawnerItem);
    }

    private boolean drops() {
        int compareTo = getRandom().nextInt(10000);
        return getChance() > compareTo;
    }

    private Spawner getRandomDrop() {
        return getSpawnersToDrop().get(getRandom().nextInt(getSpawnersToDrop().size()));
    }

}
