package com.programmers.kdtspringorder.io;

import com.programmers.kdtspringorder.voucher.domain.Voucher;

public interface Output {
    void printMessage(String message);
    void print(Voucher voucher);
    void newLine();

}
