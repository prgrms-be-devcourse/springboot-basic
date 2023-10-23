package org.prgrms.kdtspringdemo.voucher.service;

import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherTypeFunction;
import org.prgrms.kdtspringdemo.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherTypeFunction getVoucherTypeFunction(String type) {
        return VoucherTypeFunction.findByCode(type);
    }

    public Voucher createVoucher(VoucherTypeFunction voucherType, UUID voucherId, long amount) {
        Voucher voucher = voucherType.create(voucherId, amount);
        voucherRepository.insert(voucher);
        return voucher;
    }

    public Map<UUID, Voucher> getVoucherList() {
        return voucherRepository.getAllVouchers().get();
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> {
                    logger.error(MessageFormat.format("Can not find a voucher for {0}", voucherId));
                    return new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId));
                });
    }

    public void endVoucherService() {
        System.exit(0);
    }
}
