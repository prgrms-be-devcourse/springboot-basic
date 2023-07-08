package org.prgms.vouchermanagement.voucher.domain.repository;

import org.prgms.vouchermanagement.voucher.domain.entity.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Optional<Voucher> findById(UUID voucherId);
    Optional<Voucher> saveVoucher(Voucher voucher);
    List<Voucher> getVoucherList();
}
