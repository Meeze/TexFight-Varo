package de.realmeze.command.inventory;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import de.realmeze.impl.base.TexCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("inventorysee|invsee")
public class InventorySeeCommand extends TexCommand {

    @Default
    @CommandCompletion("@players")
    public void onInventorySee(Player player, String[] args) {
        if (args.length != 1) {
            sendTo(player, "/invsee <Player>", true);
            return;
        }
        if (isValidTarget(player, args[0])) {
            Player target = Bukkit.getPlayer(args[0]);
            player.openInventory(target.getInventory());
        }
    }
}
