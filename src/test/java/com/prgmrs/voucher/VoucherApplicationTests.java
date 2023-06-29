package com.prgmrs.voucher;

import com.prgmrs.voucher.view.ConsoleView;
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


}
