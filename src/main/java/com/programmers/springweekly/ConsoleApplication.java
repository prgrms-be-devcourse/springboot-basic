package com.programmers.springweekly;

import com.programmers.springweekly.domain.ProgramMenu;
import com.programmers.springweekly.view.Console;
import java.util.NoSuchElementException;
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
                    default -> throw new IllegalArgumentException("Input :" + selectMenu + ", 찾는 프로그램 메뉴가 존재하지 않습니다.");
                }
            } catch (IllegalArgumentException e) {
                console.outputErrorMessage("입력 값이 잘못되었습니다. 자세한 메시지 : " + e.getMessage());
            } catch (NoSuchElementException e) {
                console.outputErrorMessage("찾는 값이 존재하지 않습니다. 자세한 메시지 : " + e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                console.outputErrorMessage("파일안 내용의 열의 개수가 잘못되었거나 불러올 수 없는 값을 읽어들였습니다. 자세한 메시지 :  " + e.getMessage());
            } catch (Exception e) {
                console.outputErrorMessage("알 수 없는 에러가 발생되었습니다, 에러 메시지 : " + e.getMessage());
                log.error("콘솔에서 알 수 없는 에러를 잡았습니다." + e);
            }
        }
    }

}
