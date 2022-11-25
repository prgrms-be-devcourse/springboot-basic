package org.programmers.springbootbasic;

import lombok.AllArgsConstructor;
import org.programmers.springbootbasic.data.VoucherMainMenuCommand;
import org.programmers.springbootbasic.domain.voucher.model.Voucher;
import org.programmers.springbootbasic.domain.customer.dto.CustomerOutputDto;
import org.programmers.springbootbasic.domain.voucher.dto.VoucherInputDto;
import org.programmers.springbootbasic.domain.console.Input;
import org.programmers.springbootbasic.domain.console.Output;
import org.programmers.springbootbasic.domain.customer.service.CustomerService;
import org.programmers.springbootbasic.domain.voucher.service.VoucherService;
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
                        List<Voucher> vouchers = voucherService.findAll();
                        output.printVouchers(vouchers);
                    }
                    case BLACKLIST -> {
                        List<CustomerOutputDto> blackList = customerService.collectBlacklists();
                        output.printBlacklist(blackList);
                    }
                    case EXIT -> {
                        running = false;
                    }
                }
            } catch (RuntimeException e) {
                logger.error(e.getMessage(), e);
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
