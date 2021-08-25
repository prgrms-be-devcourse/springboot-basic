package org.programmers.kdt.voucher.service;

import org.programmers.kdt.voucher.Voucher;
import org.programmers.kdt.voucher.VoucherType;
import org.programmers.kdt.voucher.factory.FixedAmountVoucherFactory;
import org.programmers.kdt.voucher.factory.PercentDiscountVoucherFactory;
import org.programmers.kdt.voucher.factory.VoucherFactory;
import org.programmers.kdt.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private VoucherFactory voucherFactory;

    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    @Autowired
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> {
                    logger.error("{} -> Fail : getVoucher for ID {}", this.getClass(), voucherId);
                    return new RuntimeException(MessageFormat.format("Cannot find a voucher for ID : {0}",
                            voucherId));
                });
    }

    public void useVoucher() {
        // TODO : To be implemented later...
    }

    public void deleteVoucher(UUID voucherId) {
        // TODO : To be implemented later...
    }

    public void addVoucher(Voucher voucher) {
        voucherRepository.insert(voucher);
        logger.info("New Voucher has been successfully added : {} {} {}",
                voucher.getClass(), voucher.getVoucherId(), voucher.getDiscount());
    }

    public Voucher createVoucher(VoucherType voucherType, UUID voucherId, long discount) {
        // TODO : 팩토리 구현체를 설정하는 더 좋은 방법 찾기
        switch (voucherType) {
            case FIXED -> voucherFactory = new FixedAmountVoucherFactory();
            case PERCENT -> voucherFactory = new PercentDiscountVoucherFactory();
        }
        Voucher voucher = voucherFactory.createVoucher(voucherId, discount);
        addVoucher(voucher);
        return voucher;
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }
}
