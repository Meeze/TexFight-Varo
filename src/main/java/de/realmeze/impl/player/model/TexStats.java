package de.realmeze.impl.player.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TexStats {

    private Long kills;
    private Long deaths;
    private int lives;
    // this is seconds, dont forgetti :)
    private long onlineTime;

    public void addDeath(){
        setDeaths(getDeaths()+1);
    }

    public void addKill(){
        setKills(getKills()+1);
    }

    public void removeLive(){
        setLives(getLives()-1);
    }

    public void addOnlineTime(long secondsToAdd){
        setOnlineTime(getOnlineTime() + secondsToAdd);
    }

}
