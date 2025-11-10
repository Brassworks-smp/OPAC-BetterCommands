package org.opnsoc.opac_better_commands.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import xaero.pac.common.server.parties.command.CommandRequirementProvider;

public class CommandRegister {
    public void register(CommandDispatcher<CommandSourceStack> dispatcher, Commands.CommandSelection environment) {
        CommandRequirementProvider commandRequirementProvider = new CommandRequirementProvider();
        new PartyChatCommand().register(dispatcher, environment, commandRequirementProvider);
        new OpenPartiesCommand().register(dispatcher);
        new OpenClaimsCommand().register(dispatcher);
    }
}
