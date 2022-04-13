package org.prgrms.weeklymission.voucher.service;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.weeklymission.console.Console;
import org.prgrms.weeklymission.voucher.domain.FixedAmountVoucher;
import org.prgrms.weeklymission.voucher.domain.PercentDiscountVoucher;
import org.prgrms.weeklymission.voucher.domain.Voucher;
import org.prgrms.weeklymission.voucher.domain.VoucherType;
import org.prgrms.weeklymission.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.prgrms.weeklymission.utils.ErrorMessage.*;

@Service
@Slf4j
public class ConsoleVoucherService implements VoucherService {
    private final VoucherRepository repository;
    private final Console console;
    private Voucher voucher;

    @Autowired
    public ConsoleVoucherService(VoucherRepository repository, Console console) {
        this.repository = repository;
        this.console = console;
    }

    public Voucher createVoucher(String option, String discount) throws RuntimeException {
        checkOptionValueAndDoAction(option, discount);

        repository.save(voucher);

        return voucher;
    }

    @Override
    public Voucher findVoucherById(UUID voucherId) {
        return repository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException(NO_VOUCHER.getMessage()));
    }

    @Override
    public List<Voucher> searchAllVouchers() throws RuntimeException {
        return checkVouchersAndReturn();
    }

    @Override
    public void printCreateVoucher() throws RuntimeException {
        console.printVoucherOption();
        String command = console.takeInput();
    }

    @Override
    public void printAllVouchers() throws RuntimeException {
        console.printData(checkVouchersAndReturn());

        createVoucher();
    }

    @Override
    public void clearRepo() {
        repository.clear();
    }

//    private void validateOption(String option) {
//        try {
//            VoucherType type = VoucherType.valueOf(option);
//        } catch (IllegalAr)
//
//    }

    private void checkOptionValueAndDoAction(String option, String discount) throws RuntimeException {
        long longDiscount = validateDiscountValue(discount);

        option = option.toUpperCase();
        switch (VoucherType.valueOf(option)) {
            case FIXED:
                createFixedVoucher(longDiscount);
            case PERCENT:
                createPercentVoucher(longDiscount);
            default:
                throw new RuntimeException(OPTION_ERROR.getMessage());
        }
    }

    private long validateDiscountValue(String discount) throws NumberFormatException {
        try {
            return Long.parseLong(discount);
        } catch (NumberFormatException e) {
            log.error("Long type parsing error: {}", discount);

            throw new NumberFormatException(PARSING_ERROR.getMessage());
        }
    }

    private void createFixedVoucher(long amount) {
        this.voucher = new FixedAmountVoucher(randomUUID(), amount);
    }

    private void createPercentVoucher(long percent) {
        this.voucher = new PercentDiscountVoucher(randomUUID(), percent);
    }

    private List<Voucher> checkVouchersAndReturn() throws RuntimeException {
        List<Voucher> vouchers = repository.findAll();
        if(vouchers.isEmpty()) {
            log.error("repository is empty");
            throw new RuntimeException(NO_VOUCHER.getMessage());
        }

        return vouchers;
    }
}