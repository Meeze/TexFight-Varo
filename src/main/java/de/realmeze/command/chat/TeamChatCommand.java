package de.realmeze.command.chat;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import de.realmeze.impl.base.TexCommand;
import de.realmeze.impl.staff.controller.StaffController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@CommandAlias("teamchat|tc|staffchat")
@Getter
@AllArgsConstructor
public class TeamChatCommand extends TexCommand {

    private final StaffController staffController;

    @Default
    @CommandCompletion("message")
    public void onTeamChat(Player player, String[] args) {
        if (args.length < 1) {
            sendTo(player, "/tc <Message>", true);
            return;
        }
        String message = buildMessageFromArguments(args, 0);
        getStaffController().getOnlineStaff().forEach(staffPlayer -> {
         sendTo(staffPlayer, "TC> " + message, false);
        });
    }
}
