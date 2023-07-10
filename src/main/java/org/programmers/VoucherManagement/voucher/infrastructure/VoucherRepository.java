package org.programmers.VoucherManagement.voucher.infrastructure;

import org.programmers.VoucherManagement.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    void save(Voucher voucher); //저장

    void update(Voucher voucher); //수정

    void delete(Voucher voucher); //삭제

    List<Voucher> findAll(); //전체 조회

    Optional<Voucher> findById(UUID voucherId);
}
