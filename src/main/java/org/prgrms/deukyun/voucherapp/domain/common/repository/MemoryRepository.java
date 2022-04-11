package org.prgrms.deukyun.voucherapp.domain.common.repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkState;

public abstract class MemoryRepository<E, Id> implements Repository<E, Id> {

    private final Map<Id, E> storage;
    private final Supplier<Id> idSupplier;

    public MemoryRepository(Supplier<Id> idSupplier) {
        storage = new ConcurrentHashMap<>();
        this.idSupplier = idSupplier;
    }

    @Override
    public E insert(E elem) {

        try {
            Field idField = elem.getClass().getDeclaredField("id");
            Id id = idSupplier.get();
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
    public Optional<E> findById(Id id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<E> findAll() {
        return new ArrayList<>(storage.values());
    }
}
