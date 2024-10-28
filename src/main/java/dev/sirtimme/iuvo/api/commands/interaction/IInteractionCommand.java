package dev.sirtimme.iuvo.api.commands.interaction;

import dev.sirtimme.iuvo.api.precondition.IPreconditionHolder;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;

public interface IInteractionCommand<T extends GenericInteractionCreateEvent> extends IPreconditionHolder<T> {
    void execute(final T event);
}
