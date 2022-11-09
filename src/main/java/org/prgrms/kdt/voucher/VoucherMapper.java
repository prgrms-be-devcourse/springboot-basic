package org.prgrms.kdt.voucher;

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
