package de.realmeze.impl.item.service;

import de.realmeze.impl.item.model.ItemBuilder;
import de.realmeze.impl.punishment.model.Punishment;
import de.realmeze.impl.punishment.model.PunishmentType;
import de.realmeze.impl.shop.model.ShopItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * used to build itemstack representation from models for GUIs
 */
public class ItemService {

    public ItemService() {
    }

    public ShopItem getShopItem(Material material) {
        if (material == Material.ENDER_CHEST) {
            ShopItem echest = buildShopItem("Enderchest", Material.ENDER_CHEST, 1);
            return echest;
        } else if (material == Material.MELON_SEEDS) {
            ShopItem melon = buildShopItem("Melonensamen", Material.MELON_SEEDS, 1);
            return melon;
        } else if (material == Material.POTATO_ITEM) {
            ShopItem potato = buildShopItem("Kartoffel", Material.POTATO_ITEM, 1);
            return potato;
        } else if (material == Material.SLIME_BALL) {
            ShopItem slime = buildShopItem("Slimeball", Material.SLIME_BALL, 1);
            return slime;
        } else if (material == Material.CARROT_ITEM) {
            ShopItem carrot = buildShopItem("Karrotten", Material.CARROT_ITEM, 1);
            return carrot;
        } else if (material == Material.BLAZE_ROD) {
            ShopItem blaze = buildShopItem("Lohenruten", Material.BLAZE_ROD, 1);
            return blaze;
        } else if (material == Material.SUGAR_CANE) {
            ShopItem sugarcane = buildShopItem("Zuckerrohr", Material.SUGAR_CANE, 1);
            return sugarcane;
        }
        return null;
    }

    private ShopItem buildShopItem(String name, Material material, long cost) {
        ItemBuilder itemBuilder = new ItemBuilder();
        itemBuilder.setMaterial(material).setName(name).setAmount(1).addLore("Klicke um zu kaufen!");
        ShopItem shopItem = new ShopItem(itemBuilder.getItem(), cost);
        return shopItem;
    }

    public ItemStack buildItemForPunishment(Punishment punishment) {
        ItemBuilder itemBuilder = new ItemBuilder();
        if (punishment.getPunishmentType() == PunishmentType.KICK) {
            itemBuilder.setMaterial(Material.SKULL_ITEM);
            itemBuilder.setName(ChatColor.GRAY + "TYPE: " + punishment.getPunishmentType().toString());
            String receiver = Bukkit.getOfflinePlayer(punishment.getPunishmentReceiver()).getName();
            String issuer = Bukkit.getOfflinePlayer(punishment.getPunishmentIssuer()).getName();
            itemBuilder.addLore("Gekickter Spieler: " + receiver);
            itemBuilder.addLore("Von: " + issuer);
            itemBuilder.addLore("Grund: " + punishment.getReason());
            itemBuilder.addLore("Wann: " + punishment.getIssuedAt());
        } else if (punishment.getPunishmentType() == PunishmentType.BAN) {
            itemBuilder.setMaterial(Material.SKULL_ITEM);
            itemBuilder.setName(ChatColor.RED + "TYPE: " + punishment.getPunishmentType().toString());
            String receiver = Bukkit.getOfflinePlayer(punishment.getPunishmentReceiver()).getName();
            String issuer = Bukkit.getOfflinePlayer(punishment.getPunishmentIssuer()).getName();
            itemBuilder.addLore("Gebannter Spieler: " + receiver);
            itemBuilder.addLore("Von: " + issuer);
            itemBuilder.addLore("Grund: " + punishment.getReason());
            itemBuilder.addLore("Wann: " + punishment.getIssuedAt());
            itemBuilder.addLore("Bis: " + punishment.getExpiresAt());
        }
        return itemBuilder.getItem();
    }

}
