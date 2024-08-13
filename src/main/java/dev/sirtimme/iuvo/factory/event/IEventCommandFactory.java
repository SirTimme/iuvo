package dev.sirtimme.iuvo.factory.event;

import dev.sirtimme.iuvo.commands.ICommand;
import jakarta.persistence.EntityManager;
import net.dv8tion.jda.api.events.GenericEvent;

public interface IEventCommandFactory<T extends GenericEvent> {
    ICommand<T> createCommand(final EntityManager context);
}