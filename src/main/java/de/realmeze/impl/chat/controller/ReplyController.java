package de.realmeze.impl.chat.controller;

import de.realmeze.impl.base.TexController;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ReplyController extends TexController {

    private HashMap<Player, Player> lastMessengerMap;

    public ReplyController(HashMap<Player, Player> lastMessengerMap) {
        setLastMessengerMap(lastMessengerMap);
    }

    public void addLastMessenger(Player messageReceiver, Player messenger){
        if(this.lastMessengerMap.containsKey(messageReceiver)){
            this.lastMessengerMap.remove(messageReceiver);
        }
        this.lastMessengerMap.put(messageReceiver, messenger);
    }

    public HashMap<Player, Player> getLastMessengerMap() {
        return lastMessengerMap;
    }

    public void setLastMessengerMap(HashMap<Player, Player> lastMessengerMap) {
        this.lastMessengerMap = lastMessengerMap;
    }
}
