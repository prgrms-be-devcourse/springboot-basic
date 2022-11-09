package prgms.vouchermanagementapp.controller;

import prgms.vouchermanagementapp.io.IOManager;
import prgms.vouchermanagementapp.model.Amount;
import prgms.vouchermanagementapp.voucher.VoucherCreator;
import prgms.vouchermanagementapp.voucher.VoucherType;
import prgms.vouchermanagementapp.voucher.model.FixedAmountVoucher;

public class VoucherManager {

    private final IOManager ioManager;
    private final VoucherCreator voucherCreator;

    public VoucherManager(IOManager ioManager, VoucherCreator voucherCreator) {
        this.ioManager = ioManager;
        this.voucherCreator = voucherCreator;
    }

    public void createVoucher() {
        String voucherTypeIndex = ioManager.askVoucherTypeIndex();
        VoucherType voucherType = VoucherType.of(voucherTypeIndex);

        if (voucherType.is(VoucherType.FixedAmountVoucher)) {
            Amount fixedDiscountAmount = ioManager.askFixedDiscountAmount();
            FixedAmountVoucher fixedAmountVoucher = voucherCreator.createFixedAmountVoucher(fixedDiscountAmount);
//            MemoryVouchers.store(fixedAmountVoucher);
            return;
        }
    }
}
