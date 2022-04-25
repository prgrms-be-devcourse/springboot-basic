package org.prgms.kdt.application;


import lombok.RequiredArgsConstructor;
import org.prgms.kdt.application.customer.domain.Customer;
import org.prgms.kdt.application.customer.service.CustomerService;
import org.prgms.kdt.application.io.Input;
import org.prgms.kdt.application.io.Output;
import org.prgms.kdt.application.voucher.domain.Voucher;
import org.prgms.kdt.application.voucher.domain.VoucherType;
import org.prgms.kdt.application.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommandLineApplication implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private boolean exit = true;

    @Override
    public void run() {
        while (exit) {
            output.commandTypeMessage();
            String commandTypeInput = input.typeOptionInput();
            try {
                CommandType commandType = CommandType.findCommandType(commandTypeInput);
//                switch (commandType) {
//                    case EXIT:
//                        exit = false;
//                        output.printExit();
//                        break;
//                    case CREATE:
//                        output.voucherTypeMessage();
//                        String voucherTypeInput = input.typeOptionInput();
//                        VoucherType voucherType = VoucherType.findVoucherType(voucherTypeInput);
//                        voucherService.createVoucher(voucherType);
//                        break;
//                    case LIST:
//                        List<Voucher> voucherList = voucherService.findVouchers();
//                        output.printVoucherList(voucherList);
//                        break;
//                    case BLACKLIST:
//                        List<Customer> blacklist = customerService.findBlacklist();
//                        output.printBlackList(blacklist);
//                    default:
//                        break;
//                }
            } catch (Exception e) {
                output.printError(e);
                logger.error("{}", e);
                continue;
            }
        }
    }

}
