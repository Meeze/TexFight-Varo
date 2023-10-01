package de.realmeze.listener;

import de.realmeze.impl.base.TexListener;
import de.realmeze.impl.chat.controller.GlobalMuteController;
import de.realmeze.impl.punishment.controller.PunishmentController;
import de.realmeze.impl.punishment.model.PunishmentType;
import de.realmeze.impl.staff.controller.SupportController;
import de.realmeze.impl.staff.model.SupportRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Map;

@Getter
@AllArgsConstructor
public class AsyncPlayerChatListener implements TexListener {

    private final GlobalMuteController globalMuteController;
    private final PunishmentController punishmentController;
    private final SupportController supportController;

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if(getSupportController().isInSupportChat(player)){
            sendTo(player, "SupportChat> " + player.getName() + ": " + event.getMessage(), false);
            sendTo(getSupportController().getSupportMap().get(player), "SupportChat> " + player.getName() + ": " + event.getMessage(), false);
            event.setCancelled(true);
            return;
        }
        for (Player inSupport : getSupportController().getSupportMap().keySet()) {
            event.getRecipients().remove(inSupport);
        }

        if(getGlobalMuteController().getGlobalMute().isEnabled()){
            player.sendMessage("globalmute ist aktiv");
            event.setCancelled(true);
            return;
        }

        punishmentController.getPunishment(player.getUniqueId(), PunishmentType.MUTE).ifPresent(punishment -> {
            player.sendMessage("du bist muted");
            event.setCancelled(true);
        });

    }
}
