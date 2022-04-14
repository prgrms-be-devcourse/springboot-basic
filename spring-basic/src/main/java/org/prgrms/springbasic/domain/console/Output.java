package org.prgrms.springbasic.domain.console;

import org.prgrms.springbasic.domain.voucher.VoucherType;

import java.util.List;

public interface Output {

    void printInitMessage();

    void printVoucherType();

    void printDiscountInput(VoucherType type);

    void printNameInput();

    <T> void printData(List<T> data);

    void printErrorMessage(Exception e);
}
