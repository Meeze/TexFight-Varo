package de.realmeze.command.basic;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import de.realmeze.impl.base.TexCommand;
import org.bukkit.entity.Player;

@CommandAlias("vote")
public class VoteCommand extends TexCommand {

    @Default
    public void onVote(Player player) {
        sendTo(player, "Vote für uns: https://minecraft-server.eu/server/index/2132C/TexFightnet", true);
    }

}
