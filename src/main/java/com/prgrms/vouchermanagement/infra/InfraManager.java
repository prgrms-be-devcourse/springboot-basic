package com.prgrms.vouchermanagement.infra;

import com.prgrms.vouchermanagement.core.voucher.controller.VoucherController;
import com.prgrms.vouchermanagement.core.voucher.controller.request.VoucherCreateRequest;
import com.prgrms.vouchermanagement.core.voucher.controller.response.VouchersResponse;
import com.prgrms.vouchermanagement.core.voucher.domain.VoucherType;
import com.prgrms.vouchermanagement.infra.input.InputProvider;
import com.prgrms.vouchermanagement.infra.utils.OutputMessage;
import com.prgrms.vouchermanagement.infra.output.OutputProvider;
import com.prgrms.vouchermanagement.infra.utils.MenuType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class InfraManager implements CommandLineRunner {

    private final InputProvider inputProvider;
    private final OutputProvider outputProvider;
    private final VoucherController voucherController;

    @Autowired
    public InfraManager(InputProvider inputProvider, OutputProvider outputProvider, VoucherController voucherController) {
        this.inputProvider = inputProvider;
        this.outputProvider = outputProvider;
        this.voucherController = voucherController;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean isRunning = true;

        while (isRunning) {
            outputProvider.printMessage(OutputMessage.MENU_TYPES);
            MenuType menuType = inputProvider.inputMenuType();

            switch(menuType) {
                case LIST:
                    list();
                    break;
                case CREATE:
                    create();
                    break;
                case EXIT:
                    isRunning = false;
                    break;
            }
        }
    }

    private void list() {
        VouchersResponse vouchersResponse = voucherController.getAllVoucher();
        outputProvider.printMessage(vouchersResponse);
    }

    private void create() throws IOException {
        outputProvider.printMessage(OutputMessage.VOUCHER_NAME);
        String name = inputProvider.inputVoucherName();
        outputProvider.printMessage(OutputMessage.VOUCHER_TYPES);
        VoucherType voucherType = inputProvider.inputVoucherType();
        outputProvider.printMessage(voucherType);
        int amount = inputProvider.inputVoucherAmount();
        voucherController.createVoucher(new VoucherCreateRequest(name, voucherType, amount));
    }
}
