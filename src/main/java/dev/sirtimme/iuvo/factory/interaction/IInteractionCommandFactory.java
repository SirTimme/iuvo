package dev.sirtimme.iuvo.factory.interaction;

import dev.sirtimme.iuvo.commands.interaction.IInteractionCommand;
import jakarta.persistence.EntityManager;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;

public interface IInteractionCommandFactory<T extends GenericInteractionCreateEvent> {
    IInteractionCommand<T> createCommand(final T event, final EntityManager context);
}