package de.realmeze.impl.shop.controller;

import de.realmeze.impl.base.TexController;
import de.realmeze.impl.item.service.ItemService;
import de.realmeze.impl.item.model.ItemBuilder;
import de.realmeze.impl.player.model.TexPlayer;
import de.realmeze.impl.shop.model.ShopItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

import java.util.HashMap;

@Getter
@AllArgsConstructor
public class ShopController extends TexController {

    private final HashMap<Material, ShopItem> shopItemMap;
    private final ItemService itemService;

    public void registerItems() {
        ShopItem echest = itemService.getShopItem(Material.ENDER_CHEST);
        ShopItem melon = itemService.getShopItem(Material.MELON_SEEDS);
        ShopItem potato = itemService.getShopItem(Material.POTATO_ITEM);
        ShopItem slime = itemService.getShopItem(Material.SLIME_BALL);
        ShopItem carrot = itemService.getShopItem(Material.CARROT_ITEM);
        ShopItem blaze = itemService.getShopItem(Material.BLAZE_ROD);
        ShopItem sugarcane = itemService.getShopItem(Material.SUGAR_CANE);
        getShopItemMap().put(echest.getMaterial(), echest);
        getShopItemMap().put(melon.getMaterial(), melon);
        getShopItemMap().put(potato.getMaterial(), potato);
        getShopItemMap().put(slime.getMaterial(), slime);
        getShopItemMap().put(carrot.getMaterial(), carrot);
        getShopItemMap().put(blaze.getMaterial(), blaze);
        getShopItemMap().put(sugarcane.getMaterial(), sugarcane);
    }

    public boolean buy(Material what, TexPlayer who){
        ShopItem toBuy = getShopItemMap().get(what);
        who.getPlayer().getInventory().addItem(new ItemBuilder().setMaterial(what).setAmount(1).getItem());
        who.getMoney().remove(toBuy.getCost());
        return true;
    }


}
