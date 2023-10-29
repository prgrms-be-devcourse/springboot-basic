package com.prgrms.vouchermanagement.core.voucher.repository;

import com.prgrms.vouchermanagement.core.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    void deleteAll();

    Optional<Voucher> findById(String id);
}
