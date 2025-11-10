package org.opnsoc.opac_better_commands.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class OpenClaimsCommand {
    public void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("claims")
                        .redirect(dispatcher.getRoot().getChild("openpac-claims"))
        );
    }
}