package dev.sirtimme.iuvo.internal.precondition;

import dev.sirtimme.iuvo.api.entity.IEntity;
import dev.sirtimme.iuvo.api.precondition.IPrecondition;
import dev.sirtimme.iuvo.api.repository.Repository;
import dev.sirtimme.iuvo.api.localization.LocalizationManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;


public class IsRegistered implements IPrecondition<SlashCommandInteractionEvent> {
    private final Repository<? extends IEntity> repository;
    private final LocalizationManager localizationManager;

    public IsRegistered(final Repository<? extends IEntity> repository, final LocalizationManager localizationManager) {
        this.repository = repository;
        this.localizationManager = localizationManager;
    }

    @Override
    public boolean isValid(final SlashCommandInteractionEvent event) {
        final var user = repository.get(event.getUser().getIdLong());

        if (user == null) {
            event.reply(localizationManager.get("iuvo.precondition.isRegistered.invalid")).queue();
            return false;
        }

        return true;
    }
}
