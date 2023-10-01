package de.realmeze.impl.teams.controller;

import de.realmeze.impl.base.TexController;
import de.realmeze.impl.player.model.TexPlayer;
import de.realmeze.impl.teams.model.Team;
import de.realmeze.impl.teams.model.TeamInvite;
import de.realmeze.impl.teams.model.TeamMember;
import de.realmeze.impl.teams.model.TeamRank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class TeamController extends TexController {

    private final HashMap<String, Team> teams;
    private final HashMap<TexPlayer, TeamInvite> teamInvites;

    /**
     * creates team, set player in member and set texplayer team
     *
     * @param texPlayer creator
     * @param teamName  name
     * @return if team already exists false
     */
    public boolean createTeam(TexPlayer texPlayer, String teamName) {
        if (teamExists(teamName)) {
            return false;
        } else {
            TeamMember owner = new TeamMember(texPlayer.getPlayer().getUniqueId(), TeamRank.OWNER);
            ArrayList<TeamMember> members = new ArrayList<>();
            members.add(owner);
            Team team = new Team(teamName, members);
            texPlayer.setTeam(team);
            getTeams().put(teamName.toLowerCase(), team);
            return true;
        }
    }

    public boolean acceptInvite(TexPlayer accepter) {
        TeamInvite teamInvite = getTeamInvites().get(accepter);
        if (null == teamInvite) {
            return false;
        } else {
            joinTeam(accepter, teamInvite.getTeam());
            return true;
        }
    }

    public boolean inviteToTeam(TexPlayer sender, TexPlayer receiver) {
        if (isValidTeam(receiver.getTeam())) {
            return false;
        } else {
            TeamInvite teamInvite = new TeamInvite(sender.getTeam(), sender, receiver);
            getTeamInvites().put(receiver, teamInvite);
            return true;
        }
    }

    public boolean joinTeam(TexPlayer texPlayer, Team team) {
        if (isValidTeam(texPlayer.getTeam())) {
            return false;
        } else {
            texPlayer.setTeam(team);
            team.getMemberList().add(new TeamMember(texPlayer.getPlayer().getUniqueId(), TeamRank.MEMBER));
            return true;
        }
    }

    public boolean leaveTeam(TexPlayer texPlayer, Team team) {
        TeamMember member = find(team, texPlayer.getPlayer().getUniqueId());
        if (member.getTeamRank() != TeamRank.OWNER) {
            team.getMemberList().remove(member);
            texPlayer.setTeam(null);
            return true;
        } else {
            return false;
        }

    }

    public boolean promote(TexPlayer receiver) {
        if (isPrivileged(receiver)) {
            return false;
        } else {
            TeamMember toPromote = find(receiver.getTeam(), receiver);
            toPromote.setTeamRank(TeamRank.ADMIN);
            return true;
        }
    }

    public boolean demote(TexPlayer receiver) {
        if (isMember(receiver) || isOwner(receiver)) {
            return false;
        } else {
            TeamMember toPromote = find(receiver.getTeam(), receiver);
            toPromote.setTeamRank(TeamRank.MEMBER);
            return true;
        }
    }

    public boolean teamExists(String name) {
        return getTeams().containsKey(name);
    }

    public Team getCurrentTeam(TexPlayer texPlayer) {
        Team team = texPlayer.getTeam();
        return team;
    }

    public Team getTeam(String teamName) {
        return getTeams().get(teamName.toLowerCase());
    }

    public boolean isPrivileged(TexPlayer texPlayer) {
        TeamMember member = find(texPlayer.getTeam(), texPlayer.getPlayer().getUniqueId());
        return member.getTeamRank() == TeamRank.ADMIN || member.getTeamRank() == TeamRank.OWNER;
    }

    public boolean isOwner(TexPlayer texPlayer) {
        TeamMember member = find(texPlayer.getTeam(), texPlayer.getPlayer().getUniqueId());
        return member.getTeamRank() == TeamRank.OWNER;
    }

    public boolean isAdmin(TexPlayer texPlayer) {
        TeamMember member = find(texPlayer.getTeam(), texPlayer.getPlayer().getUniqueId());
        return member.getTeamRank() == TeamRank.ADMIN;
    }

    public boolean isMember(TexPlayer texPlayer) {
        TeamMember member = find(texPlayer.getTeam(), texPlayer.getPlayer().getUniqueId());
        return member.getTeamRank() == TeamRank.MEMBER;
    }

    public boolean setHome(Team team, Location location){
        if(null == team.getTeamHome()){
            team.setTeamHome(location);
            return true;
        } else {
            return false;
        }
    }
    public boolean sendHome(Team team, TexPlayer texPlayer){
        if(null == team.getTeamHome()){
            return false;
        } else {
            texPlayer.getPlayer().teleport(team.getTeamHome());
            return true;
        }
    }

    private TeamMember find(Team team, UUID uuid) {
        Optional<TeamMember> teamMember = team.getMemberList().stream().filter(member -> member.getUuid().equals(uuid)).findFirst();
        return teamMember.get();
    }

    private TeamMember find(Team team, TexPlayer texPlayer) {
        Optional<TeamMember> teamMember = team.getMemberList().stream().filter(member -> member.getUuid().equals(texPlayer.getPlayer().getUniqueId())).findFirst();
        return teamMember.get();
    }

    public boolean isValidTeam(Team team) {
        if (null != team) {
            return true;
        } else {
            return false;
        }
    }

    public void kick(TexPlayer sender, TexPlayer receiver) {
        Team team = getCurrentTeam(sender);
        team.getMemberList().remove(find(team, receiver));
        receiver.setTeam(null);
    }
}
