package de.realmeze.impl.scoreboard.model;

import lombok.Getter;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class TexScoreboard {

    private static final List<ChatColor> colors = Arrays.asList(ChatColor.values()); //the colors

    private final Scoreboard scoreboard; //the scoreboard
    private final Objective objective; //the objective

    private final List<ScoreboardLine> boardLines = new ArrayList<>(); //where we will store our lines

    public TexScoreboard() {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard(); //get a new scoreboard
        objective = scoreboard.registerNewObjective(ChatColor.RED + "TexFight.net", "dummy"); //create a dummy objective
        objective.setDisplaySlot(DisplaySlot.SIDEBAR); //set the display slot
        for (int i = 0; i < colors.size(); i++) { //loop through the color values
            final ChatColor color = colors.get(i);
            final Team team = scoreboard.registerNewTeam("line" + i); //register our team for that line
            team.addEntry(color.toString()); //add the entry to the board
            boardLines.add(new ScoreboardLine(color, i, team)); //register a BoardLine for this team
        }
    }

}
