package dev.sirtimme.iuvo.commands;

import dev.sirtimme.iuvo.precondition.IPreconditionHolder;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;

public interface IInteractionCommand<T extends GenericInteractionCreateEvent> extends ICommand<T>, IPreconditionHolder<T> {}
