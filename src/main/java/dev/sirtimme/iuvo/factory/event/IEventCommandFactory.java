package dev.sirtimme.iuvo.factory.event;

import dev.sirtimme.iuvo.commands.event.IEventCommand;
import jakarta.persistence.EntityManager;
import net.dv8tion.jda.api.events.GenericEvent;

public interface IEventCommandFactory<T extends GenericEvent> {
    IEventCommand<T> createCommand(final EntityManager context);
}