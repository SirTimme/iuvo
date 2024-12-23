package dev.sirtimme.iuvo.api.listener;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ListenerBase<T extends GenericEvent> implements EventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListenerBase.class);
    private final Class<T> clazz;

    protected ListenerBase(final Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void onEvent(@NotNull final GenericEvent genericEvent) {
        if (clazz.isInstance(genericEvent)) {
            LOGGER.debug("Received event with type '{}'", clazz.getSimpleName());

            handleEvent(clazz.cast(genericEvent));
        }
    }

    public abstract void handleEvent(final T event);
}