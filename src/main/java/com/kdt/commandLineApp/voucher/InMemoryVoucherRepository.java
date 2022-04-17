package com.kdt.commandLineApp.voucher;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toCollection;

@Repository
@Profile("dev")
public class InMemoryVoucherRepository implements VoucherRepository {
    private Map<UUID, Voucher> map = new ConcurrentHashMap<>();

    @Override
    public void add(Voucher voucher) {
        map.put(voucher.getId(), voucher);
    }

    @Override
    public Optional<Voucher> get(UUID id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public List<Voucher> getAll() {
        return map.values()
                .stream()
                .collect(toCollection(ArrayList::new));
    }

    @Override
    public void remove(UUID vid) {

    }
}
