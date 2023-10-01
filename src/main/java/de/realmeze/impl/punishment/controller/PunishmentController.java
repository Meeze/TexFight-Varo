package de.realmeze.impl.punishment.controller;

import de.realmeze.impl.base.TexController;
import de.realmeze.impl.punishment.model.Punishment;
import de.realmeze.impl.punishment.model.PunishmentResult;
import de.realmeze.impl.punishment.model.PunishmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.*;

@AllArgsConstructor
@Getter
@Setter
public class PunishmentController extends TexController {


    private HashMap<UUID, ArrayList<Punishment>> punishments;
    private BanList bans;

    private void addOrInit(UUID affectedPlayer, Punishment punishment) {
        if (getPunishments().containsKey(affectedPlayer)) {
            ArrayList<Punishment> existingPunishments = getPunishments().get(affectedPlayer);
            existingPunishments.add(punishment);
            getPunishments().put(affectedPlayer, existingPunishments);
        } else {
            getPunishments().put(affectedPlayer, new ArrayList<Punishment>() {{
                add(punishment);
            }});
        }
    }

    public Optional<Punishment> getPunishment(UUID playerId, PunishmentType punishmentType) {
        ArrayList<Punishment> punishments = getPunishments().getOrDefault(playerId, new ArrayList<>());
        return punishments.stream().filter(p -> p.getPunishmentType().equals(punishmentType)).findFirst();
    }

    private PunishmentResult addToPunishments(UUID affectedPlayer, Punishment punishment) {
        Optional<Punishment> potentialExistingPunishment = getPunishment(affectedPlayer, punishment.getPunishmentType());
        if (potentialExistingPunishment.isPresent()) {
            return PunishmentResult.ALREADY_PUNISHED;
        }
        addOrInit(affectedPlayer, punishment);
        return PunishmentResult.SUCCESS;
    }
    private PunishmentResult removeFromPunishments(UUID affectedPlayer, PunishmentType punishmentType) {
        Optional<Punishment> potentialExistingPunishment = getPunishment(affectedPlayer, punishmentType);
        if (potentialExistingPunishment.isPresent()) {
            getPunishments().get(affectedPlayer).remove(potentialExistingPunishment.get());
            return PunishmentResult.REMOVE_PUNISHMENT;
        }
        return PunishmentResult.NOT_PUNISHED;
    }


    private Punishment buildPunishment(UUID issuer, UUID receiver, String reason, Instant expiresAt, PunishmentType punishmentType) {
        Instant issuedAt = Instant.now();
        Punishment punishment = new Punishment();
        punishment.setPunishmentIssuer(issuer);
        punishment.setPunishmentReceiver(receiver);
        punishment.setReason(reason);
        punishment.setIssuedAt(issuedAt);
        punishment.setExpiresAt(expiresAt);
        punishment.setPunishmentType(punishmentType);
        return punishment;
    }

    public Punishment buildKick(Player issuer, Player receiver, String reason) {
        Punishment kickPunishment = buildPunishment(getUUIDFromPlayer(issuer), getUUIDFromPlayer(receiver), reason, null, PunishmentType.KICK);
        return kickPunishment;
    }

    public Punishment buildMute(Player issuer, Player receiver, String reason, Instant expiresAt) {
        Punishment mutePunishment = buildPunishment(getUUIDFromPlayer(issuer), getUUIDFromPlayer(receiver), reason, expiresAt, PunishmentType.MUTE);
        return mutePunishment;
    }

    public Punishment buildBan(Player issuer, OfflinePlayer receiver, String reason, Instant expiresAt) {
        Punishment banPunishment = buildPunishment(getUUIDFromPlayer(issuer), getUUIDFromOfflinePlayer(receiver), reason, expiresAt, PunishmentType.BAN);
        return banPunishment;
    }

    private PunishmentResult doBan(Punishment punishment) {
        OfflinePlayer offTarget = Bukkit.getOfflinePlayer(punishment.getPunishmentReceiver());
        PunishmentResult punishmentResult =  addToPunishments(punishment.getPunishmentReceiver(), punishment);
        if (punishmentResult == PunishmentResult.SUCCESS) {
            if (offTarget.isOnline()) {
                Player target = (Player) offTarget;
                target.kickPlayer(punishment.getReason());
            }
            getBans().addBan(offTarget.getName(), punishment.getReason(), getExpiresAtForBan(punishment.getExpiresAt()), punishment.getPunishmentIssuer().toString());
        }
        return punishmentResult;
    }

    private PunishmentResult doKick(Punishment punishment) {
        //dont add to punishments as kicks arent persistent
        Player player = Bukkit.getPlayer(punishment.getPunishmentReceiver());
        player.kickPlayer(punishment.getReason());
        return PunishmentResult.SUCCESS;
    }

    private PunishmentResult doMute(Punishment punishment) {
        return addToPunishments(punishment.getPunishmentReceiver(), punishment);
    }

    /**
     * @param punishment from builder
     * @return result of action, handle in command accordingly
     */
    public PunishmentResult executePunishment(Punishment punishment) {
        if (canBypassPunishment(punishment.getPunishmentReceiver())) {
            return PunishmentResult.PERMISSION_DENIED;
        }
        PunishmentType type = punishment.getPunishmentType();
        if (type.equals(PunishmentType.BAN)) {
            return doBan(punishment);
        } else if (type.equals(PunishmentType.KICK)) {
            return doKick(punishment);
        } else if (type.equals(PunishmentType.MUTE)) {
            return doMute(punishment);
        }
        return PunishmentResult.UNKNOWN_ERROR;
    }

    /**
     *
     * @param receiverId
     * @return true if target has bypass perms
     **/
    private boolean canBypassPunishment(UUID receiverId) {
        OfflinePlayer offTarget = Bukkit.getOfflinePlayer(receiverId);
        if (offTarget.isOnline()) {
            Player target = (Player) offTarget;
            return target.hasPermission("texfight.punishment.bypass");
        } else {
            // TODO implement check with luckperms if it supports offpermissions(should)
            return false;
        }
    }

    private Date getExpiresAtForBan(Instant expiresAt) {
        if (Instant.MIN == expiresAt) {
            return null;
        } else {
            return Date.from(expiresAt);
        }
    }

    private UUID getUUIDFromPlayer(Player player) {
        return player.getUniqueId();
    }

    private UUID getUUIDFromOfflinePlayer(OfflinePlayer offlinePlayer) {
        return offlinePlayer.getUniqueId();
    }

    public String makeHistoryMessage(Punishment punishment) {
        String historyMessage = "Der Spieler " + punishment.getPunishmentReceiver()
                + " wurde am " + punishment.getIssuedAt() + " bestraft von " + punishment.getPunishmentIssuer()
                + " | Grund: " + punishment.getReason() + " Art der Strafe: " + punishment.getPunishmentType();
        return historyMessage;
    }

    public void showHistoryInChat(Player player, String displayingPunishmentType) {
        getPunishments().values().forEach(punishments -> punishments.forEach(punishment ->  {
            if(punishment.getPunishmentType().getName().equalsIgnoreCase(displayingPunishmentType) || displayingPunishmentType.equalsIgnoreCase("all")){
                player.sendMessage(makeHistoryMessage(punishment));
            }
        }));
    }

    public void loadBans() {
        Set<BanEntry> bansToLoad = getBans().getBanEntries();
        bansToLoad.forEach(banEntry ->  {
            UUID punishmentReceiver = Bukkit.getOfflinePlayer(banEntry.getTarget()).getUniqueId();
            Punishment loadedBan = buildBanFromEntry(banEntry, punishmentReceiver);
            addToPunishments(punishmentReceiver, loadedBan);
        });
    }

    private Punishment buildBanFromEntry(BanEntry banEntry, UUID punishmentReceiver) {
        String reason = banEntry.getReason();
        UUID punishmentIssuer = UUID.fromString(banEntry.getSource());
        Punishment ban = new Punishment();
        Instant issuedAt = banEntry.getCreated().toInstant();
        Instant expiresAt = banEntry.getExpiration().toInstant();
        ban.setReason(reason);
        ban.setPunishmentReceiver(punishmentReceiver);
        ban.setPunishmentIssuer(punishmentIssuer);
        ban.setIssuedAt(issuedAt);
        ban.setExpiresAt(expiresAt);
        ban.setPunishmentType(PunishmentType.BAN);
        return ban;
    }

    private PunishmentResult removePunishment(PunishmentType punishmentType, UUID receiver) {
        PunishmentResult result = removeFromPunishments(receiver, punishmentType);
        if(punishmentType == PunishmentType.BAN && result == PunishmentResult.REMOVE_PUNISHMENT) {
            getBans().pardon(Bukkit.getOfflinePlayer(receiver).getName());
        }
        return result;
    }

    public PunishmentResult unBan(UUID receiver) {
        return removePunishment(PunishmentType.BAN, receiver);
    }
    public PunishmentResult unMute(UUID receiver) {
        return removePunishment(PunishmentType.MUTE, receiver);
    }

}
