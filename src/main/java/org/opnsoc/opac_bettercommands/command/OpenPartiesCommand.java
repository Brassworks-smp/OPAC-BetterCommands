package org.opnsoc.opac_bettercommands.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

public class OpenPartiesCommand {

    public void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        CommandNode<CommandSourceStack> target = dispatcher.getRoot().getChild("openpac-parties");

        if (target != null) {
            dispatcher.register(
                    Commands.literal("party")
                            .requires(target.getRequirement())
                            .redirect(target)
            );
        } else {
            scheduleRedirectLater(dispatcher);
        }
    }

    private void scheduleRedirectLater(CommandDispatcher<CommandSourceStack> dispatcher) {
        NeoForge.EVENT_BUS.addListener((ServerStartedEvent event) -> {
            CommandNode<CommandSourceStack> target = dispatcher.getRoot().getChild("openpac-parties");

            if (target != null) {
                dispatcher.register(
                        Commands.literal("party")
                                .requires(target.getRequirement())
                                .redirect(target)
                );
            }
        });
    }
}