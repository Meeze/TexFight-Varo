package de.realmeze.impl.chat.model;

import de.realmeze.impl.base.TexModel;

public class GlobalMute extends TexModel {

    private boolean enabled;

    public GlobalMute(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
