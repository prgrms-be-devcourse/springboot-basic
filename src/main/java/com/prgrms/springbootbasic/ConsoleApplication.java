package com.prgrms.springbootbasic;

import com.prgrms.springbootbasic.enums.ConsoleMenu;
import com.prgrms.springbootbasic.view.Console;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsoleApplication implements CommandLineRunner {

    private final ConsoleVoucher consoleVoucher;
    private final ConsoleCustomer consoleCustomer;
    private final Console console;

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            console.printConsoleMenu();
            try {
                ConsoleMenu inputMenu = ConsoleMenu.of(console.inputCommand());

                switch (inputMenu) {
                    case CUSTOMER -> consoleCustomer.menu();
                    case VOUCHER -> consoleVoucher.menu();
                    case EXIT -> {
                        console.printExitMessage();
                        return;
                    }
                }
            } catch (IllegalArgumentException e) {
                log.error("명령어가 잘못 입력되었습니다.", e);
                log.error("에러 메시지: {}", e.getMessage());
                log.error("Stack Trace: ", e);
            } catch (Exception e) {
                log.error("프로그램에서 오류가 발생하였습니다.", e);
                log.error("에러 메시지: {}", e.getMessage());
                log.error("Stack Trace: ", e);
            }
        }
    }
}