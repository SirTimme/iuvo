package dev.sirtimme.iuvo.internal.precondition;

import dev.sirtimme.iuvo.api.precondition.IPrecondition;
import dev.sirtimme.iuvo.api.localization.LocalizationManager;
import net.dv8tion.jda.api.events.interaction.component.GenericComponentInteractionCreateEvent;


public class IsComponentAuthor implements IPrecondition<GenericComponentInteractionCreateEvent> {
    private final LocalizationManager localizationManager;

    public IsComponentAuthor(final LocalizationManager localizationManager) {
        this.localizationManager = localizationManager;
    }

    @Override
    public boolean isValid(final GenericComponentInteractionCreateEvent event) {
        final var authorId = event.getComponentId().split(":")[0];

        if (!event.getUser().getId().equals(authorId)) {
            event.reply(localizationManager.get("iuvo.precondition.isComponentAuthor.invalid")).setEphemeral(true).queue();
            return false;
        }

        return true;
    }
}
