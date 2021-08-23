package org.prgrms.kdtspringdemo.io;

import org.prgrms.kdtspringdemo.CommandType;

public interface Input {
    CommandType getInputCommand();

    String getCreateLine();
}
