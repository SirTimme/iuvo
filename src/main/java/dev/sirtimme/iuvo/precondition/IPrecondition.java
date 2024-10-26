package dev.sirtimme.iuvo.precondition;

import dev.sirtimme.iuvo.entity.IEntity;
import dev.sirtimme.iuvo.precondition.implementation.IsComponentAuthor;
import dev.sirtimme.iuvo.precondition.implementation.IsRegistered;
import dev.sirtimme.iuvo.repository.Repository;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.GenericComponentInteractionCreateEvent;

public interface IPrecondition<T extends GenericInteractionCreateEvent> {
    boolean isValid(final T event);

    static IPrecondition<SlashCommandInteractionEvent> isRegistered(final Repository<? extends IEntity> repository) {
        return new IsRegistered(repository);
    }

    static IPrecondition<GenericComponentInteractionCreateEvent> isComponentAuthor() {
        return new IsComponentAuthor();
    }
}