package org.prgrms.kdt.app.configuration.io;

import java.io.IOException;

public interface InputHandler {

    String inputString() throws IOException;

    int inputInt() throws IOException;
}
