package org.programmers.voucher.io;

import org.programmers.voucher.domain.Voucher;
import org.programmers.voucher.domain.VoucherType;
import org.programmers.voucher.util.Command;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ConsoleOutput implements Output{

    @Override
    public void startProgram() {
        System.out.println("=== Voucher Program ===");
    }

    @Override
    public void listCommand() {
        Arrays.stream(Command.values())
                .forEach(value -> System.out.println("Type " + value.toString() + " to " + value.getExplanation()));
    }

    @Override
    public void listVoucher(List<Voucher> vouchers) {
        if (vouchers.isEmpty()){
            System.out.println("No Voucher");
        }
        vouchers.forEach(voucher -> System.out.println(voucher.toString()));
    }

    @Override
    public void listVoucherType() {
        Arrays.stream(VoucherType.values()).forEach(value -> System.out.println(value.getAlias() + " -> " + value.getDescription()));
    }

    @Override
    public void printError(String error) {
        System.out.println(error);
    }
}
