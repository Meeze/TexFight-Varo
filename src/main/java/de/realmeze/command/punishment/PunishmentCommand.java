package de.realmeze.command.punishment;

import co.aikar.commands.annotation.*;
import de.realmeze.impl.base.TexCommand;
import de.realmeze.impl.gui.controller.GuiController;
import de.realmeze.impl.item.service.ItemService;
import de.realmeze.impl.punishment.controller.PunishmentController;
import de.realmeze.impl.punishment.model.PunishmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

@CommandAlias("punishment|punishments|punish|banhistory|kickhistory|mutehistory")
@Getter
@Setter
@AllArgsConstructor
public class PunishmentCommand extends TexCommand {

    private PunishmentController punishmentController;
    private GuiController guiController;
    private ItemService itemService;

    @Subcommand("history")
    @CommandCompletion("@punishment-types")
    public void onPunishmentHistory(Player player, @Single @Default("all") String type) {
        if (isInvalidType(type)) {
            onUnknownCommand(player);
            return;
        }
        sendTo(player, "Alle Punishment mit type: " + type, true);
        getPunishmentController().showHistoryInChat(player, type);
    }

    @Subcommand("gui")
    public void onPunishmentGui(Player player) {
        Inventory punishmentGui = getGuiController().createInventoryView(getGuiController().getPunishmentHistoryKey(), player, false);
        getPunishmentController().getPunishments().values().forEach(punishments -> punishments.forEach(punishment ->  {
            punishmentGui.addItem(getItemService().buildItemForPunishment(punishment));
        }));
        getGuiController().openGui(punishmentGui, player);
    }

    @CatchUnknown
    public void onUnknownCommand(Player player) {
        sendTo(player, "/punishment <gui|history> <all|kick|mute|ban>", true);
    }

    private boolean isInvalidType(String type) {
        if (Arrays.toString(PunishmentType.values()).toUpperCase().contains(type.toUpperCase()) || type.equalsIgnoreCase("all")) {
            return false;
        } else {
            return true;
        }
    }
}
