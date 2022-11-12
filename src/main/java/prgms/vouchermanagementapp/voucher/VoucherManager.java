package prgms.vouchermanagementapp.voucher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.model.Amount;
import prgms.vouchermanagementapp.model.Ratio;
import prgms.vouchermanagementapp.voucher.model.Voucher;
import prgms.vouchermanagementapp.voucher.warehouse.VoucherWarehouse;
import prgms.vouchermanagementapp.voucher.warehouse.model.VoucherRecord;

@Component
public class VoucherManager {

    private final VoucherWarehouse voucherWarehouse;

    @Autowired
    public VoucherManager(VoucherWarehouse voucherWarehouse) {
        this.voucherWarehouse = voucherWarehouse;
    }

    public void createVoucher(Amount fixedDiscountAmount) {
        Voucher fixedAmountVoucher = VoucherCreationFactory.createVoucher(fixedDiscountAmount);
        voucherWarehouse.store(fixedAmountVoucher);
    }

    public void createVoucher(Ratio fixedDiscountRatio) {
        Voucher percentDiscountVoucher = VoucherCreationFactory.createVoucher(fixedDiscountRatio);
        voucherWarehouse.store(percentDiscountVoucher);
    }

    public VoucherRecord findAllVouchers() {
        return voucherWarehouse.getVoucherRecord();
    }
}
