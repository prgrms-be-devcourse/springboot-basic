package com.programmers.springbootbasic.domain.voucher.domain;

import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID id);

    int deleteById(UUID id);

    int update(Voucher voucher);
}
