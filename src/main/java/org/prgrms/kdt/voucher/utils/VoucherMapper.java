package org.prgrms.kdt.voucher.utils;

import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherAmount;
import org.prgrms.kdt.voucher.VoucherMetaData;
import org.prgrms.kdt.voucher.VoucherType;
import org.springframework.stereotype.Component;

@Component
public class VoucherMapper {

    public Voucher MetaDataToVoucher(VoucherMetaData voucherMetaData) {
        return new Voucher(
                toVoucherType(voucherMetaData.getType()),
                toVoucherAmount(voucherMetaData.getAmount())
        );
    }

    private VoucherAmount toVoucherAmount(String amount) {
        return new VoucherAmount(amount);
    }

    private VoucherType toVoucherType(String type) {
        return VoucherType.findVoucherType(type);
    }
}
