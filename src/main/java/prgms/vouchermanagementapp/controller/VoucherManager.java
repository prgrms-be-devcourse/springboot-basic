package prgms.vouchermanagementapp.controller;

import prgms.vouchermanagementapp.io.IOManager;
import prgms.vouchermanagementapp.model.FixedAmount;
import prgms.vouchermanagementapp.voucher.VoucherType;

public class VoucherManager {

    private final IOManager ioManager;

    public VoucherManager(IOManager ioManager) {
        this.ioManager = ioManager;
    }

    public void createVoucher() {
        String voucherTypeIndex = ioManager.askVoucherTypeIndex();
        VoucherType voucherType = VoucherType.of(voucherTypeIndex);

        if (voucherType.is(VoucherType.FixedAmountVoucher)) {
            FixedAmount fixedAmount = ioManager.askFixedAmount();
//            voucherCreator.createFixedAmountVoucher();
//            MemoryVouchers.store(FixedAmountVoucher);
            return;
        }

//        if (voucherType.is(VoucherType.PercentDiscountVoucher)) {
//            ioManager.askDiscountPercent();
//            voucherCreator.create(PercentDiscountVoucher());
//            MemoryVouchers.store()
//            return;
//        }
    }
}
