package org.opnsoc.opac_bettercommands.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import xaero.pac.common.server.parties.command.CommandRequirementProvider;
import org.opnsoc.opac_bettercommands.utils.PartyMessenger;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Predicate;

public class PartyChatCommand {
    public static final HashMap<UUID, Boolean> PARTY_CHAT_ENABLED = new HashMap<>();

    public void register(CommandDispatcher<CommandSourceStack> dispatcher, Commands.CommandSelection environment, CommandRequirementProvider commandRequirementProvider) {
        Predicate<CommandSourceStack> requirement = commandRequirementProvider.getMemberRequirement((party, mi) -> true);

        // Toggle
        Command<CommandSourceStack> toggleAction = ctx -> {
            ServerPlayer player = ctx.getSource().getPlayerOrException();
            UUID id = player.getUUID();
            boolean newState = !PARTY_CHAT_ENABLED.getOrDefault(id, false);
            PARTY_CHAT_ENABLED.put(id, newState);
            Component stateText = Component.translatable(
                    newState ? "message.party_chat.state.activated" : "message.party_chat.state.deactivated"
            ).withStyle(newState ? ChatFormatting.GREEN : ChatFormatting.RED);
            player.sendSystemMessage(Component.translatable("message.party_chat.changed_status").append(Component.literal(" ")).append(stateText));
            return 1;
        };

        // Status
        Command<CommandSourceStack> statusAction = ctx -> {
            ServerPlayer player = ctx.getSource().getPlayerOrException();
            boolean state = PARTY_CHAT_ENABLED.getOrDefault(player.getUUID(), false);
            Component stateText = Component.translatable(
                    state ? "message.party_chat.state.activated" : "message.party_chat.state.deactivated"
            ).withStyle(state ? ChatFormatting.GREEN : ChatFormatting.RED);
            player.sendSystemMessage(Component.translatable("message.party_chat.status").append(Component.literal(" ")).append(stateText));
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