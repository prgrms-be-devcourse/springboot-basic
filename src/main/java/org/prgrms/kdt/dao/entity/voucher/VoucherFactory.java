package org.prgrms.kdt.dao.entity.voucher;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherFactory {

    public Voucher createVoucher(UUID voucherId, String discountValue, String voucherType) {
        return new Voucher(voucherId, discountValue, voucherType);
    }
}
