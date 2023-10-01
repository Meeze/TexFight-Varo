package de.realmeze.command.chat;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import de.realmeze.impl.base.TexCommand;
import de.realmeze.impl.staff.controller.StaffController;
import de.realmeze.impl.staff.controller.SupportController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("support|helpop|ticket")
@Getter
@Setter
@AllArgsConstructor
public class SupportCommand extends TexCommand {

    private final SupportController supportController;
    private final StaffController staffController;

    @Default
    public void onSupport(Player player) {
        if(getSupportController().needsSupport(player.getName())) {
            sendTo(player, "Du hast bereits eine Support Anfrage gesendet!", true);
            return;
        }
        if(getSupportController().isInSupportChat(player)){
            sendTo(player, "Du bist bereits im Support!", true);
            return;
        }
        getSupportController().requestSupport(player);
        sendTo(player, "Du hast eine Support Anfrage gesendet!", true);
        getStaffController().getOnlineStaff().forEach(staffPlayer -> {
            sendTo(staffPlayer, player.getName() + " hat eine Support Anfrage gesendet!", true);
        });
    }

    @Subcommand("list")
    public void onSupportList(Player player) {
        getSupportController().getSupportRequestMap().values().forEach(supportRequest -> {
            if(getSupportController().needsSupport(supportRequest.getIssuer().getName())){
                sendTo(player, supportRequest.getIssuer().getName() + " braucht support", true);
            } else {
                sendTo(player, supportRequest.getIssuer().getName() + " wird bereits supported", true);
            }
        });
    }

    @Subcommand("accept")
    @CommandCompletion("@players")
    public void onSupportAccept(Player player, String playerNameArg) {
        if (!isValidTarget(player, playerNameArg)) {
            return;
        }
        Player target = Bukkit.getPlayer(playerNameArg);
        if (getSupportController().acceptSupportRequest(target.getName(), player)) {
            sendTo(target, "Deine Support Anfrage wurde von " + player.getName() + " angenommen", true);
            sendTo(player, "Du hast die Support Anfrage von " + target.getName() + " angenommen", true);
        } else {
            sendTo(player, "Dieser Spieler benötigt keinen Support", true);
        }
    }

    @Subcommand("close")
    public void onSupportClose(Player player) {
        if(!getSupportController().isInSupportChat(player)){
            sendTo(player, "Du bist nicht im Support!", true);
            return;
        }
        sendTo(player, "Du hast den Support beendet!", true);
        Player supportPartner = getSupportController().closeSupport(player);
        sendTo(supportPartner, supportPartner.getName() + " hat den Support beendet!", true);
    }


}
