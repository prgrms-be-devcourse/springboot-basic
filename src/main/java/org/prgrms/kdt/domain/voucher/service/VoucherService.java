package org.prgrms.kdt.domain.voucher.service;

import org.prgrms.kdt.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.model.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.prgrms.kdt.domain.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private final Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public UUID save(VoucherType voucherType, long discount) {
        Voucher voucher = createVoucher(voucherType, discount);
        UUID voucherId = voucherRepository.save(voucher);
        logger.info("save Fixed Amount Voucher: {}", voucher);
        return voucherId;
    }

    public Optional<Voucher> findById(UUID voucherId) {
        Optional<Voucher> voucher = voucherRepository.findById(voucherId);
        logger.info("find Voucher By Id {}", voucher);
        return voucher;
    }

    public List<Voucher> findAll() {
        List<Voucher> vouchers = voucherRepository.findAll();
        logger.info("find All Voucher {}", vouchers);
        return vouchers;
    }

    private Voucher createVoucher(VoucherType voucherType, long discount) {
        UUID voucherId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        if(voucherType == VoucherType.FIXED_AMOUNT){
            return new FixedAmountVoucher(voucherId, discount, now, now);
        } else {
            return new PercentDiscountVoucher(voucherId, discount, now, now);
        }
    }

}
