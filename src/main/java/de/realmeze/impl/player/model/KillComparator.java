package de.realmeze.impl.player.model;

import java.util.Comparator;

public class KillComparator implements Comparator<TexPlayer> {
    @Override
    public int compare(TexPlayer o1, TexPlayer o2) {
        return o1.getTexStats().getKills().compareTo(o2.getTexStats().getKills());
    }
}
