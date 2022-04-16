package org.prgrms.deukyun.voucherapp.domain.common.repository;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.base.Preconditions.checkState;

public abstract class MemoryRepository<E> implements Repository<E, UUID> {

    private final Map<UUID, E> storage;

    public MemoryRepository() {
        storage = new ConcurrentHashMap<>();
    }

    @Override
    public E insert(E elem) {

        try {
            Field idField = elem.getClass().getDeclaredField("id");
            UUID id = UUID.randomUUID();
            idField.setAccessible(true);

            checkState(idField.get(elem) == null, "id must be null before set Id.");
            idField.set(elem, id);

            storage.put(id, elem);
            return elem;
        } catch (NoSuchFieldException ex) {
            throw new NoIdFieldException("there is no field name [id]", ex);
        } catch (IllegalAccessException ex) {
            throw new IllegalStateException("Something Wrong", ex);
        }
    }

    @Override
    public Optional<E> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<E> findAll() {
        return new ArrayList<>(storage.values());
    }
}
