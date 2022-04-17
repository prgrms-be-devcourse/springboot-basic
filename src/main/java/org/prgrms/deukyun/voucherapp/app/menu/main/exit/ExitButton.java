package org.prgrms.deukyun.voucherapp.app.menu.main.exit;

import org.springframework.stereotype.Component;

/**
 * 퇴장 버튼 <br>
 * ExitMenu -> ExitButton 참조 : exit 을 true 로 set 함<br>
 * VoucherApp -> ExitButton 참조 : 루프마다 isExit 을 확인 함
 */
@Component
public class ExitButton {

    private boolean exit;

    public boolean isExit() {
        return exit;
    }

    public void pushBtn() {
        this.exit = true;
    }
}
