package de.realmeze.impl.scoreboard.controller;

import de.realmeze.impl.base.TexController;
import de.realmeze.impl.player.model.TexPlayer;
import de.realmeze.impl.scoreboard.model.ScoreboardLine;
import de.realmeze.impl.scoreboard.model.TexScoreboard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.HashMap;

@Getter
@AllArgsConstructor
public class ScoreboardController extends TexController {

    private final HashMap<TexPlayer, TexScoreboard> texScoreboardMap;

    public void disableScoreboard(TexPlayer texPlayer) {
        texPlayer.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    public void enableScoreboard(TexPlayer texPlayer) {
        texPlayer.getPlayer().setScoreboard(getBoard(texPlayer).getScoreboard());
    }

    private ScoreboardLine getBoardLine(TexPlayer texPlayer, int line) {
        return getBoard(texPlayer).getBoardLines().stream().filter(boardLine -> boardLine.getLine() == line).findFirst().orElse(null);
    }

    public void setValue(TexPlayer texPlayer, int line, String value) {
        TexScoreboard texScoreboard = getBoard(texPlayer);
        final ScoreboardLine boardLine = getBoardLine(texPlayer, line); //get our board line
        Validate.notNull(boardLine, "Unable to find BoardLine with index of " + line + "."); //make sure it exists
        texScoreboard.getObjective().getScore(boardLine.getColor().toString()).setScore(line); //set the line on the scoreboard
        boardLine.getTeam().setPrefix(value); //set the actual display value. if you need more than 16 characters, you are able to use the suffix but doing this dynamically is a little harder
    }

    public void registerBoard(TexPlayer texPlayer) {
        getTexScoreboardMap().put(texPlayer, new TexScoreboard());
        //setValue(texPlayer, 16, "§7<--------->");
        setValue(texPlayer, 14, ChatColor.GRAY + "Stats:");
        setStats(texPlayer);
        setValue(texPlayer, 12, ChatColor.GRAY + "Leben:");
        setLives(texPlayer);
        setValue(texPlayer, 10, ChatColor.GRAY + "Rang:");
        setRank(texPlayer);
        //setValue(texPlayer, 7, "§7<--------->");
    }

    public void setStats(TexPlayer texPlayer){
        setValue(texPlayer, 13,
                ChatColor.GREEN + String.valueOf(texPlayer.getTexStats().getKills())
                        + ChatColor.GRAY + "/"
                        + ChatColor.RED + String.valueOf(texPlayer.getTexStats().getDeaths()));
    }

    public void setLives(TexPlayer texPlayer) {
        setValue(texPlayer, 11, ChatColor.LIGHT_PURPLE + String.valueOf(texPlayer.getTexStats().getLives()));
    }

    public void setRank(TexPlayer texPlayer) {
        setValue(texPlayer, 9, "Developer");
    }


    public TexScoreboard getBoard(TexPlayer texPlayer) {
        return texScoreboardMap.get(texPlayer);
    }

}
