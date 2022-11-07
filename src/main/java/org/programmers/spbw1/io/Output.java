package org.programmers.spbw1.io;

import org.programmers.spbw1.voucher.Voucher;

import java.io.IOException;

public interface Output {
    void initSelect();

    void showVoucherInfo(Voucher voucher);

    void showExceptionTrace(IOException e);

    void invalidInstruction(String in);

    void bye();
}
