package com.programmers.springweekly;

import com.programmers.springweekly.domain.ProgramMenu;
import com.programmers.springweekly.view.Console;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!test")
public class ConsoleApplication implements CommandLineRunner {

    private final ConsoleVoucher consoleVoucher;
    private final ConsoleCustomer consoleCustomer;
    private final ConsoleWallet consoleWallet;
    private final Console console;

    private boolean running = true;

    @Override
    public void run(String... args) {
        while (running) {
            console.outputProgramGuide();
            try {
                ProgramMenu selectMenu = ProgramMenu.from(console.inputMessage());

                switch (selectMenu) {
                    case CUSTOMER -> consoleCustomer.menu();
                    case VOUCHER -> consoleVoucher.menu();
                    case WALLET -> consoleWallet.menu();
                    case EXIT -> {
                        console.outputExitMessage();
                        running = false;
                    }
                    default -> throw new IllegalArgumentException("Input :" + selectMenu + "The type you are looking for is not found.");
                }
            } catch (IllegalArgumentException e) {
                log.error("잘못된 입력값 입니다 -> " + e.getMessage());
            } catch (Exception e) {
                log.error("알 수 없는 에러입니다 -> " + e.getMessage());
            }
        }
    }
}
