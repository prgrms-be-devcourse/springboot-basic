package org.programmers.kdtspringdemo.io;

import java.util.Optional;

public interface Input {
    String selectOption(String description);
    String selectVoucher(String description);
    Optional<Long> getVoucherInfo(String description);
}
