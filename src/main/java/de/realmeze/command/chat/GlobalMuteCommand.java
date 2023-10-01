package de.realmeze.command.chat;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import de.realmeze.impl.base.TexCommand;
import de.realmeze.impl.chat.controller.GlobalMuteController;
import org.bukkit.entity.Player;

@CommandAlias("globalmute|gm")
public class GlobalMuteCommand extends TexCommand {

    private GlobalMuteController globalMuteController;

    public GlobalMuteCommand(GlobalMuteController globalMuteController) {
        this.globalMuteController = globalMuteController;
    }

    public GlobalMuteController getGlobalMuteController() {
        return globalMuteController;
    }

    @Default
    public void onGlobalMute(Player player) {
        sendTo(player, "Der Globalmute is momentan: " + globalMuteController.getGlobalMute().isEnabled(), true);
    }

    @Subcommand("on|enable|1")
    public void onGlobalMuteOn(Player player) {
        getGlobalMuteController().getGlobalMute().setEnabled(true);
        sendTo(player, "Der Globalmute wurde eingeschaltet", true);
    }

    @Subcommand("off|disable|0")
    public void onGlobalMuteOff(Player player) {
        getGlobalMuteController().getGlobalMute().setEnabled(false);
        sendTo(player, "Der Globalmute wurde ausgeschaltet", true);
    }
}
