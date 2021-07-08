package com.reflexian.example.test;

import com.reflexian.rapi.api.annotation.Registrar;
import com.reflexian.rapi.api.events.move.PlayerJumpEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@Registrar(type = Registrar.Type.LISTENER)
public class TestListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Bukkit.broadcastMessage("WELCOME! " + event.getPlayer().getName());
    }

    @EventHandler
    public void onMove(PlayerJumpEvent event) {
        if (event.getFrom().getBlockY()!=event.getTo().getBlockY()) {
            event.setCancelled(true);
        }
    }
}
