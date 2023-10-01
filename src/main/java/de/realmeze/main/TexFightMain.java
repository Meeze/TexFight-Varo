package de.realmeze.main;

import co.aikar.commands.PaperCommandManager;
import de.realmeze.command.admin.GamemodeCommand;
import de.realmeze.command.basic.DiscordCommand;
import de.realmeze.command.basic.TeamSpeakCommand;
import de.realmeze.command.chat.*;
import de.realmeze.command.economy.MoneyCommand;
import de.realmeze.command.economy.ShopCommand;
import de.realmeze.command.event.GiveAllCommand;
import de.realmeze.command.event.SelectRandomPlayerCommand;
import de.realmeze.command.inventory.BackpackCommand;
import de.realmeze.command.inventory.InventorySeeCommand;
import de.realmeze.command.inventory.SafeCommand;
import de.realmeze.command.player.*;
import de.realmeze.command.punishment.*;
import de.realmeze.command.stats.StatsCommand;
import de.realmeze.impl.chat.controller.GlobalMuteController;
import de.realmeze.impl.chat.controller.ReplyController;
import de.realmeze.impl.chat.model.GlobalMute;
import de.realmeze.impl.cooldown.controller.CoolDownController;
import de.realmeze.impl.gui.controller.GuiController;
import de.realmeze.impl.item.service.ItemService;
import de.realmeze.impl.player.controller.RankingController;
import de.realmeze.impl.player.controller.TexPlayerController;
import de.realmeze.impl.punishment.controller.PunishmentController;
import de.realmeze.impl.savechest.controller.SaveChestController;
import de.realmeze.impl.scoreboard.controller.ScoreboardController;
import de.realmeze.impl.shop.controller.ShopController;
import de.realmeze.impl.spawner.controller.SpawnerController;
import de.realmeze.impl.staff.controller.StaffController;
import de.realmeze.impl.staff.controller.SupportController;
import de.realmeze.impl.teams.controller.TeamController;
import de.realmeze.listener.*;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
public final class TexFightMain extends JavaPlugin {

    private PaperCommandManager manager;
    private GlobalMuteController globalMuteController;
    private ReplyController replyController;
    private TexPlayerController texPlayerController;
    private PunishmentController punishmentController;
    private GuiController guiController;
    private ItemService itemService;
    private StaffController staffController;
    private SupportController supportController;
    private CoolDownController coolDownController;
    private ScoreboardController scoreboardController;
    private ShopController shopController;
    private SaveChestController saveChestController;
    private RankingController rankingController;
    private TeamController teamController;
    private SpawnerController spawnerController;

    @Override
    public void onEnable(){
        registerControllers();
        registerCommandCompletions();
        registerCommands();
        registerListeners();
    }
    @Override
    public void onDisable(){
    }

    private void registerControllers() {

        setManager(new PaperCommandManager(this));
        setItemService(new ItemService());
        setGlobalMuteController(new GlobalMuteController(new GlobalMute(false)));
        setReplyController(new ReplyController(new HashMap<>()));
        setTexPlayerController(new TexPlayerController(this, new HashMap<>(), new HashMap<>()));
        setPunishmentController(new PunishmentController(new HashMap<>(), Bukkit.getBanList(BanList.Type.NAME)));
        setStaffController(new StaffController(new ArrayList<>()));
        setSupportController(new SupportController(new HashMap<>(), new HashMap<>()));
        setCoolDownController(new CoolDownController(new HashMap<>()));
        setScoreboardController(new ScoreboardController(new HashMap<>()));
        setSaveChestController(new SaveChestController(new HashMap<>(), new HashMap<>()));
        setGuiController(new GuiController(new HashMap<>(), " : TexInventory", getItemService()));
        setShopController(new ShopController(new HashMap<>(), getItemService()));
        setRankingController(new RankingController(getTexPlayerController()));
        setTeamController(new TeamController(new HashMap<>(), new HashMap<>()));
        setSpawnerController(new SpawnerController(new ArrayList<>(), new ArrayList<>()));

        // init stuff
        getPunishmentController().loadBans();
        getShopController().registerItems();
        getGuiController().registerInventories();
        getSpawnerController().init();
    }

    private void registerCommands() {
        // Basic
        getManager().registerCommand(new TeamSpeakCommand());
        getManager().registerCommand(new DiscordCommand());

        // Chat
        getManager().registerCommand(new ChatClearCommand());
        getManager().registerCommand(new MessageCommand(getReplyController()));
        getManager().registerCommand(new GlobalMuteCommand(getGlobalMuteController()));
        getManager().registerCommand(new TeamChatCommand(getStaffController()));
        getManager().registerCommand(new SupportCommand(getSupportController(), getStaffController()));

        // Inventory
        getManager().registerCommand(new InventorySeeCommand());
        getManager().registerCommand(new BackpackCommand(getTexPlayerController()));
        getManager().registerCommand(new SafeCommand(getTexPlayerController()));

        // Player (Economy, Stats etc.)
        getManager().registerCommand(new MoneyCommand(getTexPlayerController()));
        getManager().registerCommand(new StatsCommand(getTexPlayerController()));
        getManager().registerCommand(new RankingCommand(getRankingController()));
        getManager().registerCommand(new FeedCommand(getCoolDownController()));
        getManager().registerCommand(new ScoreboardCommand(getScoreboardController(), getTexPlayerController()));
        getManager().registerCommand(new ShopCommand(getShopController(), getTexPlayerController(), getGuiController()));
        getManager().registerCommand(new SaveChestCommand(getSaveChestController()));
        getManager().registerCommand(new TeamCommand(getTeamController(), getTexPlayerController()));

        // Event
        getManager().registerCommand(new GiveAllCommand());
        getManager().registerCommand(new SelectRandomPlayerCommand());

        // Punishment
        getManager().registerCommand(new KickCommand(getPunishmentController()));
        getManager().registerCommand(new BanCommand(getPunishmentController()));
        getManager().registerCommand(new MuteCommand(getPunishmentController()));
        getManager().registerCommand(new PunishmentCommand(getPunishmentController(), getGuiController(), getItemService()));
        getManager().registerCommand(new UnBanCommand(getPunishmentController()));
        getManager().registerCommand(new UnMuteCommand(getPunishmentController()));

        // Admin
        getManager().registerCommand(new GamemodeCommand(getGuiController()));


    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(getGlobalMuteController(), getPunishmentController(), getSupportController()), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(getTexPlayerController(),getStaffController(), getScoreboardController()), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(getTexPlayerController()), this);
        Bukkit.getPluginManager().registerEvents(new PlayerCloseInventoryListener(getTexPlayerController(), getSaveChestController()), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(getGuiController()), this);
        Bukkit.getPluginManager().registerEvents(new InventoryDragListener(getGuiController()), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(getTexPlayerController(), getScoreboardController()), this);
        Bukkit.getPluginManager().registerEvents(new PlayerOpenInventoryListener(getSaveChestController()), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(getSaveChestController(), getSpawnerController()), this);
        Bukkit.getPluginManager().registerEvents(new EntityExplodeListener(getSaveChestController()), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(getSaveChestController()), this);
    }

    private void registerCommandCompletions(){
        getManager().getCommandCompletions().registerStaticCompletion("@punishment-types", new String[]{"kick", "mute", "ban", "all"});
        getManager().getCommandCompletions().registerStaticCompletion("@punishment-time-units", new String[]{"perma","w", "h", "d", "m"});
        getManager().getCommandCompletions().registerStaticCompletion("@chest-types", new String[]{"single","double"});
    }

}
