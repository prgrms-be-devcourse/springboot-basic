package org.programmers.voucher.io;

import org.programmers.voucher.domain.VoucherType;
import org.programmers.voucher.util.Command;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Scanner;

@Component
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
    public Long inputValue() {
        Long input = null;
        while(input == null) {
            try {
                input = Long.parseLong(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Number Parse Error");
            }
        }
        return input;
    }
}
