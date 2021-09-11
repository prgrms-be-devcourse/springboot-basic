package org.prgrms.kdt.command;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.customer.service.CustomerService;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandLineApplication {
    public void run(final VoucherRepository voucherRepository) {
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
                    for (int i = 0; i < voucherRepository.findAll().size(); i++) {
                        System.out.println(voucherRepository.findAll().get(i));
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
