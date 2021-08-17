package org.prgrms.kdt.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Created by yhh1056
 * Date: 2021/08/17 Time: 11:39 오후
 */
class ConsoleTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("가이드 파일 읽기 테스트")
    void readFile() {
        Console console = new Console();
        console.guide();

        final String guide = "=== Voucher Program ===\n"
                + "Type exit to exit the program.\n"
                + "Type create to create a new voucher.\n"
                + "Type list to list all vouchers.\n";

        assertEquals(guide, outputStreamCaptor.toString());
    }
}