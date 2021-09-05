package org.prgrms.kdt.domain.command;

import java.io.IOException;

public interface CommandAction {
    void action() throws IOException, IllegalArgumentException;
}
