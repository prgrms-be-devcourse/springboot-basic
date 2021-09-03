package org.programmers.kdt.voucher.service;

import org.programmers.kdt.voucher.Voucher;
import org.programmers.kdt.voucher.VoucherStatus;
import org.programmers.kdt.voucher.VoucherType;
import org.programmers.kdt.voucher.factory.VoucherFactory;
import org.programmers.kdt.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;
    private VoucherFactory voucherFactory;

    private static final Logger logger = LoggerFactory.getLogger(VoucherServiceImpl.class);

    public VoucherServiceImpl(VoucherRepository voucherRepository, VoucherFactory voucherFactory) {
        this.voucherRepository = voucherRepository;
        this.voucherFactory = voucherFactory;
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
        Voucher voucher;
        try {
            voucher = voucherFactory.createVoucher(voucherType, voucherId, discount);
        } catch (RuntimeException e) {
            logger.error("Failed to created a voucher : {}", e.getMessage());
            throw e;
        }
        addVoucher(voucher);
        return voucher;
    }

    @Override
    public void removeVoucher(UUID voucherid) {
        voucherRepository.deleteVoucher(voucherid);
    }

    @Override
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    @Override
    public VoucherStatus getVoucherStatus(Voucher voucher) {
        return voucher.getStatus();
    }

    @Override
    public String getPrintFormat(Voucher voucher) {
        return MessageFormat.format("<< {0} Discount Voucher >>\nID : {1}\nDiscount: {2}",
                voucher.getVoucherType(), voucher.getVoucherId(),
                voucher.getVoucherType() == VoucherType.FIXED ? "$" + voucher.getDiscount() : voucher.getDiscount() + "%");
    }
}
