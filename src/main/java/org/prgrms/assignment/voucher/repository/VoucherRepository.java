package org.prgrms.assignment.voucher.repository;

import org.prgrms.assignment.voucher.entity.VoucherEntity;
import org.prgrms.assignment.voucher.entity.VoucherHistoryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoucherRepository {
    Optional<VoucherEntity> findVoucherEntityById(UUID voucherId);

    Optional<VoucherHistoryEntity> findVoucherHistoryEntityById(UUID voucherId);

    VoucherEntity insertVoucherEntity(VoucherEntity voucher);

    VoucherHistoryEntity insertVoucherHistoryEntity(VoucherHistoryEntity voucherHistoryEntity);

    List<VoucherEntity> findAll();

    VoucherEntity update(VoucherEntity voucher);

    void deleteAll();

    void delete(UUID voucherId);
}
