package org.prgrms.kdt.voucher.ui;

import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.view.InputView;
import org.prgrms.kdt.view.OutputView;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.application.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class VoucherController {
    private final String EXIT = "exit";
    private final String CREATE = "create";
    private final String ALL = "all";

    private final VoucherService voucherService;

    public VoucherController() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        this.voucherService = applicationContext.getBean(VoucherService.class);
    }

    public void play() {
        while (true) {
            InputView.initMessage();
            String choiceMenu = InputView.input();
            choiceMenu(choiceMenu);
        }
    }

    public void choiceMenu(String userInputMessage) {
        if (userInputMessage.equals(EXIT)) {
            InputView.closeScanner();
            OutputView.exit();
        }

        if (userInputMessage.equals(CREATE)) {
            InputView.explainCreateVoucher();
            String choice = InputView.input();
            Voucher voucher = voucherService.choiceVoucher(choice);
            voucherService.addVoucher(voucher);
        }

        if (userInputMessage.equals(ALL)) {
            OutputView.showVouchers(voucherService.allVoucher());
        }

    }
}
