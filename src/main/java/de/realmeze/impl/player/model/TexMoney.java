package de.realmeze.impl.player.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TexMoney {
    private long amount;

    public TexMoney(long amount) {
        setAmount(amount);
    }

    public void add(long amount) {
        setAmount(getAmount() + amount);
    }

    public void remove(long amount) {
        setAmount(getAmount() - amount);
    }
}
