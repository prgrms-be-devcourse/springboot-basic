package com.prgrms.repository.voucher;

import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.Vouchers;

import java.util.Optional;

public interface VoucherRepository {

    Optional<Voucher> findById(int voucherId);

    Voucher insert(Voucher voucher);

    Vouchers getAllVoucher();
}
