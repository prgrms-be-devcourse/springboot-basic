package org.prgrms.assignment.voucher.repository;

import org.prgrms.assignment.voucher.entity.VoucherEntity;
import org.prgrms.assignment.voucher.entity.VoucherHistoryEntity;
import org.prgrms.assignment.voucher.model.VoucherType;
import org.prgrms.kdt.voucher.model.Voucher;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoucherRepository {

    Optional<VoucherEntity> findVoucherEntityById(UUID voucherId);

    Optional<VoucherHistoryEntity> findVoucherHistoryEntityById(UUID voucherId);

    void insert(VoucherEntity voucherEntity, VoucherHistoryEntity voucherHistoryEntity);

    List<VoucherEntity> findAll();

    VoucherEntity update(VoucherEntity voucher, VoucherHistoryEntity voucherHistoryEntity);

    void deleteAll();

    void delete(UUID voucherId);

    List<VoucherEntity> findVouchersByType(VoucherType voucherType);

}
