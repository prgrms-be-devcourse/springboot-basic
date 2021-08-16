package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.voucher.Voucher;

import java.util.*;
import java.util.stream.Collectors;

public class VoucherRepositoryImpl implements VoucherRepository{

    // 인메모리 저장소 역할
    private final Map<UUID, Voucher> memDatabase = new HashMap<>();

    // id로 voucher리턴
    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        // ofnullable을 이용해 NPE방지 , null값이면 empty를 반환
        return Optional.ofNullable(memDatabase.getOrDefault(voucherId, null));
    }

    // memory로 저장
    @Override
    public Voucher save(Voucher voucher) {
        return memDatabase.put(voucher.getVoucherId(), voucher);
    }


    //map to list
    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(memDatabase.values());
    }
}
