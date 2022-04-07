package org.prgrms.vouchermanager.console;

public interface Input {
    String read();

    default Long readLong() {
        return Long.parseLong(read());
    }

}
