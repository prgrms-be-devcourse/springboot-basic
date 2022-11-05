package org.prgrms.kdt;

import org.prgrms.kdt.voucher.*;
import org.springframework.stereotype.Component;

@Component
public class CommandExecutor {

    private final VoucherManager voucherManager;

    public CommandExecutor(VoucherManager voucherManager) {
        this.voucherManager = voucherManager;
    }

    public void create(VoucherMetaData voucherMetaData) {
        Voucher voucher = toVoucher(voucherMetaData);

        voucher.validateAmount();

        voucherManager.save(voucher);
    }

    private Voucher toVoucher(VoucherMetaData voucherMetaData) {
        return new Voucher(
                toVoucherType(voucherMetaData.getType()),
                toVoucherAmount(voucherMetaData.getAmount())
        );
    }

    private VoucherAmount toVoucherAmount(String amount) {
        return new VoucherAmount(amount);
    }

    private VoucherType toVoucherType(String type) {
        return VoucherType.valueOf(type);
    }
}
