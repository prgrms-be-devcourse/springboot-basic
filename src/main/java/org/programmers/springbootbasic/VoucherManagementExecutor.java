package org.programmers.springbootbasic;

import lombok.AllArgsConstructor;
import org.programmers.springbootbasic.data.VoucherMainMenuCommand;
import org.programmers.springbootbasic.domain.Customer;
import org.programmers.springbootbasic.domain.Voucher;
import org.programmers.springbootbasic.dto.VoucherInputDto;
import org.programmers.springbootbasic.io.Input;
import org.programmers.springbootbasic.io.Output;
import org.programmers.springbootbasic.service.CustomerService;
import org.programmers.springbootbasic.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class VoucherManagementExecutor {

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private static Logger logger = LoggerFactory.getLogger(VoucherManagementExecutor.class);


    public void run() {
        boolean running = true;
        while (running) {
            output.printMainMenu();
            try {
                VoucherMainMenuCommand menuInput = input.getVoucherMainMenuInput();
                switch (menuInput) {
                    case CREATE -> {
                        createVoucher();
                    }
                    case LIST -> {
                        List<Voucher> vouchers = voucherService.collectVouchers();
                        output.printVouchers(vouchers);
                    }
                    case BLACKLIST -> {
                        List<Customer> blackList = customerService.collectBlacklists();
                        output.printBlacklist(blackList);
                    }
                    case EXIT -> {
                        running = false;
                    }
                }
            } catch (RuntimeException e) {
                logger.error(e.getMessage() + e);
            }
        }
    }

    private void createVoucher() {
        boolean continueJob = true;
        while (continueJob) {
            try {
                VoucherInputDto voucherInputDto = input.getVoucherCreateMenuInput();
                voucherService.createVoucher(voucherInputDto);
            } catch (RuntimeException e) {
                logger.error(e.getMessage());
                continue;
            }
            continueJob = false;
        }
    }
}
