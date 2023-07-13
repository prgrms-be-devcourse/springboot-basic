package org.prgms.voucher.voucher.repository;

import org.prgms.voucher.voucher.AmountVoucher;

import java.util.List;

public interface AmountVoucherRepository {
    AmountVoucher save(AmountVoucher amountVoucher);

    List<AmountVoucher> findAll();
}
