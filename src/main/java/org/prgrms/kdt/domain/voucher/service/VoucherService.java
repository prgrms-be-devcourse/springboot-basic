package org.prgrms.kdt.domain.voucher.service;

import org.prgrms.kdt.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.model.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.repository.FileVoucherRepository;
import org.prgrms.kdt.domain.voucher.types.VoucherType;
import org.prgrms.kdt.domain.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void save(VoucherType voucherType, long discount) {
        UUID voucherId = UUID.randomUUID();
        if(voucherType == VoucherType.FIXED_AMOUNT){
            Voucher voucher = new FixedAmountVoucher(voucherId, discount);
            voucherRepository.save(voucher);
            logger.info("save Fixed Amount Voucher: {}", voucher);
        } else if(voucherType == VoucherType.PERCENT_DISCOUNT){
            Voucher voucher = new PercentDiscountVoucher(voucherId, (int) discount);
            voucherRepository.save(voucher);
            logger.info("save Percent Discount Voucher: {}", voucher);
        }
    }

    public List<Voucher> findAll() {
        List<Voucher> vouchers = voucherRepository.findAll();
        logger.info("find All Voucher {}", vouchers);
        return vouchers;
    }


}
