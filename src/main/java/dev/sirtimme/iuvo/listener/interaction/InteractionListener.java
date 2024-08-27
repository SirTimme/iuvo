package dev.sirtimme.iuvo.listener.interaction;

import dev.sirtimme.iuvo.factory.interaction.IInteractionCommandFactory;
import dev.sirtimme.iuvo.listener.ListenerBase;
import jakarta.persistence.EntityManagerFactory;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InteractionListener<T extends GenericInteractionCreateEvent> extends ListenerBase<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(InteractionListener.class);
    private final EntityManagerFactory entityManagerFactory;
    private final IInteractionCommandFactory<T> commandFactory;

    public InteractionListener(final Class<T> clazz, final EntityManagerFactory entityManagerFactory, final IInteractionCommandFactory<T> commandFactory) {
        super(clazz);
        this.entityManagerFactory = entityManagerFactory;
        this.commandFactory = commandFactory;
    }

    @Override
    public void handleEvent(final T event) {
        final var context = entityManagerFactory.createEntityManager();
        final var command = commandFactory.createCommand(event, context);
        final var locale = event.getGuildLocale().toLocale();

        if (command.hasInvalidPreconditions(event)) {
            return;
        }

        try {
            context.getTransaction().begin();
            command.execute(event, locale);
            context.getTransaction().commit();
        } catch (Exception error) {
            LOGGER.error("Execution of command {} failed: {}", command, error.getMessage());
            context.getTransaction().rollback();
        } finally {
            context.close();
        }
    }
}
