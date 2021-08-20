package com.org.prgrms.mission1;


import java.util.*;

public class VoucherRepositoryImpl implements VoucherRepository {

    private final Map<UUID, Voucher> memory = new HashMap<>(); // HashMap  생성

    @Override
    public Optional<Voucher> findById(UUID voucherId) {

        return Optional.ofNullable(memory.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {    // HashMap value 값 Arraylist로 리턴
        return new ArrayList<>(memory.values());
    }

    @Override
    public Voucher insert(Voucher voucher) {  // hashMap 삽입
        memory.put(voucher.getVoucherID(), voucher);
        return voucher;
    }
}
