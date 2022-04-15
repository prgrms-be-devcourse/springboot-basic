package com.voucher.vouchermanagement.repository.io;

import java.io.File;
import java.io.IOException;

public interface FileOutput {
    void writeln(File file, String content) throws IOException;
}
