package de.realmeze.impl.staff.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
@Setter
@RequiredArgsConstructor
public class SupportRequest {

    private final Player issuer;
    private boolean accepted = false;
    private Player supporter;


}
