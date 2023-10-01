package de.realmeze.listener;

import de.realmeze.impl.base.TexListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@AllArgsConstructor
@Getter
public class EntityDamageByEntityListener implements TexListener {

    public void onDamage(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player){

        }
    }

}
