package dev.sirtimme.iuvo.api.precondition;

import dev.sirtimme.iuvo.api.entity.IEntity;
import dev.sirtimme.iuvo.api.repository.Repository;
import dev.sirtimme.iuvo.internal.precondition.IsComponentAuthor;
import dev.sirtimme.iuvo.internal.precondition.IsOwner;
import dev.sirtimme.iuvo.internal.precondition.IsRegistered;
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

    static IPrecondition<SlashCommandInteractionEvent> isOwner() {
        return new IsOwner();
    }
}