package org.prgrms.assignment.voucher.repository;

import org.prgrms.assignment.customer.Customer;
import org.prgrms.assignment.voucher.entity.VoucherEntity;
import org.prgrms.assignment.voucher.entity.VoucherHistoryEntity;
import org.prgrms.assignment.voucher.model.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoucherRepository {
    Optional<VoucherEntity> findByID(UUID voucherId);

    VoucherEntity insert(VoucherEntity voucher, VoucherHistoryEntity voucherHistoryEntity);

    List<VoucherEntity> findAll();

    VoucherEntity update(VoucherEntity voucher, VoucherHistoryEntity voucherHistoryEntity);

    void deleteAll();

    void delete(UUID voucherId);
}
