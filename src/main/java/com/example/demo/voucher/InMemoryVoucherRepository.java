package com.example.demo.voucher;

import com.example.demo.voucher.Voucher;
import com.example.demo.voucher.VoucherRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

@Repository
public class InMemoryVoucherRepository implements VoucherRepository {
    HashMap<UUID, Voucher> hashMap = new HashMap<UUID, Voucher>();

    @Override
    public void add(Voucher voucher) {
        hashMap.put(voucher.getId(), voucher);
    }

    @Override
    public Optional<Voucher> get(UUID id) {
        return Optional.ofNullable(hashMap.get(id));
    }

    @Override
    public Optional<ArrayList<Voucher>> getAll() {
        return Optional.ofNullable(Stream.of(hashMap.keySet()).map((e)->hashMap.get(e)).collect(toCollection(ArrayList::new)));
    }
}
