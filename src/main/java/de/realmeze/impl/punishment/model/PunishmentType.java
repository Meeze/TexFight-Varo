package de.realmeze.impl.punishment.model;

public enum PunishmentType {

    KICK("KICK"),
    MUTE("MUTE"),
    BAN("BAN");

    private String name;

    PunishmentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
