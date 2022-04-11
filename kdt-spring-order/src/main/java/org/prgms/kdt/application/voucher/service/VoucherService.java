package org.prgms.kdt.application.voucher.service;

import lombok.RequiredArgsConstructor;
import org.prgms.kdt.application.voucher.domain.FixedAmountVoucher;
import org.prgms.kdt.application.voucher.domain.PercentDiscountVoucher;
import org.prgms.kdt.application.voucher.domain.Voucher;
import org.prgms.kdt.application.voucher.domain.VoucherType;
import org.prgms.kdt.application.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;


    public void createVoucher (VoucherType voucherType) {
        Voucher voucher = null;
        switch (voucherType) {
            case FIXED_AMOUNT:
                voucher = new FixedAmountVoucher(UUID.randomUUID());
                logger.info("create FixedAmountVoucher {}", voucher);
                break;
            case PERCENT_DISCOUNT:
                voucher = new PercentDiscountVoucher(UUID.randomUUID());
                logger.info("create PercentDiscountVoucher {}", voucher);
                break;
        }
        voucherRepository.save(voucher);
    }

    public List<Voucher> findVouchers () {
        return voucherRepository.findAll();
    }
}
