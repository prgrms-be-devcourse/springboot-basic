package org.prgrms.springorder.domain.voucher.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.springorder.domain.voucher.api.CustomerWithVoucher;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.model.VoucherType;

public interface VoucherRepository {

    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);

    List<Voucher> findAll();

    void deleteAll();

    Voucher update(Voucher voucher);

    Optional<CustomerWithVoucher> findByIdWithCustomer(UUID voucherId);

    void deleteById(UUID voucherId);

    List<Voucher> findAllBy(LocalDateTime startDate, LocalDateTime endDate, VoucherType voucherType);
}
