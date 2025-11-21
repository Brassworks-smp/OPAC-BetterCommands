package org.opnsoc.opac_better_commands;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.opnsoc.opac_better_commands.command.CommandRegister;
import org.opnsoc.opac_better_commands.listener.PartyChatListener;
import org.slf4j.Logger;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Mod(opac_better_commands.MODID)
public class opac_better_commands {
    public static final String MODID = "opac_better_commands";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final ConcurrentHashMap<UUID, Boolean> PARTY_CHAT_ENABLED = new ConcurrentHashMap<>();

    public opac_better_commands() {
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(new PartyChatListener());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onRegisterCommands(RegisterCommandsEvent event) {
        new CommandRegister().register(event.getDispatcher());
        LOGGER.info("[OPAC-BetterCommands] Registered commands after server start");
    }
}