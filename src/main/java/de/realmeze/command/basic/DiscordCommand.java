package de.realmeze.command.basic;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import de.realmeze.impl.base.TexCommand;
import org.bukkit.entity.Player;

@CommandAlias("discord|dc")
public class DiscordCommand extends TexCommand {

    @Default
    public void onDiscord(Player player) {
       sendTo(player, "Unser Discord: https://discord.gg/dReCaQK", true);
    }

}
