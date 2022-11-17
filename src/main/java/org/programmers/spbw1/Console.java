package org.programmers.spbw1;

import org.programmers.spbw1.io.Input;
import org.programmers.spbw1.io.Output;
import org.programmers.spbw1.voucher.Voucher;
import org.programmers.spbw1.voucher.Model.VoucherType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console implements Input, Output {
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String input(String prompt) throws IOException {
        System.out.print(prompt);
        return bufferedReader.readLine();
    }

    @Override
    public void initSelect() {
        System.out.println("\n=== Voucher Program ===");
        System.out.println("Type **exit** to exit the program.");
        System.out.println("Type **create** to create a new voucher.");
        System.out.println("Type **list** to list all vouchers.");
    }

    @Override
    public void showVoucherInfo(Voucher voucher) {
        System.out.println(voucher.toString());
    }

    @Override
    public void showExceptionTrace(IOException e) {
        e.printStackTrace();
    }

    @Override
    public void invalidInstruction(String in) {
        System.out.println("Invalid Instruction inserted!! : " + in);
    }

    @Override
    public void bye() {
        System.out.println("Bye!");
    }

    @Override
    public void invalidVoucherSelected() {
        System.out.println("Invalid Voucher selected!!");
        System.out.println("Please select between 1 or 2");
    }

    @Override
    public void numFormatException() {
        System.out.println("you give invalid format");
        System.out.println("voucher discount amount should be number");
    }

    @Override
    public void invalidRange(VoucherType voucherType) {
        System.out.println("invalid voucher range, valid range : " + VoucherType.getRange(voucherType));
    }

    @Override
    public void voucherCreated(Voucher voucher) {
        System.out.println("Voucher created successfully!!");
        System.out.println("voucher info : " + voucher.toString());
    }

    @Override
    public void listCalled(int voucherNum) {
        System.out.println("List Called!!, number of saved vouchers : " + voucherNum);
    }
}
