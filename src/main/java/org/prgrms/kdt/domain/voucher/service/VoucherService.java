package org.prgrms.kdt.domain.voucher.service;

import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.prgrms.kdt.domain.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public UUID saveVoucher(VoucherType voucherType, long discount) {
        Voucher voucher = createVoucher(voucherType, discount);
        UUID voucherId = voucherRepository.save(voucher);
        logger.info("save Voucher: {}", voucher);
        return voucherId;
    }

    @Transactional
    public Optional<Voucher> getVoucherById(UUID voucherId) {
        Optional<Voucher> voucher = voucherRepository.findById(voucherId);
        logger.info("find Voucher By Id {}", voucher);
        return voucher;
    }

    @Transactional
    public List<Voucher> getAllVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        logger.info("find All Voucher size: {}", vouchers.size());
        return vouchers;
    }

    private Voucher createVoucher(VoucherType voucherType, long discount) {
        UUID voucherId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        return new Voucher(voucherId, voucherType, discount, now, now);
    }
}
