package org.prgms.voucheradmin.domain.voucher.dao;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * 생성된 바우처를 메모리에 저장하거나 메모리에 저장된 바우처 목록의 반환을 담당하는 클래스입니다.
 **/
@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    /**
     * 생성된 바우처를 저장하는 필드 입니다.
     **/
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    /**
     * 생성된 바우처를 메모리에 저장하는 메서드입니다.
     **/
    @Override
    public Voucher create(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    /**
     * 바우처들의 목록을 반환하는 메서드입니다.
     **/
    @Override
    public List<Voucher> getAll() {
        List<Voucher> vouchers = storage.keySet().stream()
                .map(storage::get)
                .collect(Collectors.toList());

        return vouchers;
    }
}
