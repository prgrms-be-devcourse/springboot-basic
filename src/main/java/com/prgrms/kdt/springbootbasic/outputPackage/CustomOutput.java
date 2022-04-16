package com.prgrms.kdt.springbootbasic.outputPackage;

import com.prgrms.kdt.springbootbasic.entity.Voucher;

import java.util.List;

public interface CustomOutput {
    public void informCommandWithConsole();

    void informNewVoucherInfo();

    void printVoucherList(List<Voucher> voucherList);

    void printError(String error);

    void informProcessEnd();
}
