package prgms.vouchermanagementapp.voucher;

import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.io.model.Amount;
import prgms.vouchermanagementapp.io.model.Ratio;
import prgms.vouchermanagementapp.storage.Vouchers;
import prgms.vouchermanagementapp.storage.model.VoucherRecord;
import prgms.vouchermanagementapp.voucher.model.Voucher;

@Component
public class VoucherManager {

    private final Vouchers vouchers;

    public VoucherManager(Vouchers vouchers) {
        this.vouchers = vouchers;
    }

    public void createVoucher(Amount fixedDiscountAmount) {
        Voucher fixedAmountVoucher = VoucherCreationFactory.createVoucher(fixedDiscountAmount);
        vouchers.store(fixedAmountVoucher);
    }

    public void createVoucher(Ratio fixedDiscountRatio) {
        Voucher percentDiscountVoucher = VoucherCreationFactory.createVoucher(fixedDiscountRatio);
        vouchers.store(percentDiscountVoucher);
    }

    public VoucherRecord findAllVouchers() {
        return vouchers.getVoucherRecord();
    }
}
