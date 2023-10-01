package de.realmeze.command.event;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import de.realmeze.impl.base.TexCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandAlias("giveall")
public class GiveAllCommand extends TexCommand {

    @Default
    public void onGiveAll(Player player, @Optional Integer amount) {

        ItemStack toGiveAll = player.getItemInHand().clone();
        if (toGiveAll == null || toGiveAll.getType() == Material.AIR) {
            sendInvalidItem(player);
            return;
        }
        if (amount != null) {
            if (isValidStackSize(amount)) {
                toGiveAll.setAmount(amount);
            } else {
                sendInvalidNumber(player, "1-64");
                return;
            }
        }
        giveAll(player, toGiveAll);
    }

    private void giveAll(Player from, ItemStack toGiveAll) {
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            ItemStack toGivePlayer = toGiveAll.clone();
            if (onlinePlayer != from) {
                sendTo(onlinePlayer, "Dir wurde " + toGivePlayer.getAmount() + "x " + toGivePlayer.getType() + " gegeben", true);
                onlinePlayer.getInventory().addItem(toGivePlayer);
            }
        });
    }

}
