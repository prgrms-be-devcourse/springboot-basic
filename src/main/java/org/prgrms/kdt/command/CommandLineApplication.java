package org.prgrms.kdt.command;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.customer.service.CustomerService;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandLineApplication implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    @Override
    public void run() {
        boolean programRunning = true;
        do {
            Output.commandChooseMessage();

            final String commandInput = Input.input();
            switch (commandInput) {
                case "exit":
                    programRunning = false;
                    break;

                case "create":
                    Output.voucherChooseMessage();
                    final String voucherType = CommandCreate.createVoucherType();
                    final long voucherDiscountValue = CommandCreate.createVoucherDiscountValue(voucherType);
                    VoucherService.createVoucher(voucherType, voucherDiscountValue);
                    break;

                case "list":
                    final List<Voucher> allVoucherList = VoucherService.getAllVoucher();
                    for (final Voucher voucher : allVoucherList) {
                        System.out.println(voucher);
                    }
                    break;

                case "blacklist":
                    final List<Customer> blacklist = CustomerService.findAllBlacklist();
                    for (int i = 0; i < blacklist.size(); i++) {
                        System.out.println(blacklist.get(i));
                    }
                    break;

                default:
                    Output.inputTypeErrorMessage(commandInput);
                    break;
            }
        } while (programRunning);
    }
}
