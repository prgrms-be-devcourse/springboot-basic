package org.programmers.VoucherManagement.voucher.infrastructure;

import org.programmers.VoucherManagement.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher save(Voucher voucher); //저장

    void update(Voucher voucher); //수정

    void delete(Voucher voucher); //삭제

    void deleteAll(); //전체 삭제

    List<Voucher> findAll(); //전체 조회

    Optional<Voucher> findById(UUID voucherId); //조회
}
