package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);
    Voucher update(Voucher voucher);
    void delete(UUID voucherId);
    void deleteAll();
    List<Voucher> findAll();
    List<Voucher> findAll(Optional<LocalDate> startDate, Optional<LocalDate> endDate, Optional<VoucherType> type);
    Optional<Voucher> findById(UUID voucherId);
}
