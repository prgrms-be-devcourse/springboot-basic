package org.prgrms.kdt.output;

import org.prgrms.kdt.voucher.Voucher;

import java.util.List;

public interface Output {
    String VOUCHER_PRINT_FORMAT = "[%-25s] id: %s discountValue: %d";

    void displayMenu();

    void displayUserInputLine();

    void displayVoucherCreateMenu();

    void displayFixedAmountInputValue();

    void displayPercentDiscountInputValue();

    void userInputWrongValue();

    void displayError(Exception e);

    void displayAllVoucherList(List<Voucher>voucherList);
}
