package org.prgrms.deukyun.voucherapp.app.menu.main.showallvoucher;

import org.prgrms.deukyun.voucherapp.app.menu.Menu;
import org.prgrms.deukyun.voucherapp.app.menu.main.MainMenuChoice;
import org.prgrms.deukyun.voucherapp.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

/**
 * 바우처 목록 출력 메뉴
 */
@Component
public class ShowAllVoucherMenu extends Menu<MainMenuChoice> {

    private final VoucherService voucherService;

    public ShowAllVoucherMenu(VoucherService voucherService) {
        super(MainMenuChoice.LIST);
        this.voucherService = voucherService;
    }

    /**
     * 바우처 서비스를 통해 모든 바우처를 조회하여 출력
     */
    @Override
    public void proc() {
        voucherService.findAll().forEach(v -> System.out.println(v.toDisplayString()));
    }
}
