package com.programmers.vouchermanagement.voucher.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    Optional<Voucher> findById(UUID id);

    List<Voucher> findAll();
}
