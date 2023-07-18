package com.devcourse.voucherapp.command;

import static java.text.MessageFormat.format;

import com.devcourse.voucherapp.entity.HomeMenu;
import com.devcourse.voucherapp.exception.HomeException;
import com.devcourse.voucherapp.view.HomeView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("local")
public class HomeCommand implements CommandLineRunner {

    private final HomeView homeView;
    private final VoucherCommand voucherCommand;
    private final CustomerCommand customerCommand;
    private boolean isRunning = true;

    @Override
    public void run(String... args) {
        while (isRunning) {
            homeView.showMenu();

            try {
                String menuOption = homeView.readUserInput();
                HomeMenu selectedMenu = HomeMenu.from(menuOption);
                executeMenu(selectedMenu);
            } catch (HomeException e) {
                log.error("홈 메뉴에서 예외 발생 - {} | '{}' | 사용자 입력 : {}", e.getRule(), e.getMessage(), e.getCauseInput(), e);
                homeView.showExceptionMessage(format("{0} | 현재 입력 : {1}", e.getMessage(), e.getCauseInput()));
            } catch (Exception e) {
                log.error("원인 불명의 예외 발생 : '{}'", e.getMessage(), e);
                homeView.showExceptionMessage("알 수 없는 에러가 발생하였습니다.");
                quitApplication();
            }
        }
    }

    private void executeMenu(HomeMenu selectedMenu) {
        switch (selectedMenu) {
            case VOUCHER -> voucherCommand.run();
            case CUSTOMER -> customerCommand.run();
            case QUIT -> quitApplication();
        }
    }

    private void quitApplication() {
        isRunning = false;
        homeView.showQuitMessage();
    }
}
