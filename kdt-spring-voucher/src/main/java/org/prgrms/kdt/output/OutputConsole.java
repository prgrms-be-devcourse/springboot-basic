package org.prgrms.kdt.output;

import org.prgrms.kdt.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutputConsole implements Output {

    @Override
    public void displayMenu() {
        System.out.println("=== Voucher Program ===");
        printMenuLine("exit", "exit the program");
        printMenuLine("create", "create a new voucher");
        printMenuLine("list", "list all vouchers");
        System.out.println();
    }

    @Override
    public void displayUserInputLine() {
        System.out.print("Please Type command : ");
    }

    @Override
    public void displayVoucherCreateMenu() {
        System.out.println("=== What voucher do you want to make? ===");
        printMenuLine("fixedAmount", "create FixedAmountVoucher");
        printMenuLine("percentDiscount", "create PercentDiscountVoucher");
        System.out.println();
    }

    @Override
    public void displayFixedAmountInputValue() {
        printMenuLine("FixedAmount value", "create FixedAmountVoucher");
    }

    @Override
    public void displayPercentDiscountInputValue() {
        printMenuLine("PercentDiscount value", "create PercentDiscountVoucher");
    }

    @Override
    public void userInputWrongValue() {
        System.out.println("Please check the input value.");
    }

    @Override
    public void displayError(Exception e) {
        System.out.println(e.getMessage());
    }

    @Override
    public void displayAllVoucherList(List<Voucher> voucherList) {
        voucherList.stream()
            .forEach(voucher -> System.out.println(String.format(VOUCHER_PRINT_FORMAT, voucher.getVoucherName(), voucher.getVoucherId(), voucher.getVoucherDiscountValue())));
    }

    private void printMenuLine(String userInput, String inputExplanation) {
        System.out.printf("Type %s to %s.%n", userInput, userInput, inputExplanation);
    }
}
