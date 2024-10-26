package dev.sirtimme.iuvo.api.commands.event;

import net.dv8tion.jda.api.events.GenericEvent;

public interface IEventCommand<T extends GenericEvent> {
    void execute(final T event);
}
