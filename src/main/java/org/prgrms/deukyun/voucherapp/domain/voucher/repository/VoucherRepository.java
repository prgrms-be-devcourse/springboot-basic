package org.prgrms.deukyun.voucherapp.domain.voucher.repository;

import org.prgrms.deukyun.voucherapp.domain.voucher.entity.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 바우처 리포지토리
 */
public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    Optional<Voucher> findById(UUID id);

    List<Voucher> findAll();

    void clear();
}
