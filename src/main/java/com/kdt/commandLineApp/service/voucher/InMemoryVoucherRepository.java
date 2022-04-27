package com.kdt.commandLineApp.service.voucher;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toCollection;

@Repository
public class InMemoryVoucherRepository implements VoucherRepository {
    private Map<UUID, Voucher> map = new ConcurrentHashMap<>();

    @Override
    public void add(Voucher voucher) {
        map.put(voucher.getId(), voucher);
    }

    @Override
    public Optional<Voucher> get(String id) {
        return Optional.ofNullable(map.get(UUID.fromString(id)));
    }

    @Override
    public List<Voucher> getAll(int page, int size, String type) {
        List<Voucher> voucherList;

        if ((page < 0) || (size < 0)) {
            return List.of();
        }
        voucherList = map.values()
                .stream()
                .filter((e)-> type.equals(null)||e.getType().equals(type))
                .toList();
        if (page * size >= voucherList.size()) {
            return List.of();
        }
        return voucherList.stream()
                .skip(page * size)
                .limit(size)
                .toList();
    }

    @Override
    public void remove(String id) {
        map.remove(UUID.fromString(id));
    }

    @Override
    public void deleteAll() {
        map.clear();
    }

    @Override
    public void destroy() throws Exception {

    }
}
