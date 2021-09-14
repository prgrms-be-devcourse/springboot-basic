package org.prgrms.kdt.command.service;

import org.prgrms.kdt.command.domain.Command;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.customer.domain.BannedCustomer;
import org.prgrms.kdt.customer.service.CustomerService;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BlackListCommandService implements Command {

    private final Output output;
    private final CustomerService customerService;

    public BlackListCommandService(Output output, CustomerService customerService) {
        this.output = output;
        this.customerService = customerService;
    }

    @Override
    public boolean execute() {
        List<BannedCustomer> blackList = customerService.getBlackList();
        output.printBlackList(blackList);
        return true;
    }
}
