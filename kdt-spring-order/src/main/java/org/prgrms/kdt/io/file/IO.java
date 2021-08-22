package org.prgrms.kdt.io.file;

import java.io.IOException;

public interface IO {
    String readLine() throws IOException;
    void writeLine(String s) throws IOException;
    void reset();
}
