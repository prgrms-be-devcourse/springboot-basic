package org.prgrms.kdt.voucher;

import org.springframework.stereotype.Component;

@Component
public class VoucherMapper {

    public Voucher mapFrom(VoucherInfo voucherInfo) {
        return new Voucher();
    }
}
