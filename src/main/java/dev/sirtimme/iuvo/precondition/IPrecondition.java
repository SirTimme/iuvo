package dev.sirtimme.iuvo.precondition;

import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;

public interface IPrecondition<T extends GenericInteractionCreateEvent> {
    boolean isValid(final T event);
}