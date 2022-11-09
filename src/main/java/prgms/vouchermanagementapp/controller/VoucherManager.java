package prgms.vouchermanagementapp.controller;

import prgms.vouchermanagementapp.io.IOManager;
import prgms.vouchermanagementapp.model.Amount;
import prgms.vouchermanagementapp.voucher.MemoryVouchers;
import prgms.vouchermanagementapp.voucher.VoucherCreator;
import prgms.vouchermanagementapp.voucher.VoucherType;
import prgms.vouchermanagementapp.voucher.model.FixedAmountVoucher;

public class VoucherManager {

    private final IOManager ioManager;
    private final VoucherCreator voucherCreator;
    private final MemoryVouchers memoryVouchers;

    public VoucherManager(IOManager ioManager, VoucherCreator voucherCreator, MemoryVouchers memoryVouchers) {
        this.ioManager = ioManager;
        this.voucherCreator = voucherCreator;
        this.memoryVouchers = memoryVouchers;
    }

    public void createVoucher() {

        // 바우처를 만들고(입/출력이 다르다)
        // 저장한다
        String voucherTypeIndex = ioManager.askVoucherTypeIndex();
        VoucherType voucherType = VoucherType.of(voucherTypeIndex);

        if (voucherType.is(VoucherType.FixedAmountVoucher)) {
            Amount fixedDiscountAmount = ioManager.askFixedDiscountAmount();
            FixedAmountVoucher fixedAmountVoucher = voucherCreator.createFixedAmountVoucher(fixedDiscountAmount);
            memoryVouchers.store(fixedAmountVoucher);
            return;
        }
    }
}
