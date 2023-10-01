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
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.time.Instant;

@CommandAlias("mute")
@Getter
@Setter
@AllArgsConstructor
public class MuteCommand extends TexCommand {

    private PunishmentController punishmentController;

    @Default
    @CommandCompletion("@players @punishment-time-units grund")
    public void onMute(Player player, String[] args) {
        if (args.length < 3) {
            sendTo(player, "/mute <Player> <perma|w|d|h|m> <Grund...>", true);
            return;
        }
        if (isHimself(player, args[0])) {
            sendNotYourself(player);
            return;
        }
        Player receiver = Bukkit.getPlayer(args[0]);
        String reason = buildMessageFromArguments(args, 2);
        Instant expiresAt = parsePunishTime(args[1]);
        if (null == expiresAt) {
            sendInvalidTimeUnit(player);
            return;
        }
        Punishment mute = getPunishmentController().buildMute(player, receiver, reason, expiresAt);
        PunishmentResult result = getPunishmentController().executePunishment(mute);
        if (result == PunishmentResult.SUCCESS) {
            sendTo(player, "Du hast " + receiver.getName() + " gemuted! Grund: " + mute.getReason(), true);
            sendAll(receiver.getName() + " wurde gebannt! Grund: " + mute.getReason(), true);
        } else if (result == PunishmentResult.PERMISSION_DENIED) {
            player.sendMessage("du kannst " + receiver.getName() + " nicht muten");
        } else if (result == PunishmentResult.ALREADY_PUNISHED) {
            player.sendMessage(receiver.getName() + " ist bereits gemuted!");
        } else {
            Bukkit.broadcastMessage("idk what happened tbh");
        }

    }

}

