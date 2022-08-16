package com.prgrms.kdt.springbootbasic.io.outputPackage;

import com.prgrms.kdt.springbootbasic.voucher.entity.Voucher;

import java.util.List;

public interface CustomOutput {
    void informCommandWithConsole();

    void informNewVoucherInfo();

    void printVoucherList(List<Voucher> voucherList);

    void printError(String error);

    void informProcessEnd();

    void wrongInput();

    void errorOccurred();
}
