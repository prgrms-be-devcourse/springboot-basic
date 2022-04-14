package org.prgrms.springbasic.domain.application;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.domain.console.Console;
import org.prgrms.springbasic.service.console.ConsoleService;
import org.prgrms.springbasic.utils.exception.NonExistentCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.prgrms.springbasic.utils.enumm.ErrorMessage.COMMAND_ERROR;

@Component
@Slf4j
public class Application implements Runnable {

    private final Map<String, ConsoleService> services;
    private final Console console;

    @Autowired
    public Application(Map<String, ConsoleService> services, Console console) {
        this.services = services;
        this.console = console;
    }

    @Override
    public void run() {
        log.info("program start");

        while (true) {
            console.printInitMessage();

            try {
                ConsoleService service = findService(console.takeInput());
                service.execute();
            } catch (Exception e) {
                console.printErrorMessage(e);
            }
        }
    }

    private ConsoleService findService(String command){
        var service = services.get(command);

        if (service == null) {
            log.error("Non existent command: {}", command);
            throw new NonExistentCommand(COMMAND_ERROR.getMessage());
        }

        return service;
    }
}