package org.prgrms.kdt.io;

import java.util.Scanner;

import static org.apache.commons.lang3.math.NumberUtils.toLong;

public class InputConsole implements Input {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String input() {
        return scanner.nextLine();
    }

    @Override
    public long inputLongBiggerThanZero() {
        long input = toLong(scanner.nextLine());
        if (input <= 0) {
            throw new IllegalArgumentException("바우처 값은 0 초과로 입력해주세요.");
        }
        return input;
    }
}
