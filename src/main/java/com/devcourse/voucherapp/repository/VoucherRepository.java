package com.devcourse.voucherapp.repository;

import com.devcourse.voucherapp.entity.voucher.Voucher;
import java.util.Collection;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    Collection<Voucher> findAllVouchers();
}
