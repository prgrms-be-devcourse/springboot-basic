package com.voucher.vouchermanagement.repository.io;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileInput {
  List<String> readAllLine(File file) throws IOException;
}
