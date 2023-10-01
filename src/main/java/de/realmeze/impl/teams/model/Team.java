package de.realmeze.impl.teams.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;

import java.util.ArrayList;

@Getter
@Setter
@RequiredArgsConstructor
public class Team {

    private final String name;
    private final ArrayList<TeamMember> memberList;
    private Location teamHome;


}
