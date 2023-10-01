package de.realmeze.impl.base;

import co.aikar.commands.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class TexCommand extends BaseCommand {

    private String prefix = ChatColor.RED + "TexFight> " + ChatColor.GRAY;

    public void sendTo(Player player, String message, boolean withPrefix) {
        if(withPrefix) {
            message = prefix + message;
        }
        sendToPlayer(player, message);
    }

    public void sendAll(String message, boolean withPrefix) {
        if(withPrefix) {
            message = prefix + message;
        }
        sendToAllPlayers(message);
    }

    private void sendToAllPlayers(String message) {
        Bukkit.broadcastMessage(message);
    }

    private void sendToPlayer(Player player, String message) {
        player.sendMessage(message);
    }

    protected void sendInvalidItem(Player player) {
        sendTo(player, "Das Item in deiner Hand darf keine Luft sein!", true);
    }

    protected void sendInvalidNumber(Player player, String validRange) {
        sendTo(player, "Bitte gib eine Zahl von " +  validRange +  " ein!", true);
    }

    protected void sendInvalidTimeUnit(Player player) {
        sendTo(player, "Bitte gib eine gültige Zahl und eine Time Unit <w/d/h/m> an, bsp: 1w | 20d | 15m !", true);
    }

    protected void sendPlayerNotFound(Player player, String target) {
        sendTo(player, "Der Spieler " + target + " ist nicht online!", true);
    }

    protected void sendTexPlayerNotRegistered(Player player) {
        sendTo(player, "this shouldnt happen, texplayer not registered", true);
    }

    protected void sendNotYourself(Player player) {
        sendTo(player, "Du kannst diesen Befehl nicht auf dich selbst benutzen!", true);
    }

    /**
     *
     * @param targetName
     * @return true if player is online
     */
    protected boolean isOnlineByName(String targetName) {
        AtomicBoolean playerExists = new AtomicBoolean(false);
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            if (onlinePlayer.getName().equalsIgnoreCase(targetName)) {
                playerExists.set(true);
            }
        });
        return playerExists.get();
    }

    protected boolean isValidStackSize(int amount) {
        boolean isValid = false;
        if(0 < amount && amount < 65){
            isValid = true;
        }
        return isValid;
    }

    protected boolean isValidTarget(Player player, String targetName) {
        if(!isOnlineByName(targetName)){
            sendPlayerNotFound(player, targetName);
            return false;
        }
        if(isHimself(player, targetName)) {
            sendNotYourself(player);
            return false;
        }
        return true;
    }

    protected boolean isHimself(Player player, String playerName) {
        return player.getName().equalsIgnoreCase(playerName);
    }

    @Deprecated
    protected String buildMessageFromArguments(String[] args, boolean skipFirst) {
        String builderOutput;
        if (args.length == 1) {
            builderOutput = args[0];
        } else {
            StringBuilder builder = new StringBuilder();
            int startingFrom = skipFirst ? 1 : 0;
            for (int i = startingFrom; i < args.length; i++) {
                builder.append(args[i]);
                if (i != args.length - 1) {
                    builder.append(" ");
                }
            }
            builderOutput = builder.toString();
        }
        return builderOutput;
    }
    protected String buildMessageFromArguments(String[] args, int skipAmount) {
        String builderOutput;
        if (args.length == 1) {
            builderOutput = args[0];
        } else {
            StringBuilder builder = new StringBuilder();
            for (int i = skipAmount; i < args.length; i++) {
                builder.append(args[i]);
                if (i != args.length - 1) {
                    builder.append(" ");
                }
            }
            builderOutput = builder.toString();
        }
        return builderOutput;
    }

    protected Instant parsePunishTime(String timeToAdd) {
        if (timeToAdd.equalsIgnoreCase("perma")) {
            return Instant.MIN;
        } else {
            String timeWithoutUnit = timeToAdd.substring(0, timeToAdd.length() - 1);
            if (!timeWithoutUnit.matches("\\d+")) {
                return null;
            }
            // last char is unit
            switch (timeToAdd.charAt(timeToAdd.length() - 1)) {
                case 'w':
                    return Instant.now().plus(Integer.parseInt(timeWithoutUnit) * 7, ChronoUnit.DAYS);
                case 'd':
                    return Instant.now().plus(Integer.parseInt(timeWithoutUnit), ChronoUnit.DAYS);
                case 'h':
                    return Instant.now().plus(Integer.parseInt(timeWithoutUnit), ChronoUnit.HOURS);
                case 'm':
                    return Instant.now().plus(Integer.parseInt(timeWithoutUnit), ChronoUnit.MINUTES);
                default:
                    return null;
            }
        }
    }

}
