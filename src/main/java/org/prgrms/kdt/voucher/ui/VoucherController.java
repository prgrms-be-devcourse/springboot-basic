package org.prgrms.kdt.voucher.ui;

import org.prgrms.kdt.common.CommandStatus;
import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.view.InputView;
import org.prgrms.kdt.view.OutputView;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherType;
import org.prgrms.kdt.voucher.application.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class VoucherController {

    private final int TYPE_INDEX = 0;
    private final int VALUE_INDEX = 1;
    private final VoucherService voucherService;

    public VoucherController() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        this.voucherService = applicationContext.getBean(VoucherService.class);
    }

    public void play() {
        while (true) {
            InputView.initMessage();
            String choiceMenu = InputView.input();
            CommandStatus status = choiceMenu(choiceMenu);
            executeMenu(status);
        }
    }

    private CommandStatus choiceMenu(String userInputMessage) {
        return CommandStatus.findByCommandType(userInputMessage);
    }


    private void executeMenu(CommandStatus status) {
        if (status == CommandStatus.EXIT) {
            exitCommandOrder();
        }

        if (status == CommandStatus.CREATE) {
            createVoucherOrder();
        }

        if (status == CommandStatus.ALL) {
            showAllVoucherOrder();
        }
    }

    private void exitCommandOrder() {
        InputView.closeScanner();
        OutputView.exit();
    }

    private void createVoucherOrder() {
        InputView.explainCreateVoucher();
        List<String> typeAndValue = InputView.typeAndValue(InputView.input());
        VoucherType type = voucherService.choiceVoucher(typeAndValue.get(TYPE_INDEX));
        Voucher voucher = voucherService.createVoucher(type, typeAndValue.get(VALUE_INDEX));
        voucherService.addVoucher(voucher);
    }

    private void showAllVoucherOrder() {
        OutputView.showVouchers(voucherService.allVoucher());
    }

}
