package dev.sirtimme.iuvo.api.repository;

import dev.sirtimme.iuvo.api.entity.IEntity;
import jakarta.persistence.EntityManager;

import java.util.List;

public abstract class QueryableRepository<T extends IEntity> extends Repository<T> {
    public QueryableRepository(final Class<T> clazz, final EntityManager context) {
        super(clazz, context);
    }

    public abstract List<T> findAll(final long id);

    public abstract void deleteAll(final long id);
}