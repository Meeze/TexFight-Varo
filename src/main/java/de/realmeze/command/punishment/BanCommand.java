package de.realmeze.command.punishment;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import de.realmeze.impl.base.TexCommand;
import de.realmeze.impl.punishment.controller.PunishmentController;
import de.realmeze.impl.punishment.model.Punishment;
import de.realmeze.impl.punishment.model.PunishmentResult;
import de.realmeze.impl.punishment.model.PunishmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@CommandAlias("ban|sperre")
@Getter
@Setter
@AllArgsConstructor
public class BanCommand extends TexCommand {

    private PunishmentController punishmentController;

    @Default
    @CommandCompletion("@players @punishment-time-units grund")
    public void onBan(Player player, String[] args) {
        if (args.length < 3) {
            sendTo(player, "/ban <Player> <perma|w|d|h|m> <Grund...>", true);
            return;
        }
        if (isValidTarget(player, args[0])) {
            OfflinePlayer receiver = Bukkit.getOfflinePlayer(args[0]);
            String reason = buildMessageFromArguments(args, 2);
            Instant expiresAt = parsePunishTime(args[1]);
            if (null == expiresAt) {
                sendInvalidTimeUnit(player);
                return;
            }
            Punishment ban = getPunishmentController().buildBan(player, receiver, reason, expiresAt);
            PunishmentResult result = getPunishmentController().executePunishment(ban);
            if (result == PunishmentResult.SUCCESS) {
                sendTo(player, "Du hast " + receiver.getName() + " gebannt! Grund: " + ban.getReason(), true);
                sendAll(receiver.getName() + " wurde gebannt! Grund: " + ban.getReason(), true);
            } else if (result == PunishmentResult.PERMISSION_DENIED) {
                player.sendMessage("du kannst " + receiver.getName() + " nicht bannen");
            } else if (result == PunishmentResult.ALREADY_PUNISHED) {
                player.sendMessage(receiver.getName() + " ist bereits gemuted!");
            } else {
                Bukkit.broadcastMessage("idk what happened tbh");
            }
        }
    }
}
