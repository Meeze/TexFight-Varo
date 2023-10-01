package de.realmeze.command.chat;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import de.realmeze.impl.base.TexCommand;
import org.bukkit.entity.Player;

@CommandAlias("chatclear|cc|clearchat")
public class ChatClearCommand extends TexCommand {

    @Default
    public void onChatClear(Player player) {
        for (int i = 0; i < 100; i++) {
            sendAll("", false);
        }
    }

}
