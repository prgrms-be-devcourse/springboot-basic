package kr.co.programmers.springbootbasic.io;

import kr.co.programmers.springbootbasic.voucher.VoucherType;

public interface Input {
    MenuCommand readMenuCommand();
    VoucherType readVoucherType();
    int readAmount();
}
