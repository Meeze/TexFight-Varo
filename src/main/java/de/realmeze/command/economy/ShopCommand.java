package de.realmeze.command.economy;

import co.aikar.commands.annotation.CatchUnknown;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import de.realmeze.impl.base.TexCommand;
import de.realmeze.impl.gui.controller.GuiController;
import de.realmeze.impl.player.controller.TexPlayerController;
import de.realmeze.impl.shop.controller.ShopController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@CommandAlias("shop")
@Getter
@AllArgsConstructor
public class ShopCommand extends TexCommand {

    private ShopController shopController;
    private TexPlayerController texPlayerController;
    private GuiController guiController;

    @Default
    @CatchUnknown
    public void onShop(Player player){
       getGuiController().openGui("shop", player, true);
    }

    @Subcommand("buy")
    public void onShopBuy(Player player, String what){
        Material material = Material.getMaterial(what);
        sendTo(player, "trying to buy " + material, true);
        if(material == null){
            return;
        }
        if(getShopController().buy(material, getTexPlayerController().getPlayer(player))){
            sendTo(player, "Du hast gekauft: " + material, true);
        }
    }

}
