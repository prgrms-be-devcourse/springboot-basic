package org.programmers.spbw1.io;

import org.programmers.spbw1.voucher.Voucher;
import org.programmers.spbw1.voucher.Model.VoucherType;

import java.io.IOException;

public interface Output {
    void initSelect();

    void showVoucherInfo(Voucher voucher);

    void showExceptionTrace(IOException e);

    void invalidInstruction(String in);

    void bye();

    void invalidVoucherSelected();

    void numFormatException();

    void invalidRange(VoucherType voucherType);

    void voucherCreated(Voucher v);

    void listCalled(int voucherNum);
}
