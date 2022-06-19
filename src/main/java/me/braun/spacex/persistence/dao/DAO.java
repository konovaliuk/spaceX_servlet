package me.braun.spacex.persistence.dao;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

interface DAO<E, I> {
    @NotNull
    List<E> findAll();

    Optional<E> findById(I id);

    E save(E entity) throws IllegalStateException;

    E update(E entity);

    void delete(I id);
}
