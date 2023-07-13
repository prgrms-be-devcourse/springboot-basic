package com.programmers.springmission.view;

import com.programmers.springmission.customer.presentation.response.CustomerResponse;
import com.programmers.springmission.customer.presentation.response.WalletResponse;
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

    public void outputVoucherMenu() {
        output.write(newLine + """
                === Input Voucher CRUD menu ===
                Type "1" => Create voucher
                Type "2" => Find by voucher id
                Type "3" => Find all voucher
                Type "4" => Update voucher amount
                Type "5" => Delete by voucher id
                Type "6" => Delete all voucher
                Type "7" => Assign customer to voucher
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

    public void outputOneVoucher(VoucherResponse voucherResponse) {
        output.write(voucherResponse.toString() + newLine);
    }

    public void outputAllVoucher(List<VoucherResponse> voucherResponses) {
        output.write(voucherResponses);
    }

    public void outputVoucherUpdate(VoucherResponse voucherResponse) {
        output.write(newLine + "Success Update Voucher !" + newLine + voucherResponse + newLine);
    }

    public void outputVoucherDelete() {
        output.write(newLine + "Delete Success Voucher !" + newLine);
    }

    public void outputVoucherDeleteAll() {
        output.write(newLine + "Delete Success All Voucher !" + newLine);
    }

    public void outputCustomerMenu() {
        output.write(newLine + """
                === Input Customer CRUD menu ===
                Type "1" => Create Customer
                Type "2" => Find by customer id or customer email
                Type "3" => Find all customer
                Type "4" => Update customer name
                Type "5" => Delete by customer id
                Type "6" => Delete all customer
                Type "7" => Find customer's wallet
                """);
    }

    public String inputCustomerId() {
        output.write(newLine + """
                === Input Customer Id ===
                Type the customer Id
                """);
        return input.read();
    }

    public String inputCustomerName() {
        output.write(newLine + """
                === Input Customer Name ===
                Type the customer name
                """);
        return input.read();
    }

    public String inputCustomerEmail() {
        output.write(newLine + """
                === Input Customer Email ===
                Type the customer email
                """);
        return input.read();
    }

    public String inputCustomerFindOption() {
        output.write(newLine + """
                === Select Customer Find Option ===
                Type "id" find by customer id
                Type "email" find by customer email
                """);
        return input.read();
    }

    public void outputCustomerCreate(CustomerResponse customerResponse) {
        output.write(newLine + "Success Create Customer !" + newLine + customerResponse + newLine);
    }

    public void outputCustomerFindOne(CustomerResponse customerResponse) {
        output.write(customerResponse.toString() + newLine);
    }

    public void outputCustomerFindAll(List<CustomerResponse> customerResponses) {
        output.write(customerResponses);
    }

    public void outputCustomerWallet(List<WalletResponse> walletResponses) {
        output.write(walletResponses);
    }

    public void outputCustomerUpdate(CustomerResponse customerResponse) {
        output.write(newLine + "Success Update Customer !" + newLine + customerResponse + newLine);
    }

    public void outputCustomerDeleteById() {
        output.write(newLine + "Delete Success Customer !" + newLine);
    }

    public void outputCustomerDeleteAll() {
        output.write(newLine + "Delete Success All Customer !" + newLine);
    }

    public void outputVoucherAssign(VoucherResponse voucherResponse) {
        output.write(newLine + "Success Assign Customer to Voucher !" + newLine + voucherResponse + newLine);
    }
}

