package com.programmers.application.domain.command.execution;

import com.programmers.application.io.IO;
import com.programmers.application.service.VoucherService;

public interface VoucherExecution {
    void run(VoucherService voucherService, IO io);
}
