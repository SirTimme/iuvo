package dev.sirtimme.iuvo.internal.precondition;

import dev.sirtimme.iuvo.api.entity.IEntity;
import dev.sirtimme.iuvo.api.precondition.IPrecondition;
import dev.sirtimme.iuvo.api.repository.Repository;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import static dev.sirtimme.iuvo.api.localization.LocalizationManager.getResponse;

public class IsRegistered implements IPrecondition<SlashCommandInteractionEvent> {
    private final Repository<? extends IEntity> repository;

    public IsRegistered(final Repository<? extends IEntity> repository) {
        this.repository = repository;
    }

    @Override
    public boolean isValid(final SlashCommandInteractionEvent event) {
        final var user = repository.get(event.getUser().getIdLong());

        if (user == null) {
            event.reply(getResponse("iuvo.error.notRegistered", event.getUserLocale())).queue();
            return false;
        }

        return true;
    }
}
