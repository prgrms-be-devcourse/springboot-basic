package org.programmers.voucher.service;

import org.programmers.voucher.model.FixedDiscountVoucherInterface;
import org.programmers.voucher.model.PercentDiscountVoucherInterface;
import org.programmers.voucher.model.VoucherInterface;
import org.programmers.voucher.model.VoucherType;
import org.programmers.voucher.repository.FileMemoryVoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Profile("dev")
public class FileMemoryVoucherService {

    private static final Logger logger = LoggerFactory.getLogger(FileMemoryVoucherService.class);

    private final FileMemoryVoucherRepository voucherRepository;
    private final int LOWER_LIMIT = 0;
    private final int UPPER_LIMIT = 100;

    public FileMemoryVoucherService(FileMemoryVoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<VoucherInterface> getVoucherList() {
        return voucherRepository.findAllVouchers();
    }

    public void createVoucher(UUID voucherId, VoucherType voucherType, long value) {

        switch (voucherType) {
            case FIXED -> {
                if (value < LOWER_LIMIT) {
                    logger.info("Wrong number -> {} ", value);
                    break;
                }
                voucherRepository.save(new FixedDiscountVoucherInterface(voucherId, value));

            }
            case PERCENT -> {
                if (value <= LOWER_LIMIT || value >= UPPER_LIMIT) {
                    logger.info("Wrong number -> {} ", value);
                    break;
                }
                voucherRepository.save(new PercentDiscountVoucherInterface(voucherId, value));
            }
            default -> logger.info("Wrong Type -> {} ", voucherType);
        }
    }

    public void memoryShowVoucherList() {
        List<VoucherInterface> allVouchers = voucherRepository.findAllVouchers();
        for (VoucherInterface voucher : allVouchers) {
            System.out.println(voucher.toString());
        }
    }

}
