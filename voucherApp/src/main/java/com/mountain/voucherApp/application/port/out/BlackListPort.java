package com.mountain.voucherApp.application.port.out;

import com.mountain.voucherApp.adapter.out.file.BlackListFileFormat;

import java.io.IOException;
import java.util.List;

public interface BlackListPort {
    List<BlackListFileFormat> getBlackList() throws IOException;
}
