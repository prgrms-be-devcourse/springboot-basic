package org.prgrms.kdt.command.service.customer;

import org.prgrms.kdt.command.service.CommandService;
import org.prgrms.kdt.customer.BlackList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlackListCommandService implements CommandService {

    private final org.prgrms.kdt.customer.service.BlackListService blackListService;

    public BlackListCommandService(final org.prgrms.kdt.customer.service.BlackListService blackListService) {
        this.blackListService = blackListService;
    }

    @Override
    public void commandRun() {
        final List<BlackList> blacklist = blackListService.findAllBlacklist();
        for (final BlackList blackList : blacklist) {
            System.out.println(blackList);
        }
    }
}
