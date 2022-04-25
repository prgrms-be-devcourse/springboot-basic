package org.programmers.voucher.io;

import org.programmers.voucher.domain.VoucherType;
import org.programmers.voucher.util.Command;

import java.util.Arrays;
import java.util.Scanner;

public class ConsoleInput implements Input{
    private final Scanner sc = new Scanner(System.in);

    @Override
    public Command inputCommand() throws IllegalArgumentException {
        String input = sc.nextLine().strip();
        return Arrays.stream(Command.values())
                .filter(value -> value.isMatches(input))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public VoucherType inputVoucherType() throws IllegalArgumentException {
        String input = sc.nextLine().strip();
        return Arrays.stream(VoucherType.values())
                .filter(value -> value.isMatches(input))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public long inputAmount() {
        long input = Long.parseLong(sc.nextLine());
        return 0;
    }

    @Override
    public double inputPercent() {
        double input = Double.parseDouble(sc.nextLine());
        return 0;
    }
}
