package org.prgrms.kdt;

import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherManager;
import org.prgrms.kdt.voucher.VoucherMetaData;
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
        return null;
    }
}
