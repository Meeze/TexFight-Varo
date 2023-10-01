package de.realmeze.impl.player.controller;

import de.realmeze.impl.base.TexController;
import de.realmeze.impl.player.model.KillComparator;
import de.realmeze.impl.player.model.TexPlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;


@AllArgsConstructor
@Getter
public class RankingController extends TexController {

    private final TexPlayerController texPlayerController;

    public ArrayList<TexPlayer> getKillRanking() {
        ArrayList<TexPlayer> tpSort = new ArrayList<>(getTexPlayerController().getTexPlayerMap().values());
        tpSort.sort(new KillComparator());
        return tpSort;
    }

}
