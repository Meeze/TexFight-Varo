package de.realmeze.impl.teams.model;

import de.realmeze.impl.player.model.TexPlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class TeamMember {

    private final UUID uuid;
    private TeamRank teamRank;

}
