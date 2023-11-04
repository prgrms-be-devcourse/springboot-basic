package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.Dto.FixedAmountVoucherDto;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.prgrms.kdt.voucher.VoucherMessage.EXCEPTION_FIXED_AMOUNT_MINUS;
import static org.prgrms.kdt.voucher.VoucherMessage.EXCEPTION_FIXED_AMOUNT_OVER;

@Service
public class FixedAmountVoucherService {

    private final VoucherRepository voucherRepository;
    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucherService.class);

    public FixedAmountVoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createFixedAmountVoucher(int amount) {
        if (amount <= 0) {
            String errorMessage = EXCEPTION_FIXED_AMOUNT_MINUS.getMessage();
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        if (amount >= 100_000) {
            String errorMessage = EXCEPTION_FIXED_AMOUNT_OVER.getMessage();
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        var fixedAmountVoucherDto = new FixedAmountVoucherDto(UUID.randomUUID(), amount);
        createVoucher(fixedAmountVoucherDto);
    }

    private void createVoucher(FixedAmountVoucherDto fixedAmountVoucherDto) {
        var voucher = new FixedAmountVoucher(fixedAmountVoucherDto.getVoucherId(), fixedAmountVoucherDto.getAmount());
        voucherRepository.save(voucher);
    }
}
