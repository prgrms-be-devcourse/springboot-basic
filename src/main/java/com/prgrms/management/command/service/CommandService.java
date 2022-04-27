package com.prgrms.management.command.service;

import com.prgrms.management.command.domain.Command;

public interface CommandService {
    void run();

    void execute(Command command);
}
