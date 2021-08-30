package org.prgrms.kdt.io;

import org.prgrms.kdt.exception.InvalidIOMessageException;

public interface Input {
    String readLine() throws InvalidIOMessageException;
}
