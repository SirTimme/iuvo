package dev.sirtimme.iuvo.precondition.implementation;

import dev.sirtimme.iuvo.entity.IEntity;
import dev.sirtimme.iuvo.precondition.IPrecondition;
import dev.sirtimme.iuvo.repository.Repository;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class IsRegistered implements IPrecondition<SlashCommandInteractionEvent> {
    private final Repository<? extends IEntity> repository;

    public IsRegistered(final Repository<? extends IEntity> repository) {
        this.repository = repository;
    }

    @Override
    public boolean isValid(final SlashCommandInteractionEvent event) {
        final var user = repository.get(event.getUser().getIdLong());

        if (user == null) {
            event.reply("You are not registered, please use `/register` first").queue();
            return false;
        }

        return true;
    }
}
