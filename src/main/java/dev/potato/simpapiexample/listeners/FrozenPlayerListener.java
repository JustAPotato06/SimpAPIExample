package dev.potato.simpapiexample.listeners;

import dev.potato.simpapiexample.SimpAPIExample;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FrozenPlayerListener implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (SimpAPIExample.getFrozenPlayers().contains(p)) {
            e.setCancelled(true);
        }
    }
}
