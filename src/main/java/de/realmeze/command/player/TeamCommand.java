package de.realmeze.command.player;

import co.aikar.commands.annotation.*;
import de.realmeze.impl.base.TexCommand;
import de.realmeze.impl.player.controller.TexPlayerController;
import de.realmeze.impl.player.model.TexPlayer;
import de.realmeze.impl.teams.controller.TeamController;
import de.realmeze.impl.teams.model.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
@CommandAlias("team|clan")
public class TeamCommand extends TexCommand {

    private final TeamController teamController;
    private final TexPlayerController texPlayerController;

    @Default
    @CatchUnknown
    public void onTeamHelp(Player player) {
        sendTo(player, "/team create name", true);
        sendTo(player, "/team info name", true);
        sendTo(player, "/team kick player", true);
        sendTo(player, "/team invite player", true);
        sendTo(player, "/team leave", true);
    }

    @Subcommand("create")
    public void onTeamCreate(Player player, String teamName) {
        TexPlayer texPlayer = getTexPlayerController().getPlayer(player);
        if (getTeamController().getCurrentTeam(texPlayer) != null) {
            sendTo(player, "Du bist bereits in einem Team!", true);
            return;
        }
        if (getTeamController().createTeam(texPlayer, teamName)) {
            sendTo(player, "Du hast das Team " + teamName + "erstellt", true);
        } else {
            sendTo(player, "Das Team " + teamName + "existiert bereits", true);
        }
    }

    @Subcommand("invite")
    @CommandCompletion("@players")
    public void onTeamInvite(Player player, String playerName) {
        if (isValidTarget(player, playerName)) {
            TexPlayer sender = getTexPlayerController().getPlayer(player);
            if (getTeamController().isValidTeam(sender.getTeam())) {
                if (getTeamController().isPrivileged(sender)) {
                    Player receiverPlayer = Bukkit.getPlayer(playerName);
                    TexPlayer receiver = getTexPlayerController().getPlayer(receiverPlayer);
                    if (getTeamController().inviteToTeam(sender, receiver)) {
                        sendTo(player, "Du hast " + receiverPlayer.getName() + "eingeladen", true);
                        sendTo(receiverPlayer, "Du wurdest von " + player.getName() + "eingeladen", true);
                    } else {
                        sendTo(player, "Der Spieler ist bereits in einem Team", true);
                    }
                } else {
                    sendTo(player, "Du kannst als Member keine Leute einladen", true);
                }
            } else {
                sendTo(player, "Du bist in keinem Team!", true);
            }
        }
    }

    @Subcommand("kick")
    @CommandCompletion("@players")
    public void onTeamKick(Player player, String playerName) {
        if (!isHimself(player, playerName)) {
            TexPlayer sender = getTexPlayerController().getPlayer(player);
            if (getTeamController().isValidTeam(sender.getTeam())) {
                if (getTeamController().isPrivileged(sender)) {
                    if (isOnlineByName(playerName)) {
                        Player receiverPlayer = Bukkit.getPlayer(playerName);
                        TexPlayer receiver = getTexPlayerController().getPlayer(receiverPlayer);
                        if (sender.getTeam() != receiver.getTeam()) {
                            sendTo(player, "not in same team", true);
                            return;
                        }
                        getTeamController().kick(sender, receiver);
                        sendTo(player, "Du hast " + receiverPlayer.getName() + "gekickt", true);
                        sendTo(receiverPlayer, "Du wurdest von " + player.getName() + "gekickt", true);
                    } else {
                        sendTo(player, "not implemented fucker", true);
                    }
                } else {
                    sendTo(player, "Du kannst als Member keine Leute kicken", true);
                }
            } else {
                sendTo(player, "Du bist in keinem Team!", true);
            }
        } else {
            sendNotYourself(player);
        }

    }

    @Subcommand("promote")
    @CommandCompletion("@players")
    public void onTeamPromote(Player player, String playerName) {
        if (isValidTarget(player, playerName)) {
            TexPlayer sender = getTexPlayerController().getPlayer(player);
            if (getTeamController().isValidTeam(sender.getTeam())) {
                if (getTeamController().isPrivileged(sender)) {
                    Player receiverPlayer = Bukkit.getPlayer(playerName);
                    TexPlayer receiver = getTexPlayerController().getPlayer(receiverPlayer);
                    if (sender.getTeam() != receiver.getTeam()) {
                        sendTo(player, "not in same team", true);
                        return;
                    }
                    if (getTeamController().promote(receiver)) {
                        sendTo(player, "Du hast " + receiverPlayer.getName() + "promoted", true);
                        sendTo(receiverPlayer, "Du wurdest von " + player.getName() + "promoted", true);
                    } else {
                        sendTo(player, "Der Spieler " + receiverPlayer.getName() + "ist bereits Admin/Owner!", true);
                    }
                } else {
                    sendTo(player, "Du kannst als Member keine Leute promoten", true);
                }
            } else {
                sendTo(player, "Du bist in keinem Team!", true);
            }
        }
    }


    @Subcommand("demote")
    @CommandCompletion("@players")
    public void onTeamDemote(Player player, String playerName) {
        if (isValidTarget(player, playerName)) {
            TexPlayer sender = getTexPlayerController().getPlayer(player);
            if (getTeamController().isValidTeam(sender.getTeam())) {
                if (getTeamController().isPrivileged(sender)) {
                    Player receiverPlayer = Bukkit.getPlayer(playerName);
                    TexPlayer receiver = getTexPlayerController().getPlayer(receiverPlayer);
                    if (sender.getTeam() != receiver.getTeam()) {
                        sendTo(player, "not in same team", true);
                        return;
                    }
                    if (getTeamController().demote(receiver)) {
                        sendTo(player, "Du hast " + receiverPlayer.getName() + "demoted", true);
                        sendTo(receiverPlayer, "Du wurdest von " + player.getName() + "demoted", true);
                    } else {
                        sendTo(player, "Der Spieler " + receiverPlayer.getName() + "ist kein Admin!", true);
                    }
                } else {
                    sendTo(player, "Du kannst als Member keine Leute promoten", true);
                }
            } else {
                sendTo(player, "Du bist in keinem Team!", true);
            }
        }
    }

    @Subcommand("sethome")
    public void onTeamSetHome(Player player) {
        TexPlayer sender = getTexPlayerController().getPlayer(player);
        Team team = sender.getTeam();
        if (getTeamController().isValidTeam(team)) {
            if (getTeamController().isPrivileged(sender)) {
                Location location = player.getLocation().clone();
                if (getTeamController().setHome(team, location)) {
                    sendTo(player, "Teamhome gesetzt", true);
                } else {
                    sendTo(player, "already set", true);
                }
            } else {
                sendTo(player, "Du kannst als Member keine Team Home setzen", true);
            }
        } else {
            sendTo(player, "Du bist in keinem Team!", true);
        }
    }

    @Subcommand("home")
    public void onTeamHome(Player player) {
        TexPlayer sender = getTexPlayerController().getPlayer(player);
        Team team = sender.getTeam();
        if (getTeamController().isValidTeam(team)) {
            if (getTeamController().isPrivileged(sender)) {
                if (getTeamController().sendHome(team, sender)) {
                    sendTo(player, "zum home tpd", true);
                } else {
                    sendTo(player, "no home :(", true);
                }
            } else {
                sendTo(player, "Du kannst als Member keine Team Home setzen", true);
            }
        } else {
            sendTo(player, "Du bist in keinem Team!", true);
        }
    }

    @Subcommand("accept")
    public void onTeamAccept(Player player) {
        TexPlayer texPlayer = getTexPlayerController().getPlayer(player);
        if (getTeamController().acceptInvite(texPlayer)) {
            sendTo(player, "joined lul", true);
        } else {
            sendTo(player, "nicht eingeladen", true);
        }
    }

    @Subcommand("info")
    public void onTeamInfo(Player player, @Optional String teamName) {
        if (teamName == null) {
            TexPlayer texPlayer = getTexPlayerController().getPlayer(player);
            Team team = texPlayer.getTeam();
            if (getTeamController().isValidTeam(team)) {
                showInfo(team, player);
            } else {
                sendTo(player, "Das Team existiert nicht!", true);
            }
        } else {
            Team team = getTeamController().getTeam(teamName);
            if (getTeamController().isValidTeam(team)) {
                showInfo(team, player);
            } else {
                sendTo(player, "Das Team existiert nicht!", true);
            }
        }
    }

    private void showInfo(Team team, Player player) {
        sendTo(player, team.getName(), true);
        team.getMemberList().forEach(member -> {
            sendTo(player, member.getTeamRank() + " | " + Bukkit.getOfflinePlayer(member.getUuid()).getName(), false);
        });
    }

    @Subcommand("leave")
    public void onTeamLeave(Player player) {
        TexPlayer texPlayer = getTexPlayerController().getPlayer(player);
        Team team = texPlayer.getTeam();
        if (getTeamController().isValidTeam(team)) {
            if (getTeamController().leaveTeam(texPlayer, team)) {
                sendTo(player, "Du hast dein Team verlassen", true);
            } else {
                sendTo(player, "Als Owner kannst du dein Team nicht verlassen", true);
            }
        } else {
            sendTo(player, "Du bist in keinem Team!", true);
        }
    }

    @Subcommand("help")
    public void onTeamHelpArg(Player player) {
        onTeamHelp(player);
    }

}
