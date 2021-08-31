package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.CustomerEntity;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.domain.VoucherEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    VoucherEntity insert(VoucherEntity voucher);

    VoucherEntity update(VoucherEntity voucher);

    List<VoucherEntity> findAll();

    Optional<VoucherEntity> findById(UUID voucherId);

    void deleteAll();

    void deleteById(UUID voucherId);
}
