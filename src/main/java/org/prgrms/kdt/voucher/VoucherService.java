package org.prgrms.kdt.voucher;

import org.prgrms.kdt.order.Order;
import org.prgrms.kdt.order.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(@Qualifier("memory") VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Can not find a voucher for " + voucherId));
    }

    public Voucher createFixedAmountVoucher(int fixedAmount) {
        var voucher = new FixedAmountVoucher(UUID.randomUUID(), fixedAmount);
        voucherRepository.insert(voucher);
        return voucher;
    }

    public Voucher createPercentDiscountVoucher(int percentDiscount) {
        var voucher = new PercentDiscountVoucher(UUID.randomUUID(), percentDiscount);
        voucherRepository.insert(voucher);
        return voucher;
    }


    public List<Voucher> getVoucherList() {
        return null;
    }

    public void useVoucher(Voucher voucher) {
    }
}
