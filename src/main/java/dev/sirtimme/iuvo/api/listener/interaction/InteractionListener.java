package dev.sirtimme.iuvo.api.listener.interaction;

import dev.sirtimme.iuvo.api.factory.interaction.IInteractionCommandFactory;
import dev.sirtimme.iuvo.api.listener.ListenerBase;
import jakarta.persistence.EntityManagerFactory;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public class InteractionListener<T extends GenericInteractionCreateEvent> extends ListenerBase<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(InteractionListener.class);
    private final EntityManagerFactory entityManagerFactory;
    private final IInteractionCommandFactory<T> commandFactory;

    public final static ScopedValue<Locale> USER_LOCALE = ScopedValue.newInstance();

    public InteractionListener(final Class<T> clazz, final EntityManagerFactory entityManagerFactory, final IInteractionCommandFactory<T> commandFactory) {
        super(clazz);
        this.entityManagerFactory = entityManagerFactory;
        this.commandFactory = commandFactory;
    }

    @Override
    public void handleEvent(final T event) {
        final Runnable eventHandler = () -> {
            try (var context = entityManagerFactory.createEntityManager()) {
                final var command = commandFactory.createCommand(event, context);

                if (command.hasInvalidPreconditions(event)) {
                    return;
                }

                final var transaction = context.getTransaction();

                try {
                    transaction.begin();
                    command.execute(event);
                    transaction.commit();
                } catch (Exception error) {
                    transaction.rollback();
                    LOGGER.error("Executing command '{}' failed: {}", command, error.getMessage());
                }
            }
        };

        ScopedValue.where(USER_LOCALE, event.getUserLocale().toLocale()).run(eventHandler);
    }
}
