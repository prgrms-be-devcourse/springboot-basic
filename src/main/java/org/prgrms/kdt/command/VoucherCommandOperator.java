package org.prgrms.kdt.command;

import java.util.Map;
import java.util.UUID;
import org.prgrms.kdt.voucher.VoucherData;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherService;
import org.prgrms.kdt.voucher.VoucherType;
import org.springframework.stereotype.Component;

/**
 * Created by yhh1056
 * Date: 2021/08/23 Time: 3:24 오후
 */

@Component
public class VoucherCommandOperator implements CommandOperator<UUID, Voucher, VoucherData> {
    private static final int STATUS = 0;

    private final VoucherService voucherService;

    public VoucherCommandOperator(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public Voucher create(VoucherData voucherData) {
        Voucher voucher = VoucherType.convertCommandToVoucher(voucherData.getVoucherNumber(), voucherData.getDiscount());
        return voucherService.createVoucher(voucher);
    }

    public Map<UUID, Voucher> getAll() {
        return voucherService.getAllVoucher();
    }

    @Override
    public void exit() {
        System.exit(STATUS);
    }
}
