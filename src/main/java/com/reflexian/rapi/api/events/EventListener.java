package com.reflexian.rapi.api.events;

import com.reflexian.rapi.api.annotation.Registrar;
import com.reflexian.rapi.api.events.move.PlayerJumpEvent;
import com.reflexian.rapi.api.events.move.PlayerRelocateEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

@Registrar(type = Registrar.Type.LISTENER)
public class EventListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent event) {
        PlayerRelocateEvent relocateEvent = new PlayerRelocateEvent(event.getPlayer(), event.getFrom(),event.getTo());
        Bukkit.getPluginManager().callEvent(relocateEvent);
        if (relocateEvent.isCancelled()) {
            event.getPlayer().teleport(event.getFrom());
            return;
        }
        if (event.getFrom().getY()<event.getTo().getY()&&event.getTo().getY()!=event.getFrom().getY()+0.5) {
            PlayerJumpEvent jumpEvent = new PlayerJumpEvent(relocateEvent.getPlayer(), event.getFrom(),event.getTo());
            Bukkit.getPluginManager().callEvent(jumpEvent);
            if (jumpEvent.isCancelled()) {
                event.getPlayer().teleport(event.getFrom());
            }
        }

    }

}
