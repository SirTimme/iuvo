package dev.sirtimme.iuvo.internal.precondition;

import dev.sirtimme.iuvo.api.precondition.IPrecondition;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import static dev.sirtimme.iuvo.api.localization.LocalizationManager.getResponse;

public class IsOwner implements IPrecondition<SlashCommandInteractionEvent> {
    @Override
    public boolean isValid(SlashCommandInteractionEvent event) {
        if (!event.getUser().getId().equals(System.getenv("OWNER_ID"))) {
            event.reply(getResponse("iuvo.error.notOwner", event.getUserLocale())).setEphemeral(true).queue();
            return false;
        }

        return true;
    }
}
