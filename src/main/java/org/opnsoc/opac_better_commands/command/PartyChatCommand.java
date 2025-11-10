package org.opnsoc.opac_better_commands.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import xaero.pac.common.server.parties.command.CommandRequirementProvider;
import org.opnsoc.opac_better_commands.utils.PartyMessenger;
import org.opnsoc.opac_better_commands.listener.PartyChatListener;

import java.util.UUID;
import java.util.function.Predicate;

public class PartyChatCommand {
    public void register(CommandDispatcher<CommandSourceStack> dispatcher, Commands.CommandSelection environment, CommandRequirementProvider commandRequirementProvider) {
        Predicate<CommandSourceStack> requirement = commandRequirementProvider.getMemberRequirement((party, mi) -> true);

        // Toggle
        Command<CommandSourceStack> toggleAction = ctx -> {
            ServerPlayer player = ctx.getSource().getPlayerOrException();
            UUID id = player.getUUID();
            boolean newState = !PartyChatListener.PARTY_CHAT_ENABLED.getOrDefault(id, false);
            PartyChatListener.PARTY_CHAT_ENABLED.put(id, newState);
            Component stateText = Component.literal(
                    newState ? "enabled" : "disabled"
            ).withStyle(newState ? ChatFormatting.GREEN : ChatFormatting.RED);
            player.sendSystemMessage(Component.literal("Party chat is now").append(Component.literal(" ")).append(stateText));
            return 1;
        };

        // Status
        Command<CommandSourceStack> statusAction = ctx -> {
            ServerPlayer player = ctx.getSource().getPlayerOrException();
            boolean state = PartyChatListener.PARTY_CHAT_ENABLED.getOrDefault(player.getUUID(), false);
            Component stateText = Component.literal(
                    state ? "enabled" : "disabled"
            ).withStyle(state ? ChatFormatting.GREEN : ChatFormatting.RED);
            player.sendSystemMessage(Component.literal("Party chat has been").append(Component.literal(" ")).append(stateText));
            return 1;
        };

        // Message
        Command<CommandSourceStack> messageAction = ctx -> {
            ServerPlayer player = ctx.getSource().getPlayerOrException();
            String message = StringArgumentType.getString(ctx, "message");
            PartyMessenger.sendPartyMessage(player, message);
            return 1;
        };

        dispatcher.register(
                Commands.literal("pchat")
                        .requires(requirement)
                        .then(Commands.literal("toggle").executes(toggleAction))
                        .then(Commands.literal("status").executes(statusAction))
                        .then(Commands.argument("message", StringArgumentType.greedyString()).executes(messageAction))
        );
    }
}