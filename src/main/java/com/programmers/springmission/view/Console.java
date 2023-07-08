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

    public String input() {
        return input.read();
    }

    public void output(String message) {
        output.write(message);
    }

    public void outputDomainOption() {
        output.write(newLine + newLine + """
                === Welcome Voucher Program ===
                Type "exit" to exit the program.
                Type "voucher" to switch to the voucher mode.
                Type "customer" to switch to the customer mode.
                """);
    }

    public void outputExit() {
        output.write("Voucher Program exit !");
    }

    public void outputVoucherCrud() {
        output.write(newLine + """
                === Input Voucher CRUD menu ===
                Type "1" => Create voucher
                Type "2" => Find by voucher id
                Type "3" => Find all voucher
                Type "4" => Update voucher amount
                Type "5" => Delete by voucher id
                Type "6" => Delete all voucher
                """);
    }

    public String inputVoucherPolicy() {
        output.write(newLine + """
                === Select Voucher Policy ===
                Type "fixed" create a FixedAmountVoucher
                Type "percent" create a PercentDiscountVoucher
                """);
        return input.read();
    }

    public String inputVoucherAmount() {
        output.write(newLine + """
                === Input Voucher Amount ===
                Type the amount you want
                0 < Fixed, 0 < Percent <= 100
                """);
        return input.read();
    }

    public void outputVoucherCreate(VoucherResponse voucherResponse) {
        output.write(newLine + "Success Create Voucher !" + newLine + voucherResponse + newLine);
    }

    public String inputVoucherId() {
        output.write(newLine + """
                === Input Voucher Id ===
                Type the voucher Id
                """);
        return input.read();
    }

    public void outputVoucherFindById(VoucherResponse voucherResponse) {
        output.write(voucherResponse.toString() + newLine);
    }

    public void outputVoucherFindAll(List<VoucherResponse> voucherResponses) {
        output.write(voucherResponses);
    }

    public void outputVoucherUpdate(VoucherResponse voucherResponse) {
        output.write(newLine + "Success Update Voucher !" + newLine + voucherResponse + newLine);
    }

    public void outputVoucherDeleteById() {
        output.write(newLine + "Delete Success Voucher !" + newLine);
    }

    public void outputVoucherDeleteAll() {
        output.write(newLine + "Delete Success All Voucher !" + newLine);
    }
}

