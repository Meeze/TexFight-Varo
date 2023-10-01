package de.realmeze.impl.teams.model;

import de.realmeze.impl.player.model.TexPlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamInvite {

    private final Team team;
    private final TexPlayer sender;
    private final TexPlayer receiver;

}
