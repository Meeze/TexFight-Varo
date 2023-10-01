package de.realmeze.command.inventory;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import de.realmeze.impl.base.TexCommand;
import de.realmeze.impl.player.controller.TexPlayerController;
import de.realmeze.impl.player.model.TexBackpack;
import de.realmeze.impl.player.model.TexPlayer;
import de.realmeze.impl.player.model.TexVipChest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@CommandAlias("safe|vipchest")
public class SafeCommand extends TexCommand {

    private TexPlayerController texPlayerController;

    public SafeCommand(TexPlayerController texPlayerController) {
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
        TexVipChest texVipChest = texPlayer.getTexVipChest();
        Inventory backpackView = Bukkit.createInventory(player, texVipChest.getChestSize(), texVipChest.getChestTitle());
        backpackView.setContents(texVipChest.getChestContent());
        player.openInventory(backpackView);
    }
}
