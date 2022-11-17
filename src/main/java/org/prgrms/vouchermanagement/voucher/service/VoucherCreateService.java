package org.prgrms.vouchermanagement.voucher.service;

import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.domain.VoucherType;
import org.prgrms.vouchermanagement.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class VoucherCreateService {

    private static final Logger logger = getLogger(VoucherCreateService.class);
    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherCreateService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(String voucherTypeInput, int discountValue) {
        Voucher voucher = VoucherType.createVoucher(UUID.randomUUID(), voucherTypeInput, discountValue);
        voucherRepository.save(voucher);
        return voucher;
    }
}
