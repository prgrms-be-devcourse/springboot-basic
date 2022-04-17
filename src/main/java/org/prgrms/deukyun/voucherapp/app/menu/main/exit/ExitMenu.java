package org.prgrms.deukyun.voucherapp.app.menu.main.exit;

import org.prgrms.deukyun.voucherapp.app.menu.Menu;
import org.prgrms.deukyun.voucherapp.app.menu.main.MainMenuChoice;
import org.springframework.stereotype.Component;

/**
 * 퇴장 메뉴
 */
@Component
public class ExitMenu extends Menu<MainMenuChoice> {

    private final ExitButton exitButton;

    public ExitMenu(ExitButton exitButton) {
        super(MainMenuChoice.EXIT);
        this.exitButton = exitButton;
    }

    /**
     * 퇴장 버튼을 누름
     */
    @Override
    public void proc() {
        exitButton.pushBtn();
    }
}
