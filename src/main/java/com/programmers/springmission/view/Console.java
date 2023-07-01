package com.programmers.springmission.view;

import com.programmers.springmission.voucher.presentation.response.VoucherResponse;

import java.util.List;

public class Console {

    private final Input input;
    private final Output output;
    private final String newLine = System.getProperty("line.separator");

    public Console(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public void outputOption() {
        output.write(newLine +
                """
                        === Voucher Program ===
                        Type "exit" to exit the program.
                        Type "create" to create a new voucher.
                        Type "list" to list all vouchers.
                        """
                + newLine);
    }

    public String inputOption() {
        return input.read();
    }

    public void outputExit() {
        output.write("Voucher Program exit !");
    }

    public void outputCreateOption() {
        output.write("""
                === Select Voucher Policy ===
                Type "1" create a FixedAmountVoucher
                Type "2" create a PercentDiscountVoucher
                """ + newLine);
    }

    public void outputCreate(VoucherResponse voucherResponse) {
        output.write("Success Create Voucher !" + newLine + voucherResponse + newLine);
    }

    public String inputVoucherAmount() {
        output.write("""
                === Input Voucher Amount ===
                Type the amount you want
                """);
        return input.read();
    }

    public void output(String message) {
        output.write(message);
    }

    public void outputVoucherList(List<VoucherResponse> voucherResponses) {
        output.write(voucherResponses);
    }
}

