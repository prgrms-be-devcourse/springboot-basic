package org.prgrms.weeklymission.voucher.service;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.weeklymission.console.Console;
import org.prgrms.weeklymission.voucher.domain.FixedAmountVoucher;
import org.prgrms.weeklymission.voucher.domain.PercentDiscountVoucher;
import org.prgrms.weeklymission.voucher.domain.Voucher;
import org.prgrms.weeklymission.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.prgrms.weeklymission.utils.Error.*;

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

    @Override
    public Voucher findVoucherById(UUID voucherId) {
        return repository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException(NO_VOUCHER));
    }

    public Voucher createVoucher(String option, String discount) throws RuntimeException {
        checkOptionValueAndDoAction(option, discount);

        repository.save(voucher);

        return voucher;
    }

    @Override
    public void printAllVouchers() throws RuntimeException {
        StringBuilder sb = new StringBuilder();
        searchAllVouchers().forEach(v -> sb.append(v.toString()).append("\n"));
        console.printData(sb.toString());
    }

    @Override
    public void printCreateVoucher() throws RuntimeException {
        console.createVoucherMessage();
        String[] values = console.inputForCreateVoucher();
        createVoucher(values[0], values[1]);
        console.saveSuccessMessage();
    }

    @Override
    public void clearRepo() {
        repository.clear();
    }

    @Override
    public List<Voucher> searchAllVouchers() throws RuntimeException {
        return checkVouchersAndReturn();
    }

    private void checkOptionValueAndDoAction(String option, String discount) throws RuntimeException {
        long longDiscount = validateDiscountValue(discount);

        if (option.equals("1")) {
            createFixedVoucher(longDiscount);
        } else if (option.equals("2")) {
            createPercentVoucher(longDiscount);
        } else {
            log.error("Exception Option: {}", option);
            throw new RuntimeException(OPTION_ERROR);
        }
    }

    private long validateDiscountValue(String discount) throws NumberFormatException {
        try {
            return Long.parseLong(discount);
        } catch (NumberFormatException e) {
            log.error("Long type parsing error: {}", discount);
            throw new NumberFormatException(PARSING_ERROR);
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
            throw new RuntimeException(NO_VOUCHER);
        }

        return vouchers;
    }
}