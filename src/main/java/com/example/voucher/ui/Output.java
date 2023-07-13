package com.example.voucher.ui;

import com.example.voucher.domain.Voucher;
import java.util.List;

public interface Output {
    void printProgramInfo();
    void requestVoucherAmount();
    void printVoucherInfoList(List<Voucher> vouchers);
}

