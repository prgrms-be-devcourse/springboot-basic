package org.programmers.kdt.weekly.io;

import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.springframework.stereotype.Component;

@Component
public class OutputConsole implements Output {
    @Override
    public void startMessage() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
        System.out.println("Type list -b to list all black-list");
    }

    @Override
    public void inputErrorMessage(Enum<InputErrorType> inputErrorType) {
        if (inputErrorType == InputErrorType.COMMAND) {
            System.out.println("This command does not exist.");
        } else if (inputErrorType == InputErrorType.INVALID) {
            System.out.println("Invalid input.");
        } else if (inputErrorType == InputErrorType.VOUCHER_EMPTY) {
            System.out.println("There are no saved vouchers.");
        } else if (inputErrorType == InputErrorType.BLACK_LIST_EMPTY) {
            System.out.println("There are no saved blacklist.");
        }
        System.out.println();
    }

    @Override
    public void voucherSelectMessage() {
        System.out.println("Select a voucher type");
        System.out.println("1. FixedAmountVoucher");
        System.out.println("2. PercentDiscountVoucher");
        System.out.print("Selected : ");
    }

    @Override
    public void voucherDiscountMessage(Enum<VoucherType> voucherTypeEnum) {
        if (voucherTypeEnum == VoucherType.FixedVoucherRepository) {
            System.out.println("Please enter the discount amount.");
        } else if (voucherTypeEnum == VoucherType.PercentDiscountVoucher) {
            System.out.println("Please enter discount rate.");
        }
        System.out.print("ENTER : ");
    }

}
