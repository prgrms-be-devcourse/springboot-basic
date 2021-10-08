package org.prgrms.kdt.command.service;

import org.prgrms.kdt.command.io.Output;
import org.springframework.stereotype.Service;

@Service
public class ExitService implements CommandService {
    @Override
    public void commandRun() {
        Output.exitMessage();
    }
}
