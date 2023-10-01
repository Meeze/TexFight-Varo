package de.realmeze.impl.gui.controller;

import de.realmeze.impl.gui.model.ClickableSlot;
import de.realmeze.impl.gui.model.ClickableInventory;
import de.realmeze.impl.item.service.ItemService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

@Getter
@RequiredArgsConstructor
public class GuiController {

    private ItemStack placeholder = new ItemStack(Material.STAINED_GLASS_PANE, 1);
    private String punishmentHistoryKey = "punishment-history";
    private String gamemodeKey = "gamemode-switcher";
    private String shopKey = "shop";
    private HashMap<Player, Inventory> punishmentHistoryInventoryCache = new HashMap<>();
    private HashMap<Player, Inventory> gamemodeSwitcherInventoryCache = new HashMap<>();
    private HashMap<Player, Inventory> shopInventoryCache = new HashMap<>();

    private final HashMap<String, ClickableInventory> inventories;
    private final String guiIdentifier;

    private final ItemService itemService;

    public void registerInventories() {
        registerPunishmentHistory();
        registerGamemodeSwitcher();
        registerShop();
    }

    public void registerPunishmentHistory() {
        String title = "Punishment History";
        int size = 54;
        ItemStack[] content = new ItemStack[54];
        ClickableSlot[] clickActions = new ClickableSlot[0];
        for (int i = 0; i < size; i++) {
           //content[i] = getPlaceholder();
        }
        registerGui(getPunishmentHistoryKey(), size, title, content, clickActions);
    }
    public void registerGamemodeSwitcher() {
        String title = "Gamemode Switcher";
        int size = 9;
        ItemStack[] content = new ItemStack[9];
        ItemStack survivalItem = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemStack creativeItem = new ItemStack(Material.GOLDEN_APPLE);
        ItemStack adventureItem = new ItemStack(Material.COMPASS);
        ItemStack spectatorItem = new ItemStack(Material.EYE_OF_ENDER);
        ClickableSlot survivalSlot = new ClickableSlot(1, "gamemode 0", survivalItem);
        ClickableSlot creativeSlot = new ClickableSlot(3, "gamemode 1", creativeItem);
        ClickableSlot adventureSlot = new ClickableSlot(5, "gamemode 2", adventureItem);
        ClickableSlot spectatorSlot = new ClickableSlot(7, "gamemode 3", spectatorItem);
        ClickableSlot[] clickActions = new ClickableSlot[]{survivalSlot, adventureSlot, creativeSlot, spectatorSlot};
        for (int i = 0; i < size; i++) {
           content[i] = getPlaceholder();
        }
        content[survivalSlot.getSlot()] = survivalSlot.getClickedItem();
        content[adventureSlot.getSlot()] = adventureSlot.getClickedItem();
        content[creativeSlot.getSlot()] = creativeSlot.getClickedItem();
        content[spectatorSlot.getSlot()] = spectatorSlot.getClickedItem();
        registerGui(getGamemodeKey(), size, title, content, clickActions);
    }
    public void registerShop() {
        String title = "Shop";
        int size = 9;
        ItemStack[] content = new ItemStack[9];
        ItemStack echest = itemService.getShopItem(Material.ENDER_CHEST).getItem();
        ItemStack melon = itemService.getShopItem(Material.MELON_SEEDS).getItem();
        ItemStack potato = itemService.getShopItem(Material.POTATO_ITEM).getItem();
        ItemStack slime = itemService.getShopItem(Material.SLIME_BALL).getItem();
        ItemStack carrot = itemService.getShopItem(Material.CARROT_ITEM).getItem();
        ItemStack blaze = itemService.getShopItem(Material.BLAZE_ROD).getItem();
        ItemStack sugarcane = itemService.getShopItem(Material.SUGAR_CANE).getItem();
        ClickableSlot buyEchest = new ClickableSlot(1, "shop buy ENDER_CHEST", echest);
        ClickableSlot buyMelon = new ClickableSlot(2, "shop buy MELON_SEEDS", melon);
        ClickableSlot buyPotato = new ClickableSlot(3, "shop buy POTATO_ITEM", potato);
        ClickableSlot buySlime = new ClickableSlot(4, "shop buy SLIME_BALL", slime);
        ClickableSlot buyCarrot = new ClickableSlot(5, "shop buy CARROT_ITEM", carrot);
        ClickableSlot buyBlaze = new ClickableSlot(6, "shop buy BLAZE_ROD", blaze);
        ClickableSlot buySugarcane = new ClickableSlot(7, "shop buy SUGAR_CANE", sugarcane);
        ClickableSlot[] clickActions = new ClickableSlot[]{buyEchest, buyMelon, buyPotato, buySlime, buyCarrot, buyBlaze, buySugarcane};
        for (int i = 0; i < size; i++) {
            content[i] = getPlaceholder();
        }
        content[buyEchest.getSlot()] = buyEchest.getClickedItem();
        content[buyMelon.getSlot()] = buyMelon.getClickedItem();
        content[buyPotato.getSlot()] = buyPotato.getClickedItem();
        content[buySlime.getSlot()] = buySlime.getClickedItem();
        content[buyCarrot.getSlot()] = buyCarrot.getClickedItem();
        content[buyBlaze.getSlot()] = buyBlaze.getClickedItem();
        content[buySugarcane.getSlot()] = buySugarcane.getClickedItem();

        registerGui(getShopKey(), size, title, content, clickActions);
    }

    public ClickableInventory getInventory(String inventoryKey) {
        return inventories.get(inventoryKey);
    }

    public void registerGui(String key, int size, String title, ItemStack[] content, ClickableSlot[] clickableSlots) {
        ClickableInventory clickableInventory = new ClickableInventory(content, size, title, clickableSlots);
        getInventories().put(key, clickableInventory);
    }

    public void openGui(String invKey, Player player, boolean useCache) {
        player.openInventory(createInventoryView(invKey, player, useCache));
    }

    public void openGui(Inventory inventory, Player player) {
        player.openInventory(inventory);
    }

    public Inventory createInventoryView(String invKey, Player player, boolean useCache) {
        Inventory view = null;
        if (useCache) {
            view = getViewFromCache(invKey, player);
        }
        if (view == null) {
            ClickableInventory clickableInventory = getInventory(invKey);
            view = Bukkit.createInventory(player, clickableInventory.getSize(), clickableInventory.getTitle() + getGuiIdentifier());
            view.setContents(clickableInventory.getInventoryContent());
            cacheInventoryView(player, view, invKey);
        }
        return view;
    }

    private Inventory getViewFromCache(String invKey, Player player) {
        return getCacheFor(invKey).get(player);
    }

    private void cacheInventoryView(Player player, Inventory inventory, String invKey) {
        HashMap<Player, Inventory> someCache = getCacheFor(invKey);
        if (null != someCache) {
            someCache.put(player, inventory);
        } else {
            Bukkit.broadcastMessage("Error when putting inv in cache, wrong key? ask McMeze if youre using this and are not him lul");
        }
    }

    private HashMap<Player, Inventory> getCacheFor(String invKey) {
        if (invKey.equalsIgnoreCase(getPunishmentHistoryKey())) {
            return getPunishmentHistoryInventoryCache();
        } else if(invKey.equalsIgnoreCase(getGamemodeKey())) {
            return getGamemodeSwitcherInventoryCache();
        } else if(invKey.equalsIgnoreCase(getShopKey())) {
            return getShopInventoryCache();
        }
        return null;
    }

    /**
     * dont know if this is needed in this plugin maybe yes
     *
     * @param player
     * @param invKey
     * @param newContent
     */
    public void updateContentInCache(Player player, String invKey, ItemStack[] newContent) {
        getCacheFor(invKey).get(player).setContents(newContent);
    }

}
