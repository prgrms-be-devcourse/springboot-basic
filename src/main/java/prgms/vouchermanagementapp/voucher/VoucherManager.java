package prgms.vouchermanagementapp.voucher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.model.Amount;
import prgms.vouchermanagementapp.model.Ratio;
import prgms.vouchermanagementapp.voucher.model.Voucher;

import java.util.List;

@Component
public class VoucherManager {

    private final MemoryVouchers memoryVouchers;

    @Autowired
    public VoucherManager(MemoryVouchers memoryVouchers) {
        this.memoryVouchers = memoryVouchers;
    }

    public void createVoucher(Amount fixedDiscountAmount) {
        Voucher fixedAmountVoucher = VoucherCreationFactory.createVoucher(fixedDiscountAmount);
        memoryVouchers.store(fixedAmountVoucher);
    }

    public void createVoucher(Ratio fixedDiscountRatio) {
        Voucher percentDiscountVoucher = VoucherCreationFactory.createVoucher(fixedDiscountRatio);
        memoryVouchers.store(percentDiscountVoucher);
    }

    public List<Voucher> findVouchers() {
        return memoryVouchers.getVouchers();
    }
}
