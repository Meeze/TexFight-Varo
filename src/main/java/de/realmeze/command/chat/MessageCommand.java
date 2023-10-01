package de.realmeze.command.chat;

import co.aikar.commands.annotation.*;
import de.realmeze.impl.base.TexCommand;
import de.realmeze.impl.chat.controller.ReplyController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("message|msg|tell|whisper|w")
@Getter
@Setter
@AllArgsConstructor
public class MessageCommand extends TexCommand {

    private ReplyController replyController;

    @Default
    @CommandCompletion("@players message")
    public void onMessage(Player player) {
        sendTo(player, "/msg <Player> <Message>", true);
    }

    @Subcommand("reply|r")
    @CommandAlias("reply|r")
    public void onMessageReply(Player player, String[] args) {
        Player target = replyController.getLastMessengerMap().get(player);
        if (target != null) {
            sendTo(target, ChatColor.RED + player.getDisplayName() + " -> Dir " + ChatColor.GRAY + buildMessageFromArguments(args, false), true);
            sendTo(player, ChatColor.RED + "Du -> " + target.getDisplayName() + " " + ChatColor.GRAY + buildMessageFromArguments(args, false), true);
        } else {
            sendPlayerNotFound(player, "?");
        }
    }

    @CatchUnknown
    @CommandCompletion("@players message")
    public void onMessageToPlayer(Player player, String[] args) {
        if (isValidTarget(player, args[0])) {
            Player target = Bukkit.getPlayer(args[0]);
            sendTo(target, ChatColor.RED + player.getDisplayName() + " -> Dir " + ChatColor.GRAY + buildMessageFromArguments(args, true), true);
            sendTo(player, ChatColor.RED + "Du -> " + target.getDisplayName() + " " + ChatColor.GRAY + buildMessageFromArguments(args, true), true);
            replyController.addLastMessenger(target, player);
        }
    }
}
