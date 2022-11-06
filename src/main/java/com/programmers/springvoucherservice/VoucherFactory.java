package com.programmers.springvoucherservice;

import com.programmers.springvoucherservice.domain.voucher.FixedAmountVoucher;
import com.programmers.springvoucherservice.domain.voucher.PercentDiscountVoucher;
import com.programmers.springvoucherservice.domain.voucher.Voucher;
import com.programmers.springvoucherservice.domain.voucher.VoucherList;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherFactory {
    public static Voucher createVoucher(UUID id, VoucherList type, long value) {
        if (type == VoucherList.FixedAmount) {
            return new FixedAmountVoucher(value, id);
        }

        return new PercentDiscountVoucher(id, value);
    }

    public static Voucher createVoucher(VoucherList type, long value) {
        return VoucherFactory.createVoucher(UUID.randomUUID(), type, value);
    }
}
