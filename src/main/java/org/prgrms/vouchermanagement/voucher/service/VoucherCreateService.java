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

    private final VoucherRepository voucherRepository;
    private static final Logger logger = getLogger(VoucherCreateService.class);

    @Autowired
    public VoucherCreateService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(String voucherTypeInput, int discountValue) {

        logger.info("바우처 생성 (타입 : {}, 할인 : {})", voucherTypeInput, discountValue);

        Voucher voucher = VoucherType.createVoucher(UUID.randomUUID(), voucherTypeInput, discountValue);
        voucherRepository.save(voucher);
        return voucher;
    }
}
