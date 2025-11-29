package org.opnsoc.opac_better_commands.listener;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import org.opnsoc.opac_better_commands.utils.PartyMessenger;
import net.neoforged.bus.api.SubscribeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.opnsoc.opac_better_commands.opac_better_commands;

public class PartyChatListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(PartyChatListener.class);

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onServerChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        Boolean isEnabled = opac_better_commands.PARTY_CHAT_ENABLED.getOrDefault(player.getUUID(), false);

        if (isEnabled) {
            PartyMessenger.sendPartyMessage(player, event.getMessage().getString());
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        opac_better_commands.PARTY_CHAT_ENABLED.put(player.getUUID(), false);
    }
}