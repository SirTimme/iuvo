package dev.sirtimme.iuvo.internal.precondition;

import dev.sirtimme.iuvo.api.precondition.IPrecondition;
import dev.sirtimme.iuvo.api.localization.LocalizationManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;


public class IsOwner implements IPrecondition<SlashCommandInteractionEvent> {
    private final LocalizationManager localizationManager;

    public IsOwner(LocalizationManager localizationManager) {
        this.localizationManager = localizationManager;
    }

    @Override
    public boolean isValid(SlashCommandInteractionEvent event) {
        if (!event.getUser().getId().equals(System.getenv("OWNER_ID"))) {
            event.reply(localizationManager.get("iuvo.precondition.isOwner.invalid")).setEphemeral(true).queue();
            return false;
        }

        return true;
    }
}
