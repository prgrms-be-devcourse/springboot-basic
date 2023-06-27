package com.dev.bootbasic.view;

import com.dev.bootbasic.voucher.domain.VoucherType;
import com.dev.bootbasic.voucher.dto.VoucherCreateRequest;
import com.dev.bootbasic.voucher.dto.VoucherDetailsResponse;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.dev.bootbasic.util.Constant.SPACE_SEPARATOR;

@Component
public class ViewManager {

    private static final String INPUT_COMMAND_MESSAGE = """
            === Voucher Program ===
            Type exit to exit the program.
            Type create to create a new voucher.
            Type list to list all vouchers.
            """;
    private static final String VOUCHER_TYPE_MESSAGE = """
            === Voucher Type Choice ===
            %s
            """;
    private static final int VOUCHER_TYPE_INDEX = 0;
    private static final int VOUCHER_AMOUNT_INDEX = 1;
    private final InputView inputView;
    private final OutputView outputView;

    public ViewManager(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public Command readCommand() {
        outputView.showMessage(INPUT_COMMAND_MESSAGE);
        return Command.from(inputView.inputLine());
    }

    public VoucherCreateRequest readVoucherCreateInfo() {
        outputView.showMessage(String.format(VOUCHER_TYPE_MESSAGE, VoucherType.getDisplayMessage()));

        String voucher = inputView.inputLine();
        String[] voucherInfo = voucher.split(SPACE_SEPARATOR);

        String voucherType = voucherInfo[VOUCHER_TYPE_INDEX];
        int amount = Integer.parseInt(voucherInfo[VOUCHER_AMOUNT_INDEX]);
        return new VoucherCreateRequest(voucherType, amount);
    }

    public void showCollectionMessage(List<VoucherDetailsResponse> vouchers) {
        vouchers.forEach(message -> showMessage(message.toString()));
    }

    public void showMessage(String message) {
        outputView.showMessage(message);
    }

}
