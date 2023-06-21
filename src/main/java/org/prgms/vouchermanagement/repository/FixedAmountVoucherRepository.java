package org.prgms.vouchermanagement.repository;

import org.prgms.vouchermanagement.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class FixedAmountVoucherRepository implements VoucherRepository{
    private Optional<Voucher> fixedVoucher;

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }
}
