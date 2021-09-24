package org.prgrms.kdt.command.service;

import org.prgrms.kdt.command.domain.Command;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.customer.domain.BannedCustomer;
import org.prgrms.kdt.customer.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class BlackListCommandService implements Command {
    private static final Logger logger = LoggerFactory.getLogger(BlackListCommandService.class);

    private final Output output;
    private final CustomerService customerService;

    public BlackListCommandService(Output output, CustomerService customerService) {
        this.output = output;
        this.customerService = customerService;
    }

    @Override
    public boolean execute() {
        List<BannedCustomer> blackList = customerService.getBlackList();
        Stream<BannedCustomer> blackListStream = blackList.stream();

        logger.info("Execute BlackListCommand : {}",
                blackListStream
                        .map(BannedCustomer::toString)
                        .collect(Collectors.joining()));

        output.printBlackList(blackList);

        return true;
    }
}
