package org.prgrms.kdt.app.io;

import java.io.IOException;

public interface InputHandler {

    String inputString() throws IOException;

    int inputInt() throws IOException;
}
