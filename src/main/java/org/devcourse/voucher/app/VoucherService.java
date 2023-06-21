package org.devcourse.voucher.app;

import org.devcourse.voucher.domain.voucher.Voucher;
import org.devcourse.voucher.domain.voucher.VoucherType;

import java.util.List;

public interface VoucherService {

    void save(VoucherType voucherType, int amount);

    List<Voucher> findAll();
}
