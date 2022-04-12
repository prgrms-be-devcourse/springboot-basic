package org.prgrms.kdt.domain.voucher.service;

import org.prgrms.kdt.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.model.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.types.VoucherType;
import org.prgrms.kdt.domain.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

    public void save(VoucherType voucherType, long discount) {
        Voucher voucher = createVoucher(voucherType, discount);
        voucherRepository.save(voucher);
        logger.info("save Fixed Amount Voucher: {}", voucher);
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
        if(voucherType == VoucherType.FIXED_AMOUNT){
            return new FixedAmountVoucher(voucherId, discount);
        } else {
            return new PercentDiscountVoucher(voucherId, (int) discount);
        }
    }

}
