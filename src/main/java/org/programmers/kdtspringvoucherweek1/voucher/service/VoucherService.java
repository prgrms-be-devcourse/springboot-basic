package org.programmers.kdtspringvoucherweek1.voucher.service;

import org.programmers.kdtspringvoucherweek1.blacklist.repository.BlacklistRepository;
import org.programmers.kdtspringvoucherweek1.io.Input;
import org.programmers.kdtspringvoucherweek1.io.Output;
import org.programmers.kdtspringvoucherweek1.log.LogLevel;
import org.programmers.kdtspringvoucherweek1.voucher.FixedAmountVoucher;
import org.programmers.kdtspringvoucherweek1.voucher.PercentDiscountVoucher;
import org.programmers.kdtspringvoucherweek1.voucher.Voucher;
import org.programmers.kdtspringvoucherweek1.voucher.repository.VoucherRepository;
import org.programmers.kdtspringvoucherweek1.error.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherService {
    private final Input input;
    private final Output output;
    private final VoucherRepository voucherRepository;
    private final BlacklistRepository customerRepository;

    @Autowired
    public VoucherService(Input input, Output output, VoucherRepository voucherRepository, BlacklistRepository customerRepository) {
        this.input = input;
        this.output = output;
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
    }

    public Voucher createVoucher() {
        UUID voucherId = UUID.randomUUID();
        while (true) {
            output.createMenu();
            switch (input.nextLine()) {
                case "1" -> {
                    long amount = 0;
                    while (amount <= 0) {
                        amount = input.discount("amount");
                    }
                    return new FixedAmountVoucher(voucherId, amount); }
                case "2" -> {
                    long percent = 0;
                    while (percent <= 0) {
                        percent = input.discount("percent");
                    }
                    return new PercentDiscountVoucher(voucherId, percent);
                }
                default -> {
                    output.msg(Error.INVALID_COMMAND);
                    output.logging(LogLevel.WARN, Error.INVALID_COMMAND);
                }
            }
        }
    }

    public void readList() {
        while (true) {
            output.listMenu();
            switch (input.nextLine()) {
                case "1" -> {
                    voucherRepository.findByIdAll();
                    return ;
                }
                case "2" -> {
                    customerRepository.findByIdAll();
                    return ;
                }
                default -> {
                    output.msg(Error.INVALID_COMMAND);
                    output.logging(LogLevel.WARN, Error.INVALID_COMMAND);
                }
            }
        }
    }
}
