package dev.sirtimme.iuvo.api.listener.interaction;

import dev.sirtimme.iuvo.api.factory.interaction.IInteractionCommandFactory;
import dev.sirtimme.iuvo.api.listener.ListenerBase;
import jakarta.persistence.EntityManagerFactory;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.interactions.DiscordLocale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InteractionListener<T extends GenericInteractionCreateEvent> extends ListenerBase<T> {
    public final static ScopedValue<DiscordLocale> USER_LOCALE = ScopedValue.newInstance();
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

        ScopedValue.where(USER_LOCALE, event.getUserLocale()).run(
                () -> {
                    if (command.hasInvalidPreconditions(event)) {
                        return;
                    }

                    try {
                        context.getTransaction().begin();
                        command.execute(event);
                        context.getTransaction().commit();
                    } catch (Exception error) {
                        LOGGER.error("Execution of command {} failed: {}", command, error.getMessage());
                        context.getTransaction().rollback();
                    } finally {
                        context.close();
                    }
                }
        );
    }
}
