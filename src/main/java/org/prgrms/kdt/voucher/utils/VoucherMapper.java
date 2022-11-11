package org.prgrms.kdt.voucher.utils;

import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherAmount;
import org.prgrms.kdt.voucher.VoucherType;
import org.springframework.stereotype.Component;

@Component
public class VoucherMapper {

    public Voucher fromMetadata(String type, String amount) {
        return Voucher.newInstance(toVoucherType(type), toVoucherAmount(amount));
    }

    private VoucherAmount toVoucherAmount(String amount) {
        return new VoucherAmount(amount);
    }

    private VoucherType toVoucherType(String type) {
        return VoucherType.of(type);
    }
}
