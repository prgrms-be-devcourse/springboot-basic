package org.prgms.management.voucher.repository;

import org.prgms.management.voucher.entity.Voucher;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

// TODO : name, type로 바우처를 찾는 메서드, 가격별로 정렬하는 메서드 구현
@Component
public interface VoucherRepository {
    Optional<Voucher> insert(Voucher voucher);

    Map<UUID, Voucher> getAll();
}
