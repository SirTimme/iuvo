package dev.sirtimme.iuvo.commands.interaction;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

public interface ISubCommand extends IInteractionCommand<SlashCommandInteractionEvent> {
    SubcommandData getSubCommandData();
}
