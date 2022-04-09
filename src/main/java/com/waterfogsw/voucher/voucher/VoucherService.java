package com.waterfogsw.voucher.voucher;

import java.util.List;

public interface VoucherService {
    Voucher createVoucher(VoucherType type, Double value);

    void save(Voucher voucher);

    List<Voucher> findAll();
}
