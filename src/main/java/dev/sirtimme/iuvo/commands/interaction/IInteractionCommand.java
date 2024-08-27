package dev.sirtimme.iuvo.commands.interaction;

import dev.sirtimme.iuvo.precondition.IPreconditionHolder;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;

import java.util.Locale;

public interface IInteractionCommand<T extends GenericInteractionCreateEvent> extends IPreconditionHolder<T> {
    void execute(final T event, final Locale locale);
}
