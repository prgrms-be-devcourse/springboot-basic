package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.Dto.PercentDiscountVoucherDto;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.prgrms.kdt.voucher.VoucherMessage.EXCEPTION_PERCENT_MINUS;
import static org.prgrms.kdt.voucher.VoucherMessage.EXCEPTION_PERCENT_OVER;

@Service
public class PercentDiscountVoucherService {

    private final VoucherRepository voucherRepository;
    private static final Logger logger = LoggerFactory.getLogger(PercentDiscountVoucherService.class);

    public PercentDiscountVoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createPercentDiscountVoucher(int percent) {
        if (percent <= 0) {
            String errorMessage = EXCEPTION_PERCENT_MINUS.getMessage();
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        if (percent >= 100) {
            String errorMessage = EXCEPTION_PERCENT_OVER.getMessage();
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        var percentDiscountVoucherDto = new PercentDiscountVoucherDto(UUID.randomUUID(), percent);
        createVoucher(percentDiscountVoucherDto);
    }

    private void createVoucher(PercentDiscountVoucherDto percentDiscountVoucherDto) {
        var voucher = new PercentDiscountVoucher(percentDiscountVoucherDto.getVoucherId(), percentDiscountVoucherDto.getPercent());
        voucherRepository.save(voucher);
    }
}
