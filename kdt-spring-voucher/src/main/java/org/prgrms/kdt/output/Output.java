package org.prgrms.kdt.output;

public interface Output {
    String VOUCHER_PRINT_FORMAT = "[%-25s] id: %s discountValue: %d";

    void displayMenu();

    void displayUserInputLine();

    void displayVoucherCreateMenu();

    void displayFixedAmountInputValue();

    void displayPercentDiscountInputValue();

    void userInputWrongValue();

    void displayError(Exception e);

    void displayAllVoucherList();
}
