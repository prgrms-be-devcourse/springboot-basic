package prgms.vouchermanagementapp.voucher;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.domain.Voucher;
import prgms.vouchermanagementapp.domain.model.Amount;
import prgms.vouchermanagementapp.domain.model.Ratio;
import prgms.vouchermanagementapp.storage.Vouchers;
import prgms.vouchermanagementapp.storage.model.VoucherRecord;

@Component
public class VoucherManager {

    private final Vouchers vouchers;

    public VoucherManager(@Qualifier("fileVouchers") Vouchers vouchers) {
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
