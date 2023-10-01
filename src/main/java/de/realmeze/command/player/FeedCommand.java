package de.realmeze.command.player;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import de.realmeze.impl.base.TexCommand;
import de.realmeze.impl.cooldown.controller.CoolDownController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("feed|eat")
@Getter
@AllArgsConstructor
public class FeedCommand extends TexCommand {

    private final CoolDownController coolDownController;

    @Default
    @CommandCompletion("@players")
    public void onInventorySee(Player player, String[] args) {
        if (args.length == 0) {
            if(!getCoolDownController().checkFeedCoolDown(player)){
                sendTo(player, "Feed Cooldown!", true);
                return;
            }
            sendTo(player, "Du wurdest gefuettert", true);
            player.setFoodLevel(20);
            player.setSaturation(20);
            getCoolDownController().setFeedOnCooldown(player);
            return;
        }
        if (args.length > 1) {
            sendTo(player, "/feed <Player>", true);
            return;
        }
        if (isValidTarget(player, args[0])) {
            Player target = Bukkit.getPlayer(args[0]);
            sendTo(player, "Du hast " + target.getName() + "gefuettert", true);
            sendTo(target, "Du wurdest gefuettert", true);
            target.setFoodLevel(20);
            target.setSaturation(20);
        }
    }

}
