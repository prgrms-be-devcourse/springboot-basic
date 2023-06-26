package com.dev.bootbasic.view;

import com.dev.bootbasic.voucher.dto.VoucherCreateRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class ViewManager {

    private static final String INPUT_COMMAND_MESSAGE = "=== Voucher Program ===\n" +
            "Type exit to exit the program.\n" +
            "Type create to create a new voucher.\n" +
            "Type list to list all vouchers.\n";
    private static final String VOUCHER_TYPE_MESSAGE = "=== Voucher Type Choice ===\n" +
            "Type fixed {Amount}\n" +
            "Type percent {1 ~ 100}\n";
    private static final int VOUCHER_TYPE_INDEX = 0;
    private static final int VOUCHER_AMOUNT_INDEX = 1;
    private static final String SPACE = " ";
    private final InputView inputView;
    private final OutputView outputView;

    public ViewManager(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public Command readCommand() throws IOException {
        outputView.showMessage(INPUT_COMMAND_MESSAGE);
        return Command.from(inputView.inputLine());
    }

    public VoucherCreateRequest readVoucherCreateInfo() throws IOException {
        outputView.showMessage(VOUCHER_TYPE_MESSAGE);

        String voucher = inputView.inputLine();
        String[] voucherInfo = voucher.split(SPACE);

        String voucherType = voucherInfo[VOUCHER_TYPE_INDEX];
        int amount = Integer.parseInt(voucherInfo[VOUCHER_AMOUNT_INDEX]);
        return new VoucherCreateRequest(voucherType, amount);
    }

    public void showMessage(String message) {
        outputView.showMessage(message);
    }

}
