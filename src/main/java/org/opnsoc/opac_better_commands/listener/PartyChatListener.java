package org.opnsoc.opac_better_commands.listener;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import org.opnsoc.opac_better_commands.utils.PartyMessenger;
import net.neoforged.bus.api.SubscribeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PartyChatListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(PartyChatListener.class);

    public static final ConcurrentHashMap<UUID, Boolean> PARTY_CHAT_ENABLED = new ConcurrentHashMap<>();

    @SubscribeEvent
    public void onServerChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        LOGGER.info("Value: {}", PARTY_CHAT_ENABLED.getOrDefault(player.getUUID(), false));
        boolean active = PARTY_CHAT_ENABLED.getOrDefault(player.getUUID(), false);

        if (active) {
            PartyMessenger.sendPartyMessage(player, event.getMessage().getString());
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        PARTY_CHAT_ENABLED.put(player.getUUID(), false);
        LOGGER.info("[PartyChat] Player {} joined, PARTY_CHAT_ENABLED set to false", player.getName().getString());
    }
}