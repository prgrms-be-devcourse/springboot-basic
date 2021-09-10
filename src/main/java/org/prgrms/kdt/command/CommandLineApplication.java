package org.prgrms.kdt.command;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.customer.service.CustomerService;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandLineApplication {
    public void run(final VoucherRepository voucherRepository) {
        boolean programRunning = true;
        do {
            Output.commandChoose();

            final String commandInput = Input.input();
            switch (commandInput) {
                case "exit":
                    programRunning = false;
                    break;

                case "create":
                    Output.voucherChoose();
                    voucherRepository.insert(CommandCreate.createVoucherType());
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
                    Output.inputTypeError(commandInput);
                    break;
            }
        } while (programRunning);
    }
}
