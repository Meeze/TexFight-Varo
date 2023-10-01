package de.realmeze.impl.chat.controller;

import de.realmeze.impl.base.TexController;
import de.realmeze.impl.chat.model.GlobalMute;

public class GlobalMuteController extends TexController {

    private GlobalMute globalMute;

    public GlobalMuteController(GlobalMute globalMute) {
        this.globalMute = globalMute;
    }

    public GlobalMute getGlobalMute() {
        return globalMute;
    }

    public void setGlobalMute(GlobalMute globalMute) {
        this.globalMute = globalMute;
    }
}
