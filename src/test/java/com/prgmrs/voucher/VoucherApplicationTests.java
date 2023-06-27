package com.prgmrs.voucher;

import com.prgmrs.voucher.view.ConsoleView;
import com.prgmrs.voucher.view.ConsoleViewEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Scanner;

@SpringBootTest
class VoucherApplicationTests {

    private ConsoleView consoleView;
    private Scanner sc;

    @Autowired
    VoucherApplicationTests(ConsoleView consoleView, Scanner sc) {
        this.consoleView = consoleView;
    }
    @Test
    void contextLoads() {
        ConsoleViewEnum consoleViewEnum = ConsoleViewEnum.findByCommand(consoleView.read());
    }

}
