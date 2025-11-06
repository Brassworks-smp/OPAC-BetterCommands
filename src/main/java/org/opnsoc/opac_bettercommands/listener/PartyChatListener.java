package org.opnsoc.opac_bettercommands.listener;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.event.ServerChatEvent;
import org.opnsoc.opac_bettercommands.command.PartyChatCommand;
import org.opnsoc.opac_bettercommands.utils.PartyMessenger;
import net.neoforged.bus.api.SubscribeEvent;

public class PartyChatListener {
    @SubscribeEvent
    public void onServerChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        boolean active = PartyChatCommand.PARTY_CHAT_ENABLED.getOrDefault(player.getUUID(), false);

        if (active) {
            PartyMessenger.sendPartyMessage(player, event.getMessage().getString());
            event.setCanceled(true);
        }
    }
}