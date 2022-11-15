package org.prgrms.kdt.forward;

import org.prgrms.kdt.controller.VoucherController;
import org.prgrms.kdt.controller.request.CreateVoucherRequest;
import org.prgrms.kdt.controller.response.VoucherResponse;
import org.prgrms.kdt.forward.io.Input;
import org.prgrms.kdt.forward.io.Output;
import org.prgrms.kdt.view.ConsoleView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IOController {
    private final Input input;
    private final Output output;
    private final VoucherController voucherController;
    private final ConsoleView consoleView;
    private static final Logger logger = LoggerFactory.getLogger(IOController.class);

    public IOController(Input input, Output output, VoucherController voucherController, ConsoleView consoleView) {
        this.input = input;
        this.output = output;
        this.voucherController = voucherController;
        this.consoleView = consoleView;
    }

    public void run() {
        boolean exit = false;
        logger.info("Start : Voucher Manage Program");
        output.write(consoleView.introduceCommand());

        while (!exit) {
            try {
                String command = input.readLine();
                switch (command) {
                    case "create" -> {
                        logger.info("Request <create voucher>");
                        create();
                    }
                    case "list" -> {
                        logger.info("Request <list voucher>");
                        list();
                    }
                    case "exit" -> {
                        logger.info("Request <exit>");
                        output.write(consoleView.exit());
                        exit = true;
                    }
                    default -> {
                        output.write(consoleView.wrongCommand());
                    }
                }
            } catch (RuntimeException e) {
                logger.error(e.getMessage());
                output.write("**" + e.getMessage());
            }

            if (!exit) output.write(consoleView.introduceCommand());
        }

        input.close();
        logger.info("Finish : Voucher Manage Program");
    }

    private void create() {
        output.write(consoleView.requestVoucherInfo());
        String requestedVoucherInfo = input.readLine();
        CreateVoucherRequest createVoucherRequest = new CreateVoucherRequest(requestedVoucherInfo);
        if (voucherController.createVoucher(createVoucherRequest)) {
            output.write(consoleView.saveVoucher());
        }
        else output.write(consoleView.saveVoucherError());
    }

    private void list() {
        List<VoucherResponse> list = voucherController.getAllVouchers();
        if (list.isEmpty()) {
            output.write(consoleView.emptyVoucherList());
        }
        else output.write(consoleView.listVoucher(list));
    }
}
