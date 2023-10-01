package de.realmeze.impl.scoreboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Team;

@AllArgsConstructor
@Getter
public class ScoreboardLine {

    private final ChatColor color; //the chatcolor name of our entry
    private final int line; //the line that the team will be on
    private final Team team; //the actual team itself

}
