package prgms.vouchermanagementapp.voucher;

import prgms.vouchermanagementapp.model.Amount;
import prgms.vouchermanagementapp.model.Ratio;
import prgms.vouchermanagementapp.voucher.model.FixedAmountVoucher;
import prgms.vouchermanagementapp.voucher.model.PercentDiscountVoucher;

public class VoucherManager {

    private final VoucherCreator voucherCreator;
    private final MemoryVouchers memoryVouchers;

    public VoucherManager(VoucherCreator voucherCreator, MemoryVouchers memoryVouchers) {
        this.voucherCreator = voucherCreator;
        this.memoryVouchers = memoryVouchers;
    }

    public void createVoucher(Amount fixedDiscountAmount) {
        FixedAmountVoucher fixedAmountVoucher = voucherCreator.createFixedAmountVoucher(fixedDiscountAmount);
        memoryVouchers.store(fixedAmountVoucher);
    }

    public void createVoucher(Ratio fixedDiscountRatio) {
        PercentDiscountVoucher percentDiscountVoucher = voucherCreator.createPercentDiscountVoucher(fixedDiscountRatio);
        memoryVouchers.store(percentDiscountVoucher);
    }
}
