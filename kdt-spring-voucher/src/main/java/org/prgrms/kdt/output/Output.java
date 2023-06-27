package org.prgrms.kdt.output;

public interface Output {
    void displayMenu();
    void displayUserInputLine();

    void displayVoucherCreateMenu();
    void displayFixedAmountInputValue();
    void displayPercentDiscountInputValue();
    void userInputWrongValue();
}
