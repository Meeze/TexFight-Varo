package de.realmeze.command.punishment;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import de.realmeze.impl.base.TexCommand;
import de.realmeze.impl.punishment.controller.PunishmentController;
import de.realmeze.impl.punishment.model.Punishment;
import de.realmeze.impl.punishment.model.PunishmentResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("kick")
@Getter
@Setter
@AllArgsConstructor
public class KickCommand extends TexCommand {

    private PunishmentController punishmentController;

    @Default
    @CommandCompletion("@players grund")
    public void onKick(Player player, String[] args) {
        if (args.length < 2) {
            sendTo(player, "/kick <Player> <Grund...>", true);
            return;
        }
        if (isValidTarget(player, args[0])) {
            Player receiver = Bukkit.getPlayer(args[0]);
            String reason = buildMessageFromArguments(args, 1);
            Punishment kick = getPunishmentController().buildKick(player, receiver, reason);
            PunishmentResult result = getPunishmentController().executePunishment(kick);
            if (result == PunishmentResult.SUCCESS) {
                sendTo(player, "Du hast " + receiver.getName() + " gekickt! Grund: " + kick.getReason(), true);
                sendAll(receiver.getName() + " wurde gekickt! Grund: " + kick.getReason(), true);
            } else if (result == PunishmentResult.PERMISSION_DENIED) {
                player.sendMessage("du kannst " + receiver.getName() + " nicht kicken");
            } else {
                Bukkit.broadcastMessage("idk what happened tbh");
            }
        }
    }
}
