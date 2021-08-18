package org.prgrms.kdt.voucher.ui;

import org.prgrms.kdt.common.CommandStatus;
import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.view.InputView;
import org.prgrms.kdt.view.OutputView;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherType;
import org.prgrms.kdt.voucher.application.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VoucherController {

    private final int TYPE_INDEX = 0;
    private final int VALUE_INDEX = 1;
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
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
            return;
        }

        if (status == CommandStatus.CREATE) {
            createVoucherOrder();
            return;
        }

        if (status == CommandStatus.ALL) {
            showAllVoucherOrder();
            return;
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
        voucherService.insert(voucher);
    }

    private void showAllVoucherOrder() {
        OutputView.showVouchers(voucherService.allVoucher());
    }

}
