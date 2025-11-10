package org.opnsoc.opac_better_commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import org.opnsoc.opac_better_commands.command.CommandRegister;
import org.opnsoc.opac_better_commands.listener.PartyChatListener;
import org.slf4j.Logger;

@Mod(opac_better_commands.MODID)
public class opac_better_commands {
    public static final String MODID = "opac_better_commands";
    private static final Logger LOGGER = LogUtils.getLogger();

    public opac_better_commands() {
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(new PartyChatListener());
    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getServer().getCommands().getDispatcher();
        new CommandRegister().register(dispatcher, Commands.CommandSelection.DEDICATED);
        LOGGER.info("[OPAC-BetterCommands] Registered commands after server start");
    }
}