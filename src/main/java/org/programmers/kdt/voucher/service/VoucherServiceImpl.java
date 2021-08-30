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
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Profile("dev")
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;
    private VoucherFactory voucherFactory;

    private static final Logger logger = LoggerFactory.getLogger(VoucherServiceImpl.class);

    @Autowired
    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public Optional<Voucher> getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }

    @Override
    public void useVoucher(Voucher voucher) {
        // TODO : To be implemented later...
    }

    private void addVoucher(Voucher voucher) {
        voucherRepository.insert(voucher);
        logger.info("New Voucher has been successfully added to repository : {} {} {}",
                voucher.getClass(), voucher.getVoucherId(), voucher.getDiscount());
    }

    @Override
    public Voucher createVoucher(VoucherType voucherType, UUID voucherId, long discount) {
        // TODO : 팩토리 구현체를 설정하는 더 좋은 방법 찾기
        switch (voucherType) {
            case FIXED -> voucherFactory = new FixedAmountVoucherFactory();
            case PERCENT -> voucherFactory = new PercentDiscountVoucherFactory();
        }
        Voucher voucher;
        try {
            voucher = voucherFactory.createVoucher(voucherId, discount);
        } catch (RuntimeException e) {
            logger.error("Failed to created a voucher : {}", e.getMessage());
            throw e;
        }
        addVoucher(voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> removeVoucher(UUID voucherid) {
        return voucherRepository.deleteVoucher(voucherid);
    }

    @Override
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    @Override
    public String getPrintFormat(Voucher voucher) {
        return MessageFormat.format("<< {0} Discount Voucher >>\nID : {1}\nDiscount: {2}",
                voucher.getVoucherType(), voucher.getVoucherId(),
                voucher.getVoucherType() == VoucherType.FIXED ? "$" + voucher.getDiscount() : voucher.getDiscount() + "%");
    }
}
