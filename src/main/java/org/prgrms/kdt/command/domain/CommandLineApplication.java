package org.prgrms.kdt.command.domain;

import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.command.service.CommandBlacklistService;
import org.prgrms.kdt.command.service.CommandCreateService;
import org.prgrms.kdt.command.service.CommandExitService;
import org.prgrms.kdt.command.service.CommandListService;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication implements Runnable {
    private final CommandExitService exitService;
    private final CommandCreateService createService;
    private final CommandListService listService;
    private final CommandBlacklistService blacklistService;

    public CommandLineApplication(
            final CommandExitService exitService,
            final CommandCreateService createService,
            final CommandListService listService,
            final CommandBlacklistService blacklistService) {
        this.exitService = exitService;
        this.createService = createService;
        this.listService = listService;
        this.blacklistService = blacklistService;
    }

    @Override
    public void run() {
        boolean programRunning = true;
        do {
            Output.commandChooseMessage();

            final String commandInput = Input.input();
            switch (commandInput) {
                case "exit" -> {
                    exitService.commandRun();
                    programRunning = false;
                }
                case "create" -> createService.commandRun();
                case "list" -> listService.commandRun();
                case "blacklist" -> blacklistService.commandRun();
                default -> Output.inputTypeErrorMessage(commandInput);
            }
        } while (programRunning);
    }
}
