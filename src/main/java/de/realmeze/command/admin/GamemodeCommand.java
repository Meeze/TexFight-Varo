package de.realmeze.command.admin;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import de.realmeze.impl.base.TexCommand;
import de.realmeze.impl.gui.controller.GuiController;
import de.realmeze.impl.punishment.model.Punishment;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.time.Instant;

@CommandAlias("gm|gamemode")
public class GamemodeCommand extends TexCommand {

    private GuiController guiController;

    public GamemodeCommand(GuiController guiController) {
        this.guiController = guiController;
    }

    public GuiController getGuiController() {
        return guiController;
    }

    public void setGuiController(GuiController guiController) {
        this.guiController = guiController;
    }

    @Default
    @CommandCompletion("@range:0-3 @players")
    public void onGamemode(Player player, String[] args) {
        if (args.length == 0) {
            getGuiController().openGui(getGuiController().getGamemodeKey(), player, true);
        }
        if (args.length == 1) {
            String gamemodeToSet = args[0];
            switch (gamemodeToSet) {
                case "0":
                    player.setGameMode(GameMode.SURVIVAL);
                    sendTo(player, "Gamemode set to " + gamemodeToSet, true);
                    return;
                case "1":
                    player.setGameMode(GameMode.CREATIVE);
                    sendTo(player, "Gamemode set to " + gamemodeToSet, true);
                    return;
                case "2":
                    player.setGameMode(GameMode.ADVENTURE);
                    sendTo(player, "Gamemode set to " + gamemodeToSet, true);
                    return;
                case "3":
                    player.setGameMode(GameMode.SPECTATOR);
                    sendTo(player, "Gamemode set to " + gamemodeToSet, true);
                    return;
                default:
                    sendTo(player, "Gamemode not found: " + gamemodeToSet, true);
            }
        }

    }

}
