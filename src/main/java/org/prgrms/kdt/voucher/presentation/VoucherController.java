package org.prgrms.kdt.voucher.presentation;

import org.prgrms.kdt.common.CommandStatus;
import org.prgrms.kdt.view.InputView;
import org.prgrms.kdt.view.OutputView;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.application.VoucherService;
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
            InputView.initVoucherMessage();
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
            createVoucher();
            return;
        }

        if (status == CommandStatus.ALL) {
            showVouchers();
        }
    }

    private void exitCommandOrder() {
        InputView.closeScanner();
        OutputView.exit();
    }

    private void createVoucher() {
        InputView.explainCreateVoucher();
        List<String> typeAndValue = InputView.typeAndValue(InputView.input());
        VoucherType type = VoucherType.findByVoucherType(typeAndValue.get(TYPE_INDEX));
        Voucher voucher = voucherService.createVoucher(type, typeAndValue.get(VALUE_INDEX));
        voucherService.insert(voucher);
    }

    private void showVouchers() {
        OutputView.showVouchers(voucherService.findByAllVoucher());
    }

}
