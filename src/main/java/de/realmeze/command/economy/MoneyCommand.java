package de.realmeze.command.economy;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import de.realmeze.impl.base.TexCommand;
import de.realmeze.impl.player.controller.TexPlayerController;
import de.realmeze.impl.player.model.TexPlayer;
import org.bukkit.entity.Player;

@CommandAlias("money|coins|balance|bal|geld")
public class MoneyCommand extends TexCommand {

    private TexPlayerController texPlayerController;

    public MoneyCommand(TexPlayerController texPlayerController) {
        setTexPlayerController(texPlayerController);
    }

    public TexPlayerController getTexPlayerController() {
        return texPlayerController;
    }

    public void setTexPlayerController(TexPlayerController texPlayerController) {
        this.texPlayerController = texPlayerController;
    }

    @Default
    public void onMoney(Player player){
        TexPlayer texPlayer = texPlayerController.getTexPlayerMap().get(player.getUniqueId());
        if(texPlayer == null) {
            sendPlayerNotFound(player, "DICH? WTF");
            return;
        }
        sendTo(player, "Du hast " + texPlayer.getMoney().getAmount() + " Coins", true);
    }
}
