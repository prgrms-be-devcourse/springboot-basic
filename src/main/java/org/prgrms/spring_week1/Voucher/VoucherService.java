package org.prgrms.spring_week1.Voucher;

import java.util.List;
import java.util.UUID;
import org.prgrms.spring_week1.Voucher.model.FixedAmountVoucher;
import org.prgrms.spring_week1.Voucher.model.PercentDiscountVoucher;
import org.prgrms.spring_week1.Voucher.model.Voucher;
import org.prgrms.spring_week1.Voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private static final Logger logger = LoggerFactory
        .getLogger(VoucherService.class); // 모든 인스턴스가 공유(static) 변경불가(final)

    private VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }


    public Voucher createFixedVoucher(long amount) {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), amount);
        voucherRepository.insert(voucher);
        return voucher;
    }

    public Voucher createPercentVoucher(long percent) {
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), percent);
        voucherRepository.insert(voucher);
        return voucher;
    }

    public List<String> getAllVoucher() {
        return voucherRepository.getAllVoucher();

    }


}
