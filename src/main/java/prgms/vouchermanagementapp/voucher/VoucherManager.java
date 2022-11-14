package prgms.vouchermanagementapp.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.model.Amount;
import prgms.vouchermanagementapp.model.Ratio;
import prgms.vouchermanagementapp.voucher.model.Voucher;

import java.util.List;

@Component
public class VoucherManager {

    private static final Logger log = LoggerFactory.getLogger(VoucherManager.class);

    private final MemoryVouchers memoryVouchers;

    @Autowired
    public VoucherManager(MemoryVouchers memoryVouchers) {
        this.memoryVouchers = memoryVouchers;
    }

    public void createVoucher(Amount fixedDiscountAmount) {
        Voucher fixedAmountVoucher = VoucherCreationFactory.createVoucher(fixedDiscountAmount);
        memoryVouchers.store(fixedAmountVoucher);
        log.info("New FixedAmountVoucher created. amount: {}", fixedDiscountAmount.getAmount());
    }

    public void createVoucher(Ratio fixedDiscountRatio) {
        Voucher percentDiscountVoucher = VoucherCreationFactory.createVoucher(fixedDiscountRatio);
        memoryVouchers.store(percentDiscountVoucher);
        log.info("New PercentDiscountVoucher created. percent: {}%", fixedDiscountRatio.getRatio());
    }

    public List<Voucher> findVouchers() {
        return memoryVouchers.getVouchers();
    }
}
