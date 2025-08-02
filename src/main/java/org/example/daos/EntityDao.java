package org.example.daos;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface EntityDao <T> {
    void add(T newEntity);
    void delete(UUID id);
    void update(UUID id, T updatedEntity);
    Optional<T> get(UUID id);
    Collection<T> getAll();
}
