package dev.sirtimme.iuvo.api.repository;

import dev.sirtimme.iuvo.api.entity.IEntity;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.jetbrains.annotations.Nullable;

public abstract class Repository<T extends IEntity> {
    private final Class<T> clazz;
    private final EntityManager context;

    public Repository(final Class<T> clazz, final EntityManager context) {
        this.clazz = clazz;
        this.context = context;
    }

    @Nullable
    public T get(final long id) {
        return context
                .unwrap(Session.class)
                .bySimpleNaturalId(clazz)
                .load(id);
    }

    public void add(final T entity) {
        context.persist(entity);
    }

    public void delete(final T entity) {
        context.remove(entity);
    }
}