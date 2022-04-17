package org.prgrms.deukyun.voucherapp.app;

import org.prgrms.deukyun.voucherapp.app.menu.main.MainMenu;
import org.prgrms.deukyun.voucherapp.app.menu.main.MainMenuChoice;
import org.prgrms.deukyun.voucherapp.app.menu.main.exit.ExitButton;
import org.prgrms.deukyun.voucherapp.domain.common.repository.NoIdFieldException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 바우처 애플리케이션 - 애플리케이션의 템플릿
 */
@Component
public class VoucherApp {

    private final Logger logger = LoggerFactory.getLogger(VoucherApp.class);
    private final MainMenu mainMenu;
    private final ExitButton exitButton;

    /**
     * @param mainMenu 메인 메뉴 : 루프마다 로직을 실행
     * @param exitButton 버튼 : 루프마다 퇴장 버튼 확인
     */
    public VoucherApp(MainMenu mainMenu, ExitButton exitButton) {
        this.mainMenu = mainMenu;
        this.exitButton = exitButton;
    }

    public void run() {
        logger.info("app launched");
        boolean isBreak = false;
        do {
            try {
                printMenus();
                mainMenu.proc();
                isBreak = exitButton.isExit();
            } catch (NoIdFieldException ex){
                System.out.println(ex.getMessage());
                isBreak = true;
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
            } catch (Exception ex) {
                System.out.println("알 수 없는 에러 발생. 프로그램 종료");
                isBreak = true;
            }
        } while (!isBreak);
    }

    /**
     * 메인 메뉴의 선택 목록 출력
     */
    private void printMenus(){
        System.out.println("\n=== Voucher Program ===");
        Arrays.stream(MainMenuChoice.values())
                .forEach(c -> System.out.println(c.getPrintEveryLoop()));
    }
}
