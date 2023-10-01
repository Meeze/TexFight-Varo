package de.realmeze.command.inventory;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import de.realmeze.impl.base.TexCommand;
import de.realmeze.impl.player.controller.TexPlayerController;
import de.realmeze.impl.player.model.TexBackpack;
import de.realmeze.impl.player.model.TexPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@CommandAlias("backpack|rucksack|bp")
public class BackpackCommand extends TexCommand {

    private TexPlayerController texPlayerController;

    public BackpackCommand(TexPlayerController texPlayerController) {
        setTexPlayerController(texPlayerController);
    }

    public TexPlayerController getTexPlayerController() {
        return texPlayerController;
    }

    public void setTexPlayerController(TexPlayerController texPlayerController) {
        this.texPlayerController = texPlayerController;
    }

    @Default
    public void onBackpack(Player player){
        TexPlayer texPlayer = getTexPlayerController().getPlayer(player);
        TexBackpack texBackpack = texPlayer.getBackpack();
        Inventory backpackView = Bukkit.createInventory(player, texBackpack.getBackpackSize(), texBackpack.getBackpackTitle());
        backpackView.setContents(texBackpack.getBackpackContent());
        player.openInventory(backpackView);
    }
}
