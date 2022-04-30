package org.prgrms.spring_week1.Voucher.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.spring_week1.Voucher.model.Voucher;
import org.prgrms.spring_week1.Voucher.model.VoucherType;

public interface VoucherRepository {

    List<Voucher> getAllVoucher();

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findByCustomer(UUID customerId);

    Voucher insert(Voucher voucher);

    Voucher update(Voucher voucher);

    void deleteById(UUID voucherId);

    List<Voucher> findByType(VoucherType voucherType);
}
