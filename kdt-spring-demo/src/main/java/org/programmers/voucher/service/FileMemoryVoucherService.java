package org.programmers.voucher.service;

import org.programmers.voucher.model.FixedDiscountVoucher;
import org.programmers.voucher.model.PercentDiscountVoucher;
import org.programmers.voucher.model.Voucher;
import org.programmers.voucher.model.VoucherType;
import org.programmers.voucher.repository.FileMemoryVoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
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

    public List<Voucher> getVoucherList() {
        return voucherRepository.findAllVouchers();
    }

    public void validateVoucher(UUID voucherId, VoucherType voucherType, long value) {
        switch (voucherType) {
            case FIXED -> {
                if (value < LOWER_LIMIT)
                    throw new IllegalArgumentException(MessageFormat.format("Wrong number -> {0}", value));
            }
            case PERCENT -> {
                if (value <= LOWER_LIMIT || value >= UPPER_LIMIT)
                    throw new IllegalArgumentException(MessageFormat.format("Wrong number -> {0}", value));
            }
            default -> logger.info("Wrong Type -> {} ", voucherType);
        }
    }

    public void createVoucher(UUID voucherId, VoucherType voucherType, long value) {
        switch (voucherType) {
            case FIXED -> voucherRepository.save(new FixedDiscountVoucher(voucherId, value));

            case PERCENT -> voucherRepository.save(new PercentDiscountVoucher(voucherId, value));

            default -> logger.info("Wrong Type -> {} ", voucherType);
        }
    }

    public void memoryShowVoucherList() {
        List<Voucher> allVouchers = voucherRepository.findAllVouchers();
        for (Voucher voucher : allVouchers) {
            System.out.println(voucher.toString());
        }
    }
}
