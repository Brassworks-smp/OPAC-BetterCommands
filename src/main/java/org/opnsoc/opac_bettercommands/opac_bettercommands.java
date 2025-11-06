package org.opnsoc.opac_bettercommands;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import org.opnsoc.opac_bettercommands.command.CommandRegister;
import org.opnsoc.opac_bettercommands.listener.PartyChatListener;
import org.slf4j.Logger;


@Mod(opac_bettercommands.MODID)
public class opac_bettercommands {
    public static final String MODID = "opac_bettercommands";
    private static final Logger LOGGER = LogUtils.getLogger();

    public opac_bettercommands() {
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(new PartyChatListener());
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        new CommandRegister().register(event.getDispatcher(), event.getCommandSelection());
        LOGGER.info("[OPAC-BetterCommands] Registered commands");
    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        LOGGER.info("[OPAC-BetterCommands] Successfully loaded");
    }
}
