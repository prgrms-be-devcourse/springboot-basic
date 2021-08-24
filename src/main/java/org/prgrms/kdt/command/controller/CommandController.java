package org.prgrms.kdt.command.controller;

import org.prgrms.kdt.command.domain.CommandType;
import org.prgrms.kdt.command.domain.Command;
import org.prgrms.kdt.command.service.ServiceBlackList;
import org.prgrms.kdt.command.service.ServiceCreate;
import org.prgrms.kdt.command.service.ServiceExit;
import org.prgrms.kdt.command.service.ServiceList;
import org.springframework.stereotype.Component;

@Component
public class CommandController {

    private final ServiceCreate strategyCreate;
    private final ServiceList serviceList;
    private final ServiceExit serviceExit;
    private final ServiceBlackList serviceBlackList;

    public CommandController(
            ServiceCreate strategyCreate,
            ServiceList serviceList,
            ServiceExit serviceExit,
            ServiceBlackList serviceBlackList
    ) {
        this.strategyCreate = strategyCreate;
        this.serviceList = serviceList;
        this.serviceExit = serviceExit;
        this.serviceBlackList = serviceBlackList;
    }

    public Command getCommandService(String inputString) {
        CommandType type = parseInput(inputString);
        return switch (type) {
            case CREATE -> strategyCreate;
            case LIST -> serviceList;
            case EXIT -> serviceExit;
            case BLACK_LIST -> serviceBlackList;
        };
    }

    private CommandType parseInput(String inputString) {
        return CommandType.findCommand(inputString);
    }

}
