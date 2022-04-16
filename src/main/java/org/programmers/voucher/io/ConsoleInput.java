package org.programmers.voucher.io;

import org.programmers.voucher.domain.VoucherType;
import org.programmers.voucher.util.Command;
import org.programmers.voucher.util.IllegalCommandException;
import org.programmers.voucher.util.IllegalVoucherTypeException;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleInput implements Input{
    private final Scanner sc = new Scanner(System.in);

    @Override
    public Command inputCommand() throws IllegalCommandException {
        String input = sc.nextLine().strip();
        Optional<Command> inputCommand = Arrays.stream(Command.values())
                .filter(value -> value.toString().equals(input))
                .findAny();
        if (inputCommand.isEmpty()){
            throw new IllegalCommandException();
        }
        return inputCommand.get();
    }

    @Override
    public VoucherType inputVoucherType() throws IllegalVoucherTypeException {
        String input = sc.nextLine().strip();
        Optional<VoucherType> inputType = Arrays.stream(VoucherType.values())
                .filter(value -> value.toString().equals(input))
                .findAny();
        if (inputType.isEmpty()){
            throw new IllegalVoucherTypeException();
        }
        return inputType.get();
    }
}
