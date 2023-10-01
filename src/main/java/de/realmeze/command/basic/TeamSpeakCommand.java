package de.realmeze.command.basic;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import de.realmeze.impl.base.TexCommand;
import org.bukkit.entity.Player;

@CommandAlias("teamspeak|ts3|ts")
public class TeamSpeakCommand extends TexCommand {

    @Default
    public void onTeamspeakCommand(Player player){
        sendTo(player, "Unser Teamspeak: ts.texfight.net", true);
    }
}
