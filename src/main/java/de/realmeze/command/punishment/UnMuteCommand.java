package de.realmeze.command.punishment;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import de.realmeze.impl.base.TexCommand;
import de.realmeze.impl.punishment.controller.PunishmentController;
import de.realmeze.impl.punishment.model.PunishmentResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("unmute")
@Getter
@Setter
@AllArgsConstructor
public class UnMuteCommand extends TexCommand {

    private PunishmentController punishmentController;

    @Default
    @CommandCompletion("@players")
    public void onUnMute(Player player, String[] args) {
        if (args.length < 1) {
            sendTo(player, "/unmute <Player>", true);
            return;
        }
        if (isHimself(player, args[0])) {
            sendNotYourself(player);
            return;
        }
        Player receiver = Bukkit.getPlayer(args[0]);
        PunishmentResult result = getPunishmentController().unMute(receiver.getUniqueId());
        if(result == PunishmentResult.REMOVE_PUNISHMENT) {
            sendTo(player, "Du hast " + receiver.getName() + " entmuted!", true);
            sendAll(receiver.getName() + " wurde entmuted!", true);
        } else if(result == PunishmentResult.NOT_PUNISHED) {
            sendTo(player, "Der Spieler " + receiver.getName() + " ist nicht gemuted!", true);
        }
    }
}